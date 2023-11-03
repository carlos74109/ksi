package com.ksi.consultoria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication//(exclude = {DataSourceAutoConfiguration.class })
public class ConsultoriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsultoriaApplication.class, args);
	}

}
