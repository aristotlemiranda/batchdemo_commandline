package com.amm.batch.demo.configuration;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JobAppRunner  {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    BatchConfiguration batchConfiguration;

    public void run(ApplicationArguments args) throws JobExecutionException {
        System.out.println(" @JobAppRunner........................");

        if(args.getOptionValues("jobName").size() > 1) {
            throw new JobExecutionException("You can only run 1 job at a time");
        }

        System.out.println("args.getOptionValues(\"job-worflow\") = " + args.getOptionValues("job-worflow"));

        List<String> workFlows = Stream.of(args.getOptionValues("job-worflow").get(0).split(",")).collect(Collectors.toList());
        for (String workFlow : workFlows) {
            System.out.println("workFlow = " + workFlow);
        }
        
        batchConfiguration.testJob(args.getOptionValues("jobName").get(0));

        System.out.println("batchStat = " + BatchStatus.FAILED.ordinal());
        SpringApplication.exit(applicationContext, () -> 0);
    }
}
