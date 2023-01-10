package com.ebc.ecard.mapper;

import com.ebc.ecard.domain.majority.UserMajorityBean;
import com.ebc.ecard.application.majority.dto.MajorityFilterDto;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMajorityMapper {
    Integer addMajority(UserMajorityBean bean);
    List<UserMajorityBean> findByUserId(String userId);

    List<UserMajorityBean> findMajorityBySpecification(MajorityFilterDto userId);
    UserMajorityBean getMajorityByMajorityId(String majorityId);
    Integer updateMajority(UserMajorityBean bean);

}
