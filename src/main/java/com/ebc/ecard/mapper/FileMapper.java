package com.ebc.ecard.mapper;

import com.ebc.ecard.util.FileBean;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {
	Integer saveFiles(List<FileBean> files);
}
