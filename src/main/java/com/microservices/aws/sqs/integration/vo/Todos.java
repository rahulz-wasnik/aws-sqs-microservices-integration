package com.microservices.aws.sqs.integration.vo;

import lombok.Data;

@Data
public class Todos {
	
	private Long userId;
	private Long id;
	private String title;
	private boolean completed;
}
