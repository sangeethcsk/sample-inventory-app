package com.sansys.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
public class InventoryApplication {

	public static void main(String[] args) {
		SpringApplication app =
				new SpringApplication(InventoryApplication.class);

		app.setApplicationStartup(
				new BufferingApplicationStartup(2048));

		app.run(args);
	}

}
