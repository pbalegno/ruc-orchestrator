
package com.guru.ruc.orchestrator.listener;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.config.annotation.NotificationMessage;
import org.springframework.cloud.aws.messaging.config.annotation.NotificationSubject;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.guru.ruc.orchestrator.config.Constants;
import com.guru.ruc.orchestrator.domain.Advertise;
import com.guru.ruc.orchestrator.service.AdvertiseService;

@Component
public class NotificationListener {

	private static final Logger LOG = LoggerFactory.getLogger(NotificationListener.class);

	private final NotificationMessagingTemplate notificationMessagingTemplate;
	private final AdvertiseService advertiseService;

	public NotificationListener(NotificationMessagingTemplate notificationMessagingTemplate, AdvertiseService advertiseService) {
		this.notificationMessagingTemplate = notificationMessagingTemplate;
		this.advertiseService = advertiseService;
	}
	
	@SqsListener(value = "advertisement-input", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
	private void processMessage(
			@NotificationMessage Advertise message, 
			@NotificationSubject String subject, 
			@Headers Map<String, Object> headers,
			@Payload String payload) {

		LOG.info("Received SQS message {} - headers {}", message, headers);
		
		Advertise advertise = advertiseService.save(message);
		
		LOG.info("Advertise Created  ID  {} ", advertise.getId());
		
		this.notificationMessagingTemplate.sendNotification("new-advertisement", advertise, Constants.NEW_ADVERTISEMENT);
	}

}
