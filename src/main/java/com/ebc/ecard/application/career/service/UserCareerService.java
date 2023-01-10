package com.ebc.ecard.application.career.service;

import com.ebc.ecard.application.exception.EntityNotFoundException;
import com.ebc.ecard.application.scraping.dto.ScrapingStatusInsightDto;
import com.ebc.ecard.application.career.dto.CareerUpdateDto;
import com.ebc.ecard.application.career.dto.UserCareerDto;
import com.ebc.ecard.application.career.dto.UserCareerInsightDto;
import com.ebc.ecard.application.career.dto.UserCareerUpdateDto;
import com.ebc.ecard.util.ReturnMessage;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface UserCareerService {

	ReturnMessage addCareersFromResponseText(CareerUpdateDto payload, String userId) throws JsonProcessingException;
	List<UserCareerDto> findCareerByUserId(String userId, String publicYn) throws Exception;

	UserCareerInsightDto findCareerInsightByUserId(String userId, String publicYn) throws Exception;

	ReturnMessage updateCareer(UserCareerUpdateDto updateDto) throws EntityNotFoundException;

	ReturnMessage updateCareer(List<UserCareerUpdateDto> updateDto) throws EntityNotFoundException;

	ScrapingStatusInsightDto getNhisScrapingStatus(String scrapingId);
}