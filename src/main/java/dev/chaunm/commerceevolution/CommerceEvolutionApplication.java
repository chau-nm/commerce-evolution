package dev.chaunm.commerceevolution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@ConfigurationPropertiesScan
public class CommerceEvolutionApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommerceEvolutionApplication.class, args);
	}

}
