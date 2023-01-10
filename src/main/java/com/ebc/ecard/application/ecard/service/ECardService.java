package com.ebc.ecard.application.ecard.service;

import com.ebc.ecard.application.ecard.dto.CustomerAccessCountDto;
import com.ebc.ecard.application.ecard.dto.ECardPreviewImageDto;
import com.ebc.ecard.application.ecard.dto.ECardProfileContactUpdateDto;
import com.ebc.ecard.application.ecard.dto.ECardProfileDto;
import com.ebc.ecard.application.ecard.dto.ECardProfileMetadataDto;
import com.ebc.ecard.application.ecard.dto.ECardProfileRequestDto;
import com.ebc.ecard.application.ecard.dto.ECardTemplateDto;
import com.ebc.ecard.application.ecard.dto.ECardUserDto;
import com.ebc.ecard.application.ecard.dto.ECardUserExistenceDto;
import com.ebc.ecard.application.exception.EntityNotFoundException;
import com.ebc.ecard.domain.ecard.CustomerAccessLogBean;
import com.ebc.ecard.domain.ecard.ECardBean;
import com.ebc.ecard.util.ReturnMessage;

import java.io.File;
import java.util.List;

public interface ECardService {
	Integer saveECard(ECardBean bean) throws Exception;
	ECardProfileDto findECardProfile(ECardProfileRequestDto requestDto) throws Exception;
	ECardUserDto findECardUser(ECardBean bean) throws Exception;

	Integer updateECardByECardId(ECardBean bean) throws Exception;

	Integer updateProfileContact(ECardProfileContactUpdateDto bean) throws EntityNotFoundException;

	Integer addViewCount(String ecardId) throws EntityNotFoundException;
	Integer addSharedCount(String ecardId) throws EntityNotFoundException;

	ReturnMessage getECardCompleteRate(String ecardId) throws Exception;

	List<ECardTemplateDto> getBackgrounds();

	File getECardAsVCard(String ecardId) throws Exception;

	byte[] getECardProfileImg(String ecardId) throws Exception;

	ReturnMessage updateEcardInfo(String serverHost, String ecardId) throws Exception;

	int addCustomerAccessLog(CustomerAccessLogBean bean);

	int updateECardPreviewImage(ECardPreviewImageDto updateDto);
	byte[] getECardPreviewImage(String ecardId) throws Exception;

	ECardProfileMetadataDto getECardMetadata(String ecardId);

	CustomerAccessCountDto getCustomerAccessCount(CustomerAccessLogBean bean);

	ECardUserExistenceDto getECardExistenceByECardId(String userId);

	int revisionAccountId(String ecardId) throws Exception;
}