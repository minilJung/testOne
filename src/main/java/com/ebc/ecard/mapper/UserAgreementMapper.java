package com.ebc.ecard.mapper;

import com.ebc.ecard.domain.agreement.UserAgreementBean;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserAgreementMapper {
    List<UserAgreementBean> findByUserId(String userId);

    Integer createUserAgreement(UserAgreementBean bean);
}
