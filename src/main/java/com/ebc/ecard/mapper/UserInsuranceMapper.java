package com.ebc.ecard.mapper;

import com.ebc.ecard.domain.insurance.UserInsuranceBean;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserInsuranceMapper {
    List<UserInsuranceBean> findByUserId(String userId);

    Integer createUserInsurance(UserInsuranceBean bean);

    Integer updateUserInsurancePublicYn(UserInsuranceBean bean);

    Integer deleteInsurance(String userId);
}
