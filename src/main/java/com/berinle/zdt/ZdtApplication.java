package com.berinle.zdt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalTime;

@SpringBootApplication
public class ZdtApplication {

	private static final Logger LOG = LoggerFactory.getLogger(ZdtApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ZdtApplication.class, args);
	}

	@RestController
	class SampleController {
		private final RabbitTemplate rabbitTemplate;
		private final SampleConfigProps sampleConfigProps;

	    @Autowired
		public SampleController(RabbitTemplate rabbitTemplate, SampleConfigProps sampleConfigProps) {
			this.rabbitTemplate = rabbitTemplate;
			this.sampleConfigProps = sampleConfigProps;
		}

		@GetMapping("/greet")
		public ResponseEntity<String> greet() throws UnknownHostException {
			LOG.debug("this is a DEBUG statement");
			LOG.info("this is an INFO statement");
			LOG.error("this is an ERROR statement");

			LOG.info("IP Address: {}, Thread: {}, Time: {}",
					InetAddress.getLocalHost(),
					Thread.currentThread().getName(),
					LocalTime.now());

			return ResponseEntity.ok("hello world");
		}

		@PostMapping("/greet")
		public void dummyPost() throws UnknownHostException {
			LOG.info("IP Address: {}, Thread: {}, Time: {}",
					InetAddress.getLocalHost(),
					Thread.currentThread().getName(),
					LocalTime.now());
		}

		@PostMapping("/msg")
		public void sendMessage(@RequestBody(required = false) String msg) {
			LOG.info("Sending message...");
			rabbitTemplate.convertAndSend(PubSub.topicExchangeName, "foo.bar.baz", msg != null ? msg: "Hello from RabbitMQ!");
		}

		@GetMapping("/amqp")
		public ResponseEntity<?> getRabbitMQProps() {
	    	LOG.info("Dashboard URL: {}, Hostname: {}", sampleConfigProps.getDashboardUrl(), sampleConfigProps.getHostname());
	    	return ResponseEntity.ok(sampleConfigProps.getDashboardUrl());
		}
	}

}
