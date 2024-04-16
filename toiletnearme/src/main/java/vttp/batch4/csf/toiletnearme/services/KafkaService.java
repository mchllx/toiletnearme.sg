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

import vttp.batch4.csf.toiletnearme.models.Coordinates;

// PRODUCER
@Service
public class KafkaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaService.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Value("${kafka.topic.name}")
	private String topicName;

	// 	{"id":"123","firstName":"John","lastName":"Doe","department":"IT"}
	public void sendData(Coordinates coordinates) {
		Map<String, Object> headers = new HashMap<>();
		headers.put(KafkaHeaders.TOPIC, topicName);
		kafkaTemplate.send(new GenericMessage<Coordinates>(coordinates, headers));

		// kafkaTemplate.send(topicName, "some string value")
		LOGGER.info("Data - " + coordinates.toString() + " sent to Kafka Topic - " + topicName);
	}
}
