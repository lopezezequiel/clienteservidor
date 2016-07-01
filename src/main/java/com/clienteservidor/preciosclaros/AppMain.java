package com.clienteservidor.preciosclaros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class AppMain {

	public static void main(String args[]) {
		SpringApplication.run(AppMain.class, args);

	}
}