package com.abimulia.hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class HotelBackApplication {

	public static void main(String[] args) {
		log.debug("########################################");
		log.debug("main()");
		SpringApplication.run(HotelBackApplication.class, args);
	}

}
