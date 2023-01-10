package com.ebc.ecard.domain.ecard;

import java.time.Instant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerAccessLogBean {
	private String ecardId;         // fp 아이디
	private String fpId;         // fp 아이디
	private String custId;       // 고객 아이디
	private String uri;       // 고객 아이디
	private Instant accessedAt;

	public CustomerAccessLogBean(String ecardId, String custId, String uri, Instant accessedAt) {
		this.ecardId = ecardId;
		this.custId = custId;
		this.uri = uri;
		this.accessedAt = accessedAt;
	}

	public static CustomerAccessLogBean access(String ecardId, String custId, String uri) {
		return new CustomerAccessLogBean(
			ecardId,
			custId,
			uri,
			Instant.now()
		);
	}
}