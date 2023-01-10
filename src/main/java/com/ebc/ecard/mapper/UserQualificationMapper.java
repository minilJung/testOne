package com.ebc.ecard.mapper;

import com.ebc.ecard.domain.qualification.UserQualificationBean;
import com.ebc.ecard.application.qualification.dto.QualificationFilterDto;
import com.ebc.ecard.application.qualification.dto.QualificationInsight;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserQualificationMapper {

    int createQualification(UserQualificationBean bean);

    List<UserQualificationBean> findByUserId(String userId);

    List<UserQualificationBean> findUserQualificationBySpecification(QualificationFilterDto filter);

    QualificationInsight getInsightByUserId(String userId);

    UserQualificationBean findUserQualificationByQualificationId(String qualificationId);

    Integer updateQualification(UserQualificationBean bean);

    Integer deleteQualification(String userId);

    Map<String, Object> getQnetScrapingStatus(String userId);
}
