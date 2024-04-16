package vttp.batch4.csf.toiletnearme.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import vttp.batch4.csf.toiletnearme.models.Coordinates;

// CONSUMER
@Service
public class KafkaReceiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaReceiver.class);

	// {"id":"123","firstName":"John","lastName":"Doe","department":"IT"}
	@KafkaListener(topics = "${kafka.topic.name}", groupId = "${kafka.consumer.group.id}")
	public void recieveData(Coordinates coordinates) {
		LOGGER.info("Data - " + coordinates.toString() + " recieved");
		// ToiletLocation location = new ToiletLocation("1", "name", "name2", "dept");
		// LOGGER.info("Test - " + location.toString() + " recieved");
	}
}