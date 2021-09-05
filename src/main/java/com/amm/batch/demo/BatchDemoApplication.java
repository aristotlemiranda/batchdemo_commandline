package com.amm.batch.demo;

import com.amm.batch.demo.configuration.JobAppRunner;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableBatchProcessing
public class BatchDemoApplication implements ApplicationRunner {

	@Autowired
	JobAppRunner jobAppRunner;

	public static void main(String[] args) {
		SpringApplication.run(BatchDemoApplication.class, args);
	}



	@Override
	public void run(ApplicationArguments args) throws Exception {
		jobAppRunner.run(args);
	}


}
