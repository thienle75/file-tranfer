package com.github.thienle75.file_transfer.server;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@ComponentScan("com.github.thienle75.file_transfer.server")
public class App {
	public static void main(String[] args) {
		Arrays.stream(SpringApplication.run(App.class, args).getBeanDefinitionNames())
				.forEach(beanName -> System.out.println(beanName));
	}
}
