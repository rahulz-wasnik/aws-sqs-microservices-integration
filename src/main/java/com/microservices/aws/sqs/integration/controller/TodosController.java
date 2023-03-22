package com.microservices.aws.sqs.integration.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.microservices.aws.sqs.integration.vo.Todos;

@RestController
@RequestMapping(path = "/fetchAndSend")
public class TodosController {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private QueueMessagingTemplate queueMessagingTemplate;
	
	@Value("${cloud.aws.end-point.uri}")
    private String endPoint;
	
	@GetMapping
	private Todos fetchAndSend() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Todos> entity = new HttpEntity<Todos>(headers);
		
		Todos todos = restTemplate.exchange("https://jsonplaceholder.typicode.com/todos/1", 
				HttpMethod.GET, entity, Todos.class).getBody();
		
		queueMessagingTemplate.send(endPoint, MessageBuilder.withPayload(todos).build());
	
		return todos;
	}
}
