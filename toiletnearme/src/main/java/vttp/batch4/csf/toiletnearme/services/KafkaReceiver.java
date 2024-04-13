package vttp.batch4.csf.toiletnearme.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import vttp.batch4.csf.toiletnearme.models.ToiletLocation;

// CONSUMER
@Service
public class KafkaReceiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaReceiver.class);

	@KafkaListener(topics = "${kafka.topic.name}", groupId = "${kafka.consumer.group.id}")
	public void recieveData(ToiletLocation toiletLocation) {
		LOGGER.info("Data - " + toiletLocation.toString() + " recieved");
	}
}