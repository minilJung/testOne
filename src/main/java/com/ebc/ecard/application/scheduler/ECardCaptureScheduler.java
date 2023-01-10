package com.ebc.ecard.application.scheduler;

import com.ebc.ecard.application.ecard.handler.ECardCaptureImageHandler;
import com.ebc.ecard.application.ecard.dto.ECardCaptureScheduleFilterDto;
import com.ebc.ecard.domain.ecard.ECardCaptureScheduleBean;
import com.ebc.ecard.mapper.ECardCaptureScheduleMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableScheduling
public class ECardCaptureScheduler {


    @Resource
    ECardCaptureScheduleMapper captureScheduleMapper;
    @Resource
    ECardCaptureImageHandler captureImageHandler;

    @Resource
    ObjectMapper objectMapper;

    @Transactional
    @Scheduled(cron = "0 */10 * * * *")
    public void updateEcardCaptureImage() {
        log.info("Scheduler Start - update capture image");
        try {

            List<ECardCaptureScheduleBean> list = captureScheduleMapper.findRemainSchedules(
                new ECardCaptureScheduleFilterDto(50)
            );
            list.forEach(captureImageHandler::updateECardCaptureImageUsingScheduleBean);

            log.info("Scheduler finish - update capture image");
        } catch (Exception e) {
            log.info("Scheduler error - update capture image {}", e.getMessage());
        }
    }

}
