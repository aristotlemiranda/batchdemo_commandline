package com.amm.batch.demo.validator;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;

public class JobParamValidator implements JobParametersValidator {
    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        System.out.println("JobParamValidator..... validate...");
        System.out.println("parameters = " + parameters);
        throw new JobParametersInvalidException("Trying to invalidate the job parameter.");
    }
}
