 package com.ebc.ecard.application.ecard.service;

import com.ebc.ecard.application.ecard.handler.ECardCaptureImageHandler;
import com.ebc.ecard.application.ecard.dto.ECardContractSettingsDto;
import com.ebc.ecard.application.ecard.dto.ECardTemplateDto;
import com.ebc.ecard.application.ecard.dto.ECardTemplateSettingsDto;
import com.ebc.ecard.domain.ecard.ECardBean;
import com.ebc.ecard.domain.ecard.ECardTemplateBean;
import com.ebc.ecard.mapper.ECardMapper;
import com.ebc.ecard.mapper.ECardTemplateMapper;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EcardSettingServiceImpl implements ECardSettingService {

    @Resource
    private ECardMapper mapper;

    @Resource
    private ECardTemplateMapper templateMapper;

    @Resource
    private ECardCaptureImageHandler captureHandler;

	@Override
	public ECardContractSettingsDto findECardContractSettingsByECardId(String ecardId) {
		ECardBean bean = mapper.findECardByECardId(ecardId);

		return new ECardContractSettingsDto(
			bean.getEcardId(),
			bean.getCustomersPublicYn(),
			bean.getContractsPublicYn(),
			bean.getContractRatePublicYn(),
			bean.getImperfectRatePublicYn()
		);
	}

	@Override
	public int updateECardContractSettingsByECardId(ECardContractSettingsDto updateDto) {
		ECardBean bean = mapper.findECardByECardId(updateDto.getEcardId());

		bean.setEcardChangedYn("Y");
		bean.setCustomersPublicYn(updateDto.getCustomersPublicYn());
		bean.setContractsPublicYn(updateDto.getContractsPublicYn());
		bean.setContractRatePublicYn(updateDto.getContractRatePublicYn());
		bean.setImperfectRatePublicYn(updateDto.getImperfectRatePublicYn());

		int result = mapper.updateECardByECardId(bean);

		// 이미지 갱신
		CompletableFuture<Void> listenableFuture = captureHandler.updateECardCaptureImage(updateDto.getEcardId());
		listenableFuture
			.thenAccept(
				data -> {
					log.info("Success to update preview image {}", data);
				}
			).exceptionally(
				error -> {
					log.info("Error while update preview image async {}", error.getMessage(), error);
					return null;
				}
			);
		return result;
	}

	@Override
	public ECardTemplateSettingsDto findECardTemplateSettingsByECardId(String ecardId) {
		ECardBean bean = mapper.findECardSettingsByECardId(ecardId);

		ECardTemplateBean template = null;
		if (bean.getProfileTemplateNo() != null) {
			template = templateMapper.getBackground(Integer.parseInt(bean.getProfileTemplateNo()));
		}

		return ECardTemplateSettingsDto.fromBean(bean, template);
	}

	@Override
	public int updateECardTemplateSettingsByECardId(ECardTemplateSettingsDto updateDto) {
		ECardBean bean = mapper.findECardByECardId(updateDto.getEcardId());

		bean.setEcardChangedYn("Y");
		bean.setProfileTemplateNo(updateDto.getTemplateNo());
		bean.setBranchNumberPublicYn(updateDto.getBranchNumberPublicYn());
		bean.setFaxNumberPublicYn(updateDto.getFaxNumberPublicYn());
		bean.setBadgePublicYn(updateDto.getBadgePublicYn());

		int result = mapper.updateECardByECardId(bean);

		// 이미지 갱신
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

		return result;
	}

	public List<ECardTemplateDto> getBackgrounds() {
		List<ECardTemplateBean> backgrounds = templateMapper.getBackgrounds();
		List<ECardTemplateDto> templates = new ArrayList<>();
		backgrounds.forEach(template -> {
			templates.add(ECardTemplateDto.fromEntity(template));
		});

		return templates;
	}
}