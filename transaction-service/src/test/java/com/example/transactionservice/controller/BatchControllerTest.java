package com.example.transactionservice.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BatchControllerTest {

    @Mock
    private JobLauncher jobLauncher;

    @Mock
    private Job job;

    @InjectMocks
    private BatchController batchController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_RunBatchJob_Success() throws Exception {
        JobExecution jobExecution = mock(JobExecution.class);
        JobInstance jobInstance = mock(JobInstance.class);

        when(jobExecution.getJobInstance()).thenReturn(jobInstance);
        when(jobLauncher.run(any(Job.class), any(JobParameters.class))).thenReturn(jobExecution);

        ResponseEntity<String> response = batchController.runBatchJob();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Batch job completed.", response.getBody());
        verify(jobLauncher, times(1)).run(any(Job.class), any(JobParameters.class));
    }

    @Test
    void test_RunBatchJob_JobExecutionAlreadyRunningException() throws Exception {
        doThrow(JobExecutionAlreadyRunningException.class).when(jobLauncher).run(any(Job.class), any(JobParameters.class));

        ResponseEntity<String> response = batchController.runBatchJob();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Batch job unsuccessful. Please try again later.", response.getBody());
        verify(jobLauncher, times(1)).run(any(Job.class), any(JobParameters.class));
    }

    @Test
    void test_RunBatchJob_JobRestartException() throws Exception {
        doThrow(JobRestartException.class).when(jobLauncher).run(any(Job.class), any(JobParameters.class));

        ResponseEntity<String> response = batchController.runBatchJob();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Batch job unsuccessful. Please try again later.", response.getBody());
        verify(jobLauncher, times(1)).run(any(Job.class), any(JobParameters.class));
    }

    @Test
    void test_RunBatchJob_JobInstanceAlreadyCompleteException() throws Exception {
        doThrow(JobInstanceAlreadyCompleteException.class).when(jobLauncher).run(any(Job.class), any(JobParameters.class));

        ResponseEntity<String> response = batchController.runBatchJob();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Batch job unsuccessful. Please try again later.", response.getBody());
        verify(jobLauncher, times(1)).run(any(Job.class), any(JobParameters.class));
    }

    @Test
    void test_RunBatchJob_JobParametersInvalidException() throws Exception {
        doThrow(JobParametersInvalidException.class).when(jobLauncher).run(any(Job.class), any(JobParameters.class));

        ResponseEntity<String> response = batchController.runBatchJob();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Batch job unsuccessful. Please try again later.", response.getBody());
        verify(jobLauncher, times(1)).run(any(Job.class), any(JobParameters.class));
    }

}
