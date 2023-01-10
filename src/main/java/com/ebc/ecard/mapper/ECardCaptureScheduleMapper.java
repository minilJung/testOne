package com.ebc.ecard.mapper;

import com.ebc.ecard.application.ecard.dto.ECardCaptureScheduleFilterDto;
import com.ebc.ecard.domain.ecard.ECardCaptureScheduleBean;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ECardCaptureScheduleMapper {

	int addScheduleById(ECardCaptureScheduleBean schedule);

	ECardCaptureScheduleBean findScheduleById(String scheduleId);

	List<ECardCaptureScheduleBean> findRemainSchedules(ECardCaptureScheduleFilterDto filter);

	int updateSchedule(ECardCaptureScheduleBean schedule);

	int cancelRemainingSchedules(String ecardId);

}
