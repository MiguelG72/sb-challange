package com.challenge.catalogue.repository.config;

import java.nio.charset.StandardCharsets;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.FileCopyUtils;

@Configuration
public class DataInitializer {



	@Bean
	@Profile(value = "dev")
	public CommandLineRunner insertData(JdbcTemplate jdbcTemplate){
		return args -> {
            Resource resource = new ClassPathResource("scripts/data.sql");
            String sql = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()), StandardCharsets.UTF_8);

            jdbcTemplate.execute(sql);
        };
	}
}
