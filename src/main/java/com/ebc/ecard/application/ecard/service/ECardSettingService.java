package com.ebc.ecard.application.ecard.service;

import com.ebc.ecard.application.ecard.dto.ECardContractSettingsDto;
import com.ebc.ecard.application.ecard.dto.ECardTemplateDto;
import com.ebc.ecard.application.ecard.dto.ECardTemplateSettingsDto;

import java.util.List;

public interface ECardSettingService {

	ECardContractSettingsDto findECardContractSettingsByECardId(String ecardId) throws Exception;

	int updateECardContractSettingsByECardId(ECardContractSettingsDto eCardContractSettingsDto);

	ECardTemplateSettingsDto findECardTemplateSettingsByECardId(String ecardId);

	int updateECardTemplateSettingsByECardId(ECardTemplateSettingsDto updateDto);

	List<ECardTemplateDto> getBackgrounds();
}