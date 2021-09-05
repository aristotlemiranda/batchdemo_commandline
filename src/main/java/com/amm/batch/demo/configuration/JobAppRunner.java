package com.amm.batch.demo.configuration;

import org.springframework.batch.core.BatchStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class JobAppRunner  {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    BatchConfiguration batchConfiguration;

    public void run(ApplicationArguments args) throws Exception {
        System.out.println(" @JobAppRunner........................");

        for (String optionName : args.getOptionNames()) {
            System.out.println("optionName=" + optionName + " value=" + args.getOptionValues(optionName));
        }

        batchConfiguration.testJob();

        System.out.println("batchStat = " + BatchStatus.FAILED.ordinal());
        SpringApplication.exit(applicationContext, () -> 0);
    }
}
