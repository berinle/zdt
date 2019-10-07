package com.berinle.zdt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
		@GetMapping("/greet")
		public ResponseEntity<String> greet() {
		public ResponseEntity<String> greet() throws UnknownHostException {
			LOG.debug("this is a DEBUG statement");
			LOG.info("this is an INFO statement");
			LOG.error("this is an ERROR statement");

			return ResponseEntity.ok("hello world");
			LOG.info("IP Address: {}, Thread: {}, Time: {}",
					InetAddress.getLocalHost(),
					Thread.currentThread().getName(),
					LocalTime.now());
		}
	}

}
