package com.example.cardapp;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CardConfig {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
