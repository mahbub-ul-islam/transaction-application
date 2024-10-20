package com.example.transactionservice.config.batch;

import com.example.transactionservice.exception.errors.batch.BatchJobFailureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomJobExecutionListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Executing batch job "+ jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus().isUnsuccessful()) {
            throw new BatchJobFailureException("Batch job failed: " + jobExecution.getAllFailureExceptions());
        }
        log.info("Finishing batch job " + jobExecution.getJobInstance().getJobName());
    }
}
