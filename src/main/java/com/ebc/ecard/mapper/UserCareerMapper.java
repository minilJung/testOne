package com.ebc.ecard.mapper;

import com.ebc.ecard.domain.career.UserCareerBean;
import com.ebc.ecard.application.career.dto.CareerFilterDto;
import com.ebc.ecard.application.career.dto.CareerInsight;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserCareerMapper {
    Integer addCareer(UserCareerBean bean);


    List<UserCareerBean> findByUserId(String userId);

    List<UserCareerBean> findCareerBySpecification(CareerFilterDto filter);
    CareerInsight getInsightByUserId(String userId);

    UserCareerBean getCareerByCareerId(String careerId);
    Integer updateCareer(UserCareerBean bean);

    Integer deleteCareer(String userId);

    Integer updateCvCareer(UserCareerBean bean);
    
}
