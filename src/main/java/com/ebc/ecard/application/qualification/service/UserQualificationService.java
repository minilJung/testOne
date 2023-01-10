package com.ebc.ecard.application.qualification.service;

import com.ebc.ecard.application.exception.EntityNotFoundException;
import com.ebc.ecard.application.scraping.dto.ScrapingStatusInsightDto;
import com.ebc.ecard.application.qualification.dto.QualificationUpdateDto;
import com.ebc.ecard.application.qualification.dto.UserQualificationDto;
import com.ebc.ecard.application.qualification.dto.UserQualificationInsightDto;
import com.ebc.ecard.application.qualification.dto.UserQualificationUpdateDto;
import com.ebc.ecard.util.ReturnMessage;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface UserQualificationService {
    ReturnMessage addQualificationsFromResponseText(QualificationUpdateDto payload, String userId) throws JsonProcessingException;

    List<UserQualificationDto> findQualificationByUserId(String userId);

    UserQualificationInsightDto getQualificationInsightByUserId(String userId, String publicYn);

    ReturnMessage updateQualification(UserQualificationUpdateDto updateDto) throws EntityNotFoundException;
    ReturnMessage updateQualification(List<UserQualificationUpdateDto> updateDto) throws EntityNotFoundException;

    ScrapingStatusInsightDto getQnetScrapingStatus(String scrapingId);
}
