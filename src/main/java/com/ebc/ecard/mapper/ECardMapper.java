package com.ebc.ecard.mapper;

import com.ebc.ecard.application.ecard.dto.CustomerAccessCountDto;
import com.ebc.ecard.application.ecard.dto.ECardBadgeDto;
import com.ebc.ecard.application.ecard.dto.ECardPreviewImageDto;
import com.ebc.ecard.application.ecard.dto.ECardProfileMetadataDto;
import com.ebc.ecard.application.ecard.dto.ECardProfileRequestDto;
import com.ebc.ecard.domain.ecard.CustomerAccessLogBean;
import com.ebc.ecard.domain.ecard.ECardBean;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ECardMapper {
	Integer addECard(ECardBean bean);
	ECardBean findECardByECardId(String ecardId);
	ECardBean findECardByUserId(String userId);

	ECardBean findECardByFpId(String fpId);
	ECardBean findECardSettingsByECardId(String ecardId);
	HashMap<String, Object> findECardBaseInfoByECardId(String ecardId);
	HashMap<String, Object> findECardBaseInfoByCompanyIdAndUserId(@Param(value="companyId") String companyId, @Param(value="userId") String userId);
	Integer updateECardByECardId(ECardBean bean);
	List<ECardBadgeDto> findProfileBadgeByECardId(ECardProfileRequestDto imageSize);
	Integer saveECardIndicators(ECardBean bean);

	Integer addCustomerAccessLog(CustomerAccessLogBean bean);
	Integer updatePreviewImage(ECardPreviewImageDto updateDto);

	ECardProfileMetadataDto getECardProfileMetadata(String ecardId);

	CustomerAccessCountDto getCustomerAccessCount(CustomerAccessLogBean bean);
}
