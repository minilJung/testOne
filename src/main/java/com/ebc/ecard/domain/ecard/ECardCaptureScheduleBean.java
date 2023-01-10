package com.ebc.ecard.domain.ecard;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ECardCaptureScheduleBean {
	private String scheduleId;
	private String ecardId;
	private String status;
	private int retryCount;
	private Date createdAt;
	private Date processedAt;

	public static ECardCaptureScheduleBean create(String scheduleId, String ecardId) {
		return new ECardCaptureScheduleBean(
			scheduleId,
			ecardId,
			"P",
			0,
			new Date(),
			null
		);
	}

	public ECardCaptureScheduleBean(
		String scheduleId,
		String ecardId,
		String status,
		int retryCount,
		Date createdAt,
		Date processedAt
	) {
		this.scheduleId = scheduleId;
		this.ecardId = ecardId;
		this.status = status;
		this.retryCount = retryCount;
		this.createdAt = createdAt;
		this.processedAt = processedAt;
	}

	public void success() {
		this.status = "S";
		this.processedAt = new Date();
	}

}