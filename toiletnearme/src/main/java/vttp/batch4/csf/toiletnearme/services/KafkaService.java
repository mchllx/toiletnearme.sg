package vttp.batch4.csf.toiletnearme.services;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import vttp.batch4.csf.toiletnearme.models.ToiletLocation;

// PRODUCER
@Service
public class KafkaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaService.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Value("${kafka.topic.name}")
	private String topicName;

	public void sendData(ToiletLocation toiletLocation) {
		Map<String, Object> headers = new HashMap<>();
		headers.put(KafkaHeaders.TOPIC, topicName);
		kafkaTemplate.send(new GenericMessage<ToiletLocation>(toiletLocation, headers));
		// use the below to send String values through kafka
		// kafkaTemplate.send(topicName, "some string value")
		LOGGER.info("Data - " + toiletLocation.toString() + " sent to Kafka Topic - " + topicName);
	}
}
