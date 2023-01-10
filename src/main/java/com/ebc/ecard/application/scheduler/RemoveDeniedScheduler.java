package com.ebc.ecard.application.scheduler;

import com.ebc.ecard.application.activity.dto.ActivityDeleteRequestDto;
import com.ebc.ecard.application.activity.dto.ActivityFilterDto;
import com.ebc.ecard.mapper.UserEducationMapper;
import com.ebc.ecard.mapper.UserRwpsMapper;
import com.ebc.ecard.domain.activity.value.ActivityType;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableScheduling
public class RemoveDeniedScheduler {

    @Resource
    UserRwpsMapper rwpsMapper;
    @Resource
    UserEducationMapper educationMapper;

    @Scheduled(cron = "0 0 0 * * *")
    public void removeDeniedRwps() {

        try {
            rwpsMapper.findRwpsBySpecification(
                new ActivityFilterDto(
                    null,
                    null
                    //Collections.singletonList(ApprovalStatus.DENIED)
                )
            ).forEach(it -> {
                ActivityDeleteRequestDto deleteDto = new ActivityDeleteRequestDto(it.getRwpsId(), ActivityType.RWPS);
                rwpsMapper.deleteRwps(deleteDto);
            });
            log.info("Scheduler finish - Remove denied rwps");
        } catch (Exception e) {
            log.info("Scheduler error - Remove denied rwps {}", e.getMessage());
        }

    }

    @Scheduled(cron = "0 0 0 * * *")
    public void removeDeniedEducation() {

        try {
            educationMapper.findEducationBySpecification(
                new ActivityFilterDto(
                    null,
                    null
                    //Collections.singletonList(ApprovalStatus.DENIED)
                )
            ).forEach(it -> {
                ActivityDeleteRequestDto deleteDto = new ActivityDeleteRequestDto(it.getEducationId(), ActivityType.EDUCATION);
                educationMapper.deleteEducation(deleteDto);
            });
            log.info("Scheduler finish - Remove denied education");
        } catch (Exception e) {
            log.info("Scheduler error - Remove denied education {}", e.getMessage());
        }

    }
}
