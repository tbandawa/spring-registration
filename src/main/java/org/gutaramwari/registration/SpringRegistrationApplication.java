package org.gutaramwari.registration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaAuditing
@ImportResource({ "classpath:application.context.xml" })
public class SpringRegistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRegistrationApplication.class, args);
	}

}
