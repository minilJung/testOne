package com.ebc.ecard.application.career.service;

import com.ebc.ecard.application.exception.EntityNotFoundException;
import com.ebc.ecard.application.exception.ScrapingFailedException;
import com.ebc.ecard.domain.scraping.CooconScrapingErrorCode;
import com.ebc.ecard.domain.scraping.ScrapingBean;
import com.ebc.ecard.application.scraping.dto.ScrapingStatusInsightDto;
import com.ebc.ecard.application.scraping.dto.ScrapingUpdateDto;
import com.ebc.ecard.mapper.ScrapingMapper;
import com.ebc.ecard.domain.career.UserCareerBean;
import com.ebc.ecard.application.scraping.dto.CooconResponseDto;
import com.ebc.ecard.application.career.dto.CareerFilterDto;
import com.ebc.ecard.application.career.dto.CareerInsight;
import com.ebc.ecard.application.career.dto.CareerUpdateDto;
import com.ebc.ecard.application.career.dto.UserCareerDto;
import com.ebc.ecard.application.career.dto.UserCareerInsightDto;
import com.ebc.ecard.application.career.dto.UserCareerUpdateDto;
import com.ebc.ecard.mapper.UserCareerMapper;
import com.ebc.ecard.util.AESUtil;
import com.ebc.ecard.util.ReturnMessage;
import com.ebc.ecard.util.XeCommon;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.UriEncoder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserCareerServiceImpl implements UserCareerService {

    @Value("${ecard.services.coocon.publishing-key}")
    protected String PUBLISHING_KEY;

    @Resource
    XeCommon common;

    @Resource
    ScrapingMapper scrapingMapper;

    @Resource
    UserCareerMapper careerMapper;

    @Resource
    ObjectMapper objectMapper;


    @Override
    public ReturnMessage addCareersFromResponseText(CareerUpdateDto payload, String userId) throws JsonProcessingException {

        CooconResponseDto outputDto = getOutputDtoFromResponseText(payload.getPayload());

        Optional<Map<String, Object>> careers = outputDto.getResultList().stream().filter(it -> "자격득실확인서".equals(it.get("Job"))).findAny();

        if (!careers.isPresent()) {
            throw new ScrapingFailedException("잘못된 형식입니다.");
        }

        Map<String, Object> careerResult = careers.get();

        Map<String, Object> input = (Map<String, Object>) careerResult.get("Input");
        Map<String, Object> output = (Map<String, Object>) careerResult.get("Output");
        Map<String, Object> result = (Map<String, Object>) output.get("Result");
        log.info("NHIS: Scraping callback test {}", objectMapper.writeValueAsString(input));

        String errorCode = (String) output.get("ErrorCode");
        String detailMessage = (String) output.get("detailMessage");
        String scrapingId = (input != null)
            ? StringUtils.stripToNull((String) input.get("scrapingId"))
            : null;

        if (!isPassed(errorCode)) {
            if (scrapingId != null) {
                scrapingMapper.updateScrapingResult(new ScrapingUpdateDto(scrapingId, payload.getPayload(), detailMessage, "-1"));
            }

            throw new ScrapingFailedException();
        }

        if (errorCode.equals(CooconScrapingErrorCode.RESULT_NONE.getValue()) && scrapingId != null) {

            scrapingMapper.updateScrapingResult(
                new ScrapingUpdateDto(scrapingId, payload.getPayload(), detailMessage, "2")
            );
            return new ReturnMessage("0000", "성공", "불러올 경력이 없습니다.");
        }

        List<Map<String, Object>> careerList = (List<Map<String, Object>>) result.get("자격득실내역");

        careerList.stream()
            .filter(it -> "직장가입자".equals(it.get("가입자구분")))
            .forEach(career -> {
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                    careerMapper.addCareer(
                        new UserCareerBean(
                            common.getUuid(false),
                            userId,
                            String.valueOf(career.get("가입자구분")),
                            String.valueOf(career.get("사업자명칭")),
                            formatter.parse(String.valueOf(career.get("취득일"))),
                            (StringUtils.isEmpty((CharSequence) career.get("상실일")) ? null : formatter.parse(String.valueOf(career.get("상실일")))),
                            (career.get("상실일") == null) ? "Y" : "N",
                            "Y",
                        "1"
                        )
                    );
                } catch (DuplicateKeyException e) {
                    // ignore duplicate
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            });

        if (scrapingId != null) {
            scrapingMapper.updateScrapingResult(new ScrapingUpdateDto(scrapingId, payload.getPayload(), (String) output.get("detailMessage"), "1"));
        }


        return new ReturnMessage("0000", "경력을 성공적으로 불러왔습니다.", true);
    }

    @Override
    public List<UserCareerDto> findCareerByUserId(String userId, String publicYn) throws Exception {
        List<UserCareerBean> careers = careerMapper.findCareerBySpecification(new CareerFilterDto(userId, publicYn));
        List<UserCareerDto> results = new ArrayList<>();

        careers.forEach(career -> {
            results.add(UserCareerDto.fromBean(career));
        });

        return results;
    }

    @Override
    public UserCareerInsightDto findCareerInsightByUserId(String userId, String publicYn) throws Exception {
        CareerInsight insight = careerMapper.getInsightByUserId(userId);
        List<UserCareerBean> careers = careerMapper.findCareerBySpecification(new CareerFilterDto(userId, publicYn));
        List<UserCareerDto> results = new ArrayList<>();

        careers.forEach(career -> {
            results.add(UserCareerDto.fromBean(career));
        });

        return UserCareerInsightDto.of(
            insight,
            results
        );
    }

    @Override
    public ReturnMessage updateCareer(UserCareerUpdateDto updateDto) throws EntityNotFoundException {
        UserCareerBean career = careerMapper.getCareerByCareerId(updateDto.getCareerId());
        if (career == null) {
            throw new EntityNotFoundException(UserCareerBean.class);
        }

        if (updateDto.getPublicYn() != null) {
            career.setPublicYn(updateDto.getPublicYn());
        }

        return new ReturnMessage(careerMapper.updateCareer(career));
    }

    @Override
    public ReturnMessage updateCareer(List<UserCareerUpdateDto> updateDto) throws EntityNotFoundException {
        updateDto.forEach(this::updateCareer);

        return new ReturnMessage();
    }

    @Override
    public ScrapingStatusInsightDto getNhisScrapingStatus(String scrapingId) {
        ScrapingBean bean = scrapingMapper.findScrapingByScrapingId(scrapingId);
        if (bean == null) {
            return null;
        }

        return ScrapingStatusInsightDto.from(bean);
    }

    private CooconResponseDto getOutputDtoFromResponseText(String responseText) {
        try {

            String payloadString = UriEncoder.decode(responseText);

            String[] payloadVariables = payloadString.split("&");

            String reqDataName = "ReqData=";
            String reqDataOrigin = null;
            if (payloadVariables.length > 0) {
                reqDataOrigin = payloadVariables[1].substring(
                    payloadVariables[1].indexOf("ReqData=") + reqDataName.length()
                );
            }

            String decryptedData = AESUtil.decryptBase64ToAES(reqDataOrigin, PUBLISHING_KEY)
                .replaceAll("\"", "\\\"");
            log.info("NHIS Data to Decrypting: {}", objectMapper.writeValueAsString(decryptedData));
            return objectMapper.readValue(decryptedData, CooconResponseDto.class);

        } catch (Exception e) {
            log.info("error while AES decryption", e);
            return null;
        }
    }

    private boolean isPassed(String errorCode) {
        return Arrays.stream(CooconScrapingErrorCode.getSuccessCodes())
            .anyMatch(it ->  it.getValue().equals(errorCode));

    }
}