package vttp.batch4.csf.toiletnearme.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp.batch4.csf.toiletnearme.models.ToiletLocation;
import vttp.batch4.csf.toiletnearme.services.KafkaReceiver;
import vttp.batch4.csf.toiletnearme.services.KafkaService;

// PRODUCER
@RestController
@RequestMapping
public class KafkaController {

	@Autowired
	private KafkaReceiver receiver;

	@Autowired
	private KafkaService sender;
	
	@PostMapping("/kafkaProducer")
	public ResponseEntity<String> sendData(@RequestBody ToiletLocation toiletLocation){
		sender.sendData(toiletLocation);
		return new ResponseEntity<>("Data sent to Kafka", HttpStatus.OK);
	}

	@GetMapping("/kafkaReceiver")
	public ResponseEntity<String> receiveData(@RequestBody ToiletLocation toiletLocation){
		receiver.recieveData(toiletLocation);
		return new ResponseEntity<>("Data received from Kafka", HttpStatus.OK); 
	}
}