package com.ebc.ecard.util;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseBean {
	List<FileBean> files;
	
	public String toStringMultiLine() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	public String toStringJson() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
