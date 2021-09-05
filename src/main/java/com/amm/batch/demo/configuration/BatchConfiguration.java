package com.amm.batch.demo.configuration;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class BatchConfiguration {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    JobBuilderFactory jobFactory;


    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    ApplicationContext context;


    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    StepExecListener stepExecutionListener;


    @Bean("step1")
    public Step step1() {
        return stepBuilderFactory.get("step1").tasklet(new Tasklet() {

            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("This is step 1 running....");
                return RepeatStatus.FINISHED;
            }
        }).build();
    };

    @Bean("step2")
    public Step step2() {
        return stepBuilderFactory.get("step1").tasklet(new Tasklet() {

            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("This is step 2 running....");
                return RepeatStatus.FINISHED;
            }
        }).build();
    };

    @Bean("step3")
    public Step step3() {
        return stepBuilderFactory.get("step1").tasklet(new Tasklet() {

            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("This is step 3 running....");
                return RepeatStatus.FINISHED;
            }
        }).build();
    };

    @StepScope
    public Step createStep(String stepName) {
        /*
         * StepBuilder stepBuilder = new StepBuilder(stepName);
         * stepBuilder.tasklet(createTaskLet()).build().setJobRepository(jobRepository);
         */

        return stepBuilderFactory.get(stepName).listener(new StepExecListener()).tasklet((Tasklet) context.getBean(stepName)).build();

    }

    @StepScope
    @Bean("customStep1")
    public Tasklet createTaskLet1() {
        return new Tasklet() {

            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("Completed....." + chunkContext.getStepContext().getStepName());
//					if(true) {
//						throw new Exception("I demand some error...");
//					}
                return RepeatStatus.FINISHED;
            }
        };
    }

    @StepScope
    @Bean("customStep2")
    public Tasklet createTaskLet2() {
        return new Tasklet() {

            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("Completed....." + chunkContext.getStepContext().getStepName());

//					if(true) {
//						throw new Exception("I demand some error...");
//					}

                return RepeatStatus.FINISHED;
            }
        };
    }

    public String testJob() {
        System.out.println("Running... testJob... ");
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        Step step1 = (Step) context.getBean("step1");


        List<String> steps = new ArrayList<>();
        steps.add("step2");
        steps.add("step3");

        JobBuilder jb = new JobBuilder("job1");
        SimpleJobBuilder sjb = jb.start(step1);

        /*for(String s : steps) {
            Step step = (Step) context.getBean(s);
            sjb.next(step);
        }*/
        sjb.next(this.createStep("customStep2"));

        Job job = sjb.repository(jobRepository).build();

        try {
            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "BATCH COMPLETED";
    }
}
