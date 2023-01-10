package com.ebc.ecard.application.ecard.service;

import com.ebc.ecard.application.contract.dto.ContractInfoDto;
import com.ebc.ecard.application.contract.dto.EmployeeContractInfoResponseDto;
import com.ebc.ecard.application.ecard.handler.ECardCaptureImageHandler;
import com.ebc.ecard.application.ecard.dto.CustomerAccessCountDto;
import com.ebc.ecard.application.ecard.dto.ECardBadgeDto;
import com.ebc.ecard.application.ecard.dto.ECardCompletionPropertiesDto;
import com.ebc.ecard.application.ecard.dto.ECardCompletionRateDto;
import com.ebc.ecard.application.ecard.dto.ECardPreviewImageDto;
import com.ebc.ecard.application.ecard.dto.ECardProfileCaptureRequestDto;
import com.ebc.ecard.application.ecard.dto.ECardProfileCaptureResponseDto;
import com.ebc.ecard.application.ecard.dto.ECardProfileContactUpdateDto;
import com.ebc.ecard.application.ecard.dto.ECardProfileDto;
import com.ebc.ecard.application.ecard.dto.ECardProfileMetadataDto;
import com.ebc.ecard.application.ecard.dto.ECardProfileRequestDto;
import com.ebc.ecard.application.ecard.dto.ECardTemplateDto;
import com.ebc.ecard.application.ecard.dto.ECardUserDto;
import com.ebc.ecard.application.ecard.dto.ECardUserExistenceDto;
import com.ebc.ecard.application.ecard.dto.EmployeeCvInfoRequestDto;
import com.ebc.ecard.application.ecard.dto.EmployeeCvInfoResponseDto;
import com.ebc.ecard.application.ecard.handler.ECardCompletionPropertyHandler;
import com.ebc.ecard.application.exception.EntityNotFoundException;
import com.ebc.ecard.application.exception.FpIdEncryptionFailedException;
import com.ebc.ecard.application.nftcv.dto.request.NFTCVRequestDto;
import com.ebc.ecard.application.nftcv.service.NftCvInternalService;
import com.ebc.ecard.application.user.dto.IAMRequestDto;
import com.ebc.ecard.application.user.dto.IAMResponseDto;
import com.ebc.ecard.application.user.exception.IAMIntegrationFailedException;
import com.ebc.ecard.application.user.internal.EmployeeCvInternalService;
import com.ebc.ecard.application.user.internal.IAMInternalService;
import com.ebc.ecard.domain.ecard.CustomerAccessLogBean;
import com.ebc.ecard.domain.ecard.ECardBean;
import com.ebc.ecard.domain.ecard.ECardCaptureScheduleBean;
import com.ebc.ecard.domain.ecard.ECardTemplateBean;
import com.ebc.ecard.application.nftcv.dto.NFTCvUserDto;
import com.ebc.ecard.domain.user.UserBean;
import com.ebc.ecard.mapper.ECardCaptureScheduleMapper;
import com.ebc.ecard.mapper.ECardMapper;
import com.ebc.ecard.mapper.ECardTemplateMapper;
import com.ebc.ecard.mapper.UserMapper;
import com.ebc.ecard.persistence.UserRepository;
import com.ebc.ecard.property.ECardCapturePropertyBean;
import com.ebc.ecard.property.ECardUiPropertyBean;
import com.ebc.ecard.util.AES256;
import com.ebc.ecard.util.ImageUtil;
import com.ebc.ecard.util.ReturnMessage;
import com.ebc.ecard.util.XeCommon;
import com.ebc.ecard.util.request.ECardHttpRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.HttpClientErrorException;
import org.yaml.snakeyaml.util.UriEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ECardServiceImpl implements ECardService {

    private final String PROFILE_THUMBNAIL_DEFAULT_URL = "/img/open_graph_default_white.png";

    @Resource
    private ECardCapturePropertyBean captureProperty;

    @Resource
    private ECardUiPropertyBean uiProperty;

    @Resource
    private IAMInternalService iamService;

    @Resource
    private EmployeeCvInternalService employeeCvService;

    @Resource
    private NftCvInternalService nftCvService;

    @Resource
    private UserRepository userRepository;

    @Resource
    private ECardMapper mapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ECardTemplateMapper templateMapper;

    @Resource
    private ECardCaptureImageHandler captureHandler;

    @Resource
    private ECardCaptureScheduleMapper captureScheduleMapper;

    @Resource
    private ECardCompletionPropertyHandler completeRateHandler;

    @Resource
    private XeCommon common;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public Integer saveECard(ECardBean bean) throws Exception {
        bean.setEcardId(common.getRandomString(true, 6));
        bean.setCompanyId("X");
        bean.setFpId(common.getUuid(false));
        bean.setCustomersPublicYn("Y");
        bean.setContractsPublicYn("Y");
        bean.setContractRatePublicYn("Y");
        bean.setImperfectRatePublicYn("Y");
        bean.setBranchNumberPublicYn("Y");
        bean.setFaxNumberPublicYn("Y");
        bean.setBadgePublicYn("Y");

        return mapper.addECard(bean);
    }

    @Override
    public ECardProfileDto findECardProfile(ECardProfileRequestDto requestDto) {
        ECardBean ecard = mapper.findECardByECardId(requestDto.getEcardId());
        if (ecard == null) {
            return null;
        }

        UserBean user = userMapper.findUserByUserId(ecard.getUserId());
        if (user == null) {
            return null;
        }

        if (StringUtils.isNotEmpty(ecard.getProfileFileName())) {
            ecard.setProfileImageUrl(common.getS3FilePath(ecard.getProfileFileName()));
        }

        if (StringUtils.isNotEmpty(ecard.getPreviewFileName())) {
            ecard.setPreviewImageUrl(common.getS3FilePath(ecard.getPreviewFileName()));
        }

        ECardTemplateBean template = templateMapper.getBackground(
                (StringUtils.isEmpty(ecard.getProfileTemplateNo()))
                    ? 1
                    : Integer.parseInt(ecard.getProfileTemplateNo())
            );

        List<ECardBadgeDto> badgeList = mapper.findProfileBadgeByECardId(requestDto);
        badgeList.sort((a, b) -> {
                if (a.getBadgeName().equals(b.getBadgeName())) {
                    return 0;
                }

                return "ACE_CLUB".equals(a.getBadgeName()) ? 1 : -1;
            });

        return ECardProfileDto.of(ecard, user, template, badgeList);
    }

    @Override
    public ECardUserDto findECardUser(ECardBean bean) {
        HashMap<String, Object> ecardMap = mapper.findECardBaseInfoByECardId(bean.getEcardId());
        if (ecardMap == null) {
            return null;
        }

        return ECardUserDto.fromMap(ecardMap);
    }

    public List<ECardTemplateDto> getBackgrounds() {
        List<ECardTemplateBean> backgrounds = templateMapper.getBackgrounds();
        List<ECardTemplateDto> templates = new ArrayList<>();
        backgrounds.forEach(template -> {
            templates.add(ECardTemplateDto.fromEntity(template));
        });

        return templates;
    }

    public ReturnMessage getECardCompleteRate(String ecardId) throws Exception {
        ECardBean bean = mapper.findECardByECardId(ecardId);
        if (bean == null) {
            return new ReturnMessage("9000", "요청과 일치하는 전자명함을 찾지 못했습니다.", false);
        }

        ECardCompletionPropertiesDto completionPropertiesDto = completeRateHandler.getCompleteRate(bean);
        Object[] methods = Arrays.stream(completionPropertiesDto.getClass().getMethods())
            .filter(
                method -> {
                    return method.getName().contains("is");
                }
            ).toArray();

        int totalFields = methods.length;
        int completedFields = 0;
        for(Object method: methods) {
            try {
                boolean result = (Boolean) ((Method) method).invoke(completionPropertiesDto);
                if (result) {
                    completedFields += 1;
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        ECardCompletionRateDto completedRateDto = new ECardCompletionRateDto(
            totalFields,
            completedFields,
            (completedFields / (double) totalFields) * 100,
            completionPropertiesDto
        );

        return new ReturnMessage(completedRateDto);
    }

    @Override
    public Integer updateProfileContact(ECardProfileContactUpdateDto updateDto) throws EntityNotFoundException {
        ECardBean bean = mapper.findECardByECardId(updateDto.getEcardId());

        // branchNumber, faxNumber
        bean.setEcardChangedYn("Y");
        bean.setFaxNumber(updateDto.getFaxNumber().replaceAll("[^\\uAC00-\\uD7A30-9a-zA-Z\\\\s]", ""));
        bean.setBranchNumber(updateDto.getBranchNumber().replaceAll("[^\\uAC00-\\uD7A30-9a-zA-Z\\\\s]", ""));
        if (updateDto.getProfileFileId() != null && !"".equals(updateDto.getProfileFileId())) {
            bean.setProfileFileId(updateDto.getProfileFileId());

            if (updateDto.getProfileFileId().equals("REMOVE")) {
                bean.setProfileFileId(null);
            }
        }

        if (mapper.updateECardByECardId(bean) > 0) {
            CompletableFuture<Void> listenableFuture = captureHandler.updateECardCaptureImage(updateDto.getEcardId());
            listenableFuture
                .thenAccept(
                    data -> {
                        log.info("Success to update preview image {}", data);
                    }
                ).exceptionally(
                    error -> {
                        log.info("Error while update donwloadable image async {}", error.getMessage(), error);
                        return null;
                    }
                );

//            captureScheduleMapper.addScheduleById(
//                ECardCaptureScheduleBean.create(common.getUuid(false), bean.getEcardId())
//            );

            return 1;
        }

        return 0;
    }

    @Override
    public Integer updateECardByECardId(ECardBean update) throws Exception {
        ECardBean bean = mapper.findECardByECardId(update.getEcardId());

        // branchNumber, faxNumber
        bean.setEcardChangedYn("Y");
        bean.setFaxNumber(update.getFaxNumber().replaceAll("[^\\uAC00-\\uD7A30-9a-zA-Z\\\\s]", ""));
        bean.setBranchNumber(update.getBranchNumber().replaceAll("[^\\uAC00-\\uD7A30-9a-zA-Z\\\\s]", ""));

        if (update.getProfileFileId() != null && !"".equals(update.getProfileFileId())) {
            bean.setProfileFileId(update.getProfileFileId());

            if (update.getProfileFileId().equals("REMOVE")) {
                bean.setProfileFileId(null);
            }
        }

        if (StringUtils.isNotEmpty(update.getEmail())) {
            bean.setEmail(update.getEmail());
        }

        if (update.getFpId() != null) {
            bean.setFpId(update.getFpId());
        }

        if (update.getProfileImageUrl() != null) {
            bean.setProfileImageUrl(update.getProfileImageUrl());
        }

        if (update.getProfileTemplateNo() != null) {
            bean.setProfileTemplateNo(update.getProfileTemplateNo());
        }

        if (update.getContractsPublicYn() != null) {
            bean.setContractsPublicYn(update.getContractsPublicYn());
        }

        if (update.getCustomersPublicYn() != null) {
            bean.setCustomersPublicYn(update.getCustomersPublicYn());
        }

        if (update.getContractRatePublicYn() != null) {
            bean.setContractRatePublicYn(update.getContractRatePublicYn());
        }

        if (update.getImperfectRatePublicYn() != null) {
            bean.setImperfectRatePublicYn(update.getImperfectRatePublicYn());
        }

        if (update.getBranchNumberPublicYn() != null) {
            bean.setBranchNumberPublicYn(update.getBranchNumberPublicYn());
        }

        if (update.getFaxNumberPublicYn() != null) {
            bean.setFaxNumberPublicYn(update.getFaxNumberPublicYn());
        }

        if (update.getBadgePublicYn() != null) {
            bean.setBadgePublicYn(update.getBadgePublicYn());
        }

        if (mapper.updateECardByECardId(bean) > 0) {
            captureScheduleMapper.addScheduleById(
                ECardCaptureScheduleBean.create(common.getUuid(false), bean.getEcardId())
            );

            return 1;
        }

        return 0;
    }
    @Override
    public Integer addViewCount(String ecardId) throws EntityNotFoundException {
        ECardBean bean = mapper.findECardByECardId(ecardId);
        if (bean == null) {
            throw new EntityNotFoundException(ECardBean.class);
        }

        bean.visit();

        return mapper.saveECardIndicators(bean);
    }

    public Integer addSharedCount(String ecardId) throws EntityNotFoundException {
        ECardBean bean = mapper.findECardByECardId(ecardId);
        if (bean == null) {
            throw new EntityNotFoundException(ECardBean.class);
        }

        bean.share();

        return mapper.saveECardIndicators(bean);
    }

    @Override
    public File getECardAsVCard(String ecardId) throws Exception {
        HashMap<String, Object> map = mapper.findECardBaseInfoByECardId(ecardId);
        if (map == null) {
            throw new EntityNotFoundException(HashMap.class);
        }

        String fileName = (UriEncoder.encode((String) map.get("name"))) + "-" + (new Date()).getTime() + ".vcf";

        File vCardFile = File.createTempFile("vCard" + fileName, "");
        vCardFile.deleteOnExit();

        String name = UriEncoder.encode((String) map.get("name"));
        String position =
                UriEncoder.encode(
                    map.get("position") != null ? (String) map.get("position") : "FP"
                );

        String contactName = name + " " + position;
        String content = "BEGIN:VCARD\n"
            + "VERSION:3.0\n"
            + "N:" + contactName + ";\n";

        content += "FN:" + contactName + "\n";

        if (map.get("branchName") != null) {
            content += "ORG:" + UriEncoder.encode((String) map.get("branchName")) + "\n"
                + "TITLE:" + UriEncoder.encode((String) map.get("branchName")) + "\n";
        }

        //                + "PHOTO;VALUE#URI;TYPE#GIF:\n" // 프로필사진...
        if (map.get("mobileNumber") != null) {
            content += "TEL;TYPE#PHONE,VOICE:" + map.get("mobileNumber") + "\n";
        }
        if (map.get("branchNumber") != null) {
            content += "TEL;TYPE#WORK,VOICE:" + map.get("branchNumber") + "\n";
        }
        if (map.get("email") != null) {
            content += "EMAIL:" + map.get("email") + "\n";
        }
        content += "END:VCARD";

        try (FileOutputStream fouts = new FileOutputStream(vCardFile)) {
            fouts.write(content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return vCardFile;
    }

    @Override
    public byte[] getECardProfileImg(String ecardId) throws Exception {

        HashMap<String, Object> map = mapper.findECardBaseInfoByECardId(ecardId);
        if (map == null) {
            throw new EntityNotFoundException(HashMap.class);
        }

        try {
            if (map.get("profileFileName") != null && !map.get("profileFileName").equals("")) {
                String profileImgUrl = common.getS3FilePath(map.get("profileFileName").toString());

                String extension = map.get("profileFileName").toString().split("\\.")[1];
                return ImageUtil.toByteArray(
                    ImageIO.read(new URL(profileImgUrl.replaceAll("%2F", "/"))),
                    extension
                );
            }
        } catch(IOException e) {
            // ignore
        }
        return new byte[0];
    }

    @Override
    public int updateECardPreviewImage(ECardPreviewImageDto updateDto) {

        ECardBean bean = mapper.findECardByECardId(updateDto.getEcardId());
        if (bean == null) {
            throw new EntityNotFoundException(ECardBean.class);
        }

        return mapper.updatePreviewImage(updateDto);
    }

    @Override
    public byte[] getECardPreviewImage(String ecardId) {

        return captureHandler.getECardCaptureImageWithDownloadAt(ecardId);
    }

    public ECardProfileMetadataDto getECardMetadata(String ecardId) {
        ECardProfileMetadataDto profileMetadata = mapper.getECardProfileMetadata(ecardId);

        String fileName = profileMetadata.getPreviewFileName();
        String previewImageUrl = (StringUtils.isNotEmpty(fileName))
            ? common.getS3FilePath(fileName)
            : PROFILE_THUMBNAIL_DEFAULT_URL;

        profileMetadata.setPreviewImageUrl(previewImageUrl);
        profileMetadata.setProfileUrl(uiProperty.getUrlWithProtocol() + "/customer-link/" + new String(Base64Utils.encode(ecardId.getBytes())));

        return profileMetadata;
    }

    @Override
    public ReturnMessage updateEcardInfo(String serverHost, String ecardId) throws Exception {

        NFTCvUserDto userDto = userMapper.findEcardUserInfo(ecardId);
        UserBean userBean = userRepository.findById(userDto.getUserId());

        EmployeeCvInfoResponseDto cvInfoDto = null;

        if ("hanwhalifefs".equals(userDto.getCompanyId()) && StringUtils.isNotEmpty(userDto.getFpId())) {
            try {
                String encryptedFpId = AES256.encrypt(userDto.getFpId(), AES256.EBC_AES256_KEY);
                cvInfoDto = employeeCvService.getEmployeeCvInfo(
                    new EmployeeCvInfoRequestDto(encryptedFpId)
                );

            } catch(
                InvalidAlgorithmParameterException
                | InvalidKeyException
                | NoSuchPaddingException
                | UnsupportedEncodingException
                | IllegalBlockSizeException
                | BadPaddingException
                | NoSuchAlgorithmException e
            ) {
                throw new FpIdEncryptionFailedException(e);
            }
        }

        ECardBean ecard = mapper.findECardByFpId(userDto.getFpId());
        if (!isNullOrFailed(cvInfoDto)) {
            updateEcardEmployeeCvInfo(ecard, cvInfoDto);
            userBean = employeeCvService.setUserProfileToUserBean(userBean, cvInfoDto);
        }

        NFTCVRequestDto requestDto = NFTCVRequestDto.of(
            serverHost + "/api/ecard/nft-profile/callback",
            userBean
        );


        nftCvService.saveOrUpdateUserCV(requestDto, userDto.getAccountId())
            .thenAccept(it -> {

            })
            .exceptionally(e -> {
                if (e.getMessage().contains("account is not exist")) {
                    // eBAP 연동 안된 경우
                    nftCvService.saveEBAPAccount(userDto.getAccountId());
                    nftCvService.saveOrUpdateUserCV(requestDto, userDto.getAccountId());
                }
                return null;
            });

        return new ReturnMessage();
    }

    @Override
    public int addCustomerAccessLog(CustomerAccessLogBean bean) {
        ECardBean ecard = mapper.findECardByECardId(bean.getEcardId());
        bean.setFpId(ecard.getFpId());

        return mapper.addCustomerAccessLog(bean);
    }

    @Override
    public CustomerAccessCountDto getCustomerAccessCount(CustomerAccessLogBean bean) {
        return mapper.getCustomerAccessCount(bean);
    }

    /**
     * userId를 통해 ecard 존재 여부 검사
     */
    public ECardUserExistenceDto getECardExistenceByECardId(String ecardId) {

        UserBean user = userMapper.getUserInfoByECardId(ecardId);
        if (user == null) {
            return ECardUserExistenceDto.ofNotExists();
        }

        String serviceUrl = uiProperty.getUrlWithProtocol();

        return new ECardUserExistenceDto(
            "Y",
            user.getFpId(),
            user.getEcardId(),
            user.getUserId(),
            user.getName(),
            user.getCi(),
            user.getAccountId(),
            serviceUrl + "/main/" + ecardId
        );
    }

    public int revisionAccountId(String ecardId) throws Exception {

        UserBean user = userMapper.getUserInfoByECardId(ecardId);


        IAMResponseDto responseDto = null;
        try {
            responseDto = iamService.saveIAM(
                new IAMRequestDto(user.getName(), user.getUserId(), user.getCi())
            );

        } catch (HttpClientErrorException e) {
            if (e.getMessage().contains("\"errorCode\":400101")) {
                // 랜덤 유저아이디로 재시도
                responseDto = iamService.saveIAM(
                    new IAMRequestDto(
                        user.getName(),
                        user.getName() + "_" + (int)(Math.random() * 1000),
                        user.getCi()
                    )
                );
            }
        } catch(Exception e) {
            log.info("Failed to integration IAM {}", e.getMessage(), e);
        }

        if (responseDto == null) {
            throw new IAMIntegrationFailedException();
        }

        user.setAccountId(responseDto.getData().getAccountId());
        return userMapper.updateUserInfoByUserId(user);
    }

    private String getProfileImageCapture(String ecardId) throws JsonProcessingException {
        ECardProfileCaptureRequestDto requestDto = new ECardProfileCaptureRequestDto(
                common.getUuid(false),
                uiProperty.getUrlWithProtocol() + "/customer-link/" + Base64Util.encode(ecardId)
        );

        ECardProfileCaptureResponseDto response = ECardHttpRequest.Builder.build(captureProperty.getUrlWithProtocol())
                .post("/util/capture")
                .payload(requestDto)
                .executeAndGetBodyAs(ECardProfileCaptureResponseDto.class);

        log.info("ProfileImageCapture: {}", objectMapper.writeValueAsString(response));
        log.info("ProfileData: {}", objectMapper.writeValueAsString(response.getResult().get("data")));

        String imageData = (String) response.getResult().get("data");
        File imageFile = new File("");

        return imageData;
    }

    private ECardBean updateEcardEmployeeCvInfo(ECardBean bean, EmployeeCvInfoResponseDto cvInfoDto)
        throws
        InvalidAlgorithmParameterException,
        NoSuchPaddingException,
        IllegalBlockSizeException,
        UnsupportedEncodingException,
        NoSuchAlgorithmException,
        BadPaddingException,
        InvalidKeyException
    {

        String email = StringUtils.isNotEmpty(cvInfoDto.getMailAddr())
            ? AES256.decrypt(cvInfoDto.getMailAddr(), AES256.EBC_AES256_KEY)
            : null;
        String department = StringUtils.stripToNull(cvInfoDto.getBrocOrgnNm());
        String onDuty = StringUtils.stripToNull(cvInfoDto.getTnofDvsnCode());
        String position = StringUtils.stripToNull(cvInfoDto.getClpsNm());

        bean.setEmployeeCvInfo(email, department, onDuty, position);

        // 계약 현황 파싱
        if (!cvInfoDto.getCntcCrst().isEmpty()) {
            EmployeeContractInfoResponseDto cvContractResponse = cvInfoDto.getCntcCrst().get(0);

            bean.setContractInfo(ContractInfoDto.of(cvContractResponse));
        }

        mapper.updateECardByECardId(bean);

        return bean;
    }

    private boolean isNullOrFailed(EmployeeCvInfoResponseDto cvInfoDto) {
        return cvInfoDto == null || "99".equals(cvInfoDto.getRetnCode());
    }
}
