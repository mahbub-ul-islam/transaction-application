package com.example.transactionservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@Tag(name = "Transaction Batch API")
public class BatchController {

    private final JobLauncher jobLauncher;
    private final Job job;

    @PostMapping("/batch-jobs")
    @Operation(summary = "Run Transaction Batch Job")
    public ResponseEntity<String> runBatchJob() {

        log.info("Executing Batch Job Start.");

        JobParameters jobParameters =
                new JobParametersBuilder()
                        .addLong("startAt", System.currentTimeMillis())
                        .toJobParameters();

        try {
            jobLauncher.run(job, jobParameters);
            log.info("Executing Batch Job Complete.");
            return ResponseEntity.status(HttpStatus.OK).body("Batch job completed.");
        } catch (JobExecutionAlreadyRunningException
                 | JobRestartException
                 | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException e) {
            log.error("{}: {} Cause: {} StackTrace: {}", e.getClass().getName(), e.getMessage(), e.getCause(), Arrays.toString(e.getStackTrace()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Batch job unsuccessful. Please try again later.");
        }
    }
}