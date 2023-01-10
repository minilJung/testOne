package com.ebc.ecard.application.nftcv.service;

import com.ebc.ecard.application.nftcv.dto.NFTCvResponseDto;
import com.ebc.ecard.application.user.dto.EBAPRequestDto;
import com.ebc.ecard.application.nftcv.dto.request.NFTCVRequestDto;
import com.ebc.ecard.util.ReturnMessage;
import com.ebc.ecard.util.request.ECardHttpRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NftCvInternalServiceImpl implements NftCvInternalService {

    @Value("${ecard.services.ebap.url}")
    private String EBAP_URL;

    @Value("${ecard.services.ebap.service-id}")
    private String SERVICE_ID;

    @Resource(name = "applicationThreadPoolExecutor")
    private Executor applicationTaskExecutor;

    @Override
    public ReturnMessage saveEBAPAccount(String accountId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");

        NFTCvResponseDto response = ECardHttpRequest.Builder.build("https://" + EBAP_URL)
            .post("/am/v1/users")
            .payload(new EBAPRequestDto(accountId, SERVICE_ID))
//            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .executeAndGetBodyAs(NFTCvResponseDto.class);

        return new ReturnMessage(response);
    }

    @Async("applicationThreadPoolExecutor")
    @Override
    public CompletableFuture<ReturnMessage> saveOrUpdateUserCV(NFTCVRequestDto updateDto, String accountId) {
        try {
            NFTCvResponseDto nftCvDto = getUserCV(accountId);
            if (nftCvDto != null && nftCvDto.getData() != null) {
                updateUserCV(updateDto, accountId)
                    .thenAccept(it -> {
                        log.info("Success to update nft cv accountId: {}", accountId);
                    })
                    .exceptionally(error -> {
                        log.info("Failed to update nft cv accountId: {}, error: {}", accountId, error.getMessage());
                        return null;
                    });
            }
        } catch(HttpClientErrorException e) {
            log.info("Error while get NFT CV {}", e.getMessage(), e);
            if (!e.getStatusCode().equals(HttpStatus.NOT_FOUND) || !e.getMessage().contains("The fpCV is not exist")) {
                throw e;
            }

            saveUserCV(updateDto, accountId)
                .thenAccept(it -> {
                    log.info("Success to save nft cv accountId: {}", accountId);
                })
                .exceptionally(error -> {
                    log.info("Failed to save nft cv accountId: {}, error: {}", accountId, error.getMessage());
                    return null;
                });
        }

        return CompletableFuture.completedFuture(new ReturnMessage());
    }

    private CompletableFuture<Void> saveUserCV(NFTCVRequestDto updateDto, String accountId) {
        return CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {

                HttpHeaders headers = new HttpHeaders();
                headers.set("Service-Id", SERVICE_ID);

                log.info("Save NFT CV Info to {}", "https://" + EBAP_URL + "/smfp/v1/fp/" + accountId + "/profile");

                ECardHttpRequest.Builder.build("https://" + EBAP_URL)
                    .post("/smfp/v1/fp/" + accountId + "/profile")
                    .payload(updateDto)
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_JSON)
                    .executeAndGetBodyAs(NFTCvResponseDto.class);
            }
        }, applicationTaskExecutor);
    }

    private CompletableFuture<Void> updateUserCV(NFTCVRequestDto updateDto, String accountId) {
        return CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {

                HttpHeaders headers = new HttpHeaders();
                headers.set("Service-Id", SERVICE_ID);

                log.info("Update NFT CV Info to {}", "https://" + EBAP_URL + "/smfp/v1/fp/" + accountId + "/profile");

                ECardHttpRequest.Builder.build("https://" + EBAP_URL)
                    .put("/smfp/v1/fp/" + accountId + "/profile")
                    .payload(updateDto)
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_JSON)
                    .executeAndGetBodyAs(NFTCvResponseDto.class);
            }
        }, applicationTaskExecutor);

    }

    @Override
    public NFTCvResponseDto getUserCV(String accountId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Service-Id", SERVICE_ID);

        log.info("Get NFT CV Info to {}", "https://" + EBAP_URL + "/smfp/v1/fp/" + accountId + "/profile");

        return ECardHttpRequest.Builder.build("https://" + EBAP_URL)
            .get("/smfp/v1/fp/" + accountId + "/profile")
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .executeAndGetBodyAs(NFTCvResponseDto.class);

    }

}