package com.ebc.ecard.mapper;

import com.ebc.ecard.domain.ecard.ECardTemplateBean;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ECardTemplateMapper {

	List<ECardTemplateBean> getBackgrounds();

	ECardTemplateBean getBackground(int templateNo);
}
