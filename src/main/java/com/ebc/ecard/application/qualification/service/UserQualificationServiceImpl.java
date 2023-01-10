package com.ebc.ecard.application.qualification.service;

import com.ebc.ecard.application.exception.EntityNotFoundException;
import com.ebc.ecard.application.exception.ScrapingFailedException;
import com.ebc.ecard.application.qualification.dto.QualificationFilterDto;
import com.ebc.ecard.application.qualification.dto.QualificationInsight;
import com.ebc.ecard.application.qualification.dto.QualificationUpdateDto;
import com.ebc.ecard.application.qualification.dto.UserQualificationDto;
import com.ebc.ecard.application.qualification.dto.UserQualificationInsightDto;
import com.ebc.ecard.application.qualification.dto.UserQualificationUpdateDto;
import com.ebc.ecard.application.scraping.dto.ScrapingStatusInsightDto;
import com.ebc.ecard.application.scraping.dto.ScrapingUpdateDto;
import com.ebc.ecard.domain.scraping.CooconScrapingErrorCode;
import com.ebc.ecard.mapper.ScrapingMapper;
import com.ebc.ecard.domain.qualification.UserQualificationBean;
import com.ebc.ecard.application.scraping.dto.CooconResponseDto;
import com.ebc.ecard.mapper.UserQualificationMapper;
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
import java.util.stream.Collectors;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserQualificationServiceImpl implements UserQualificationService {

    private final String REQ_DATA_NAME = "ReqData=";

    @Value("${ecard.services.coocon.publishing-key}")
    protected String PUBLISHING_KEY;

    @Resource
    private XeCommon common;

    @Resource
    private UserQualificationMapper mapper;

    @Resource
    private ScrapingMapper scrapingMapper;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public ReturnMessage addQualificationsFromResponseText(QualificationUpdateDto payload, String userId) throws JsonProcessingException {

        CooconResponseDto outputDto = getOutputDtoFromResponseText(payload.getPayload());
        if (outputDto == null) {
            return new ReturnMessage("500", "데이터를 복호화하지 못했습니다.", new ScrapingFailedException("데이터 복호화 실패"));
        }

        Optional<Map<String, Object>> qualifications =
            outputDto.getResultList().stream().filter(it -> "자격증취득조회".equals(it.get("Job"))).findAny();

        if (!qualifications.isPresent()) {
            throw new ScrapingFailedException("잘못된 형식입니다.");
        }

        Map<String, Object> qualificationResult = qualifications.get();

        Map<String, Object> input = (Map<String, Object>) qualificationResult.get("Input");
        Map<String, Object> output = (Map<String, Object>) qualificationResult.get("Output");
        Map<String, Object> result = (Map<String, Object>) output.get("Result");
        log.info("QNET: Scraping callback test {}", objectMapper.writeValueAsString(input));

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
            return new ReturnMessage("0000", "성공", "불러올 자격증이 없습니다.");
        }

        List<Map<String, Object>> qualificationList = (List<Map<String, Object>>) result.get("자격증취득조회");

        qualificationList.forEach(qualification -> {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                mapper.createQualification(
                    new UserQualificationBean(
                        common.getUuid(false),
                        userId,
                        String.valueOf(qualification.get("자격명")),
                        String.valueOf(qualification.get("자격증번호")),
                        null,
                        (StringUtils.isEmpty((CharSequence) qualification.get("최종합격일자")) ? null : formatter.parse(String.valueOf(qualification.get("최종합격일자")))),
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
            scrapingMapper.updateScrapingResult(new ScrapingUpdateDto(scrapingId, payload.getPayload(), detailMessage, "1"));
        }

        return new ReturnMessage("0000", "자격증 목록을 성공적으로 불러왔습니다.", true);
    }

    @Override
    public List<UserQualificationDto> findQualificationByUserId(String userId) {

        QualificationFilterDto filter = new QualificationFilterDto(userId, null);
        List<UserQualificationBean> qualifications = mapper.findUserQualificationBySpecification(filter);
        List<UserQualificationDto> results = new ArrayList<>();

        qualifications.forEach(qualification -> {
            results.add(UserQualificationDto.fromBean(qualification));
        });

        return results;
    }

    @Override
    public UserQualificationInsightDto getQualificationInsightByUserId(String userId, String publicYn) {

        QualificationInsight insight = mapper.getInsightByUserId(userId);
        QualificationFilterDto filter = new QualificationFilterDto(userId, publicYn);
        List<UserQualificationBean> qualifications = mapper.findUserQualificationBySpecification(filter);
        List<UserQualificationDto> results = new ArrayList<>();

        qualifications.forEach(qualification -> {
            results.add(UserQualificationDto.fromBean(qualification));
        });

        return UserQualificationInsightDto.of(
            insight,
            results
        );
    }

    @Override
    public ReturnMessage updateQualification(UserQualificationUpdateDto updateDto) throws EntityNotFoundException {

        UserQualificationBean bean = mapper.findUserQualificationByQualificationId(updateDto.getQualificationId());
        if (bean == null) {
            throw new EntityNotFoundException(UserQualificationBean.class);
        }

        if (updateDto.getPublicYn() != null) {
            bean.setPublicYn(updateDto.getPublicYn());
        }

        return new ReturnMessage(mapper.updateQualification(bean));
    }

    @Override
    public ReturnMessage updateQualification(List<UserQualificationUpdateDto> updateDto) throws EntityNotFoundException {

        updateDto.forEach(this::updateQualification);
        return new ReturnMessage();
    }

    @Override
    public ScrapingStatusInsightDto getQnetScrapingStatus(String scrapingId) {
        return ScrapingStatusInsightDto.from(scrapingMapper.findScrapingByScrapingId(scrapingId));
    }

    private CooconResponseDto getOutputDtoFromResponseText(String responseText) {
        try {

            String[] payloadVariables = UriEncoder.decode(responseText).split("&");

            String reqDataOrigin = null;
            if (payloadVariables.length > 0) {
                reqDataOrigin = payloadVariables[1].substring(
                    payloadVariables[1].indexOf(REQ_DATA_NAME) + REQ_DATA_NAME.length()
                );
            }

            String decryptedData = AESUtil.decryptBase64ToAES(reqDataOrigin, PUBLISHING_KEY).replaceAll("\"", "\\\"");

            log.info("Qnet Data to Decrypting: {}", objectMapper.writeValueAsString(decryptedData));
            return objectMapper.readValue(decryptedData, CooconResponseDto.class);
        } catch(Exception e) {
            log.info("error while AES decryption", e);
            return null;
        }
    }

    private boolean isPassed(String errorCode) {
        return Arrays.stream(CooconScrapingErrorCode.getSuccessCodes())
        .anyMatch(it ->  it.getValue().equals(errorCode));

    }
}
