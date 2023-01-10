package com.ebc.ecard.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnMessage {
	
	/**
	 * @title	- API 결과코드
	 * @author	- jinyeon Jo
	 * @date	- 2021.12.30
	 * @param	- 0000	: 성공
	 * @param	- 9xxx	: 실패
	 * @param	- 9999	: 실패
	 */
	private String result;
	private String message;		// 결과메세지
	private Object value;
	
	
	public ReturnMessage() {
		this.result = "0000";
		this.message = "성공";
		this.value = null;
	}
	
	public ReturnMessage(Object value) {
		this.result = "0000";
		this.message = "성공";
		this.value = value;
	}
	
	public ReturnMessage(String result, String message, Object value) {
		this.result = result;
		this.message = message;
		this.value = value;
	}
	
	public ReturnMessage(String result, String message, Exception e) {
		e.printStackTrace();
		
		this.result = result;
		this.message = message;
		this.value = e.toString();
	}
	
	public ReturnMessage emptySaveData() {
		this.result = "9999";
		this.message = "저장된 데이터가 없습니다";
		this.value = null;
		return this;
	}
}
