package com.example.transactionservice.config.batch;

import com.example.transactionservice.entity.Transaction;
import com.example.transactionservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final TransactionRepository repository;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    @Autowired
    private JobExecutionListener customJobExecutionListener;
//    @Bean
//    public FlatFileItemReader<Transaction> reader() {
//        FlatFileItemReader<Transaction> itemReader = new FlatFileItemReader<>();
//        itemReader.setResource(new ClassPathResource("batch/dataSource.txt"));
//        itemReader.setName("txtReader");
//        itemReader.setLinesToSkip(1);
//        itemReader.setLineMapper(lineMapper());
//
//        return itemReader;
//    }

    @Bean
    public TransactionReader reader() {
        return new TransactionReader();
    }

    @Bean
    public TransactionProcessor processor() {
        return new TransactionProcessor();
    }

//    @Bean
//    public RepositoryItemWriter<Transaction> writer() {
//        RepositoryItemWriter<Transaction> itemWriter = new RepositoryItemWriter<>();
//        itemWriter.setRepository(repository);
//        itemWriter.setMethodName("save");
//        return itemWriter;
//    }

    @Bean
    public TransactionWriter writer() {
        return new TransactionWriter(repository);
    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);

        return asyncTaskExecutor;
    }

    @Bean
    public Step step1() {
        return new StepBuilder("txtImport", jobRepository)
                .<Transaction, Transaction>chunk(10, platformTransactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Job runJob(com.example.transactionservice.config.batch.JobExecutionListener jobExecutionListener) {
        return new JobBuilder("importTransactions", jobRepository)
                .listener(jobExecutionListener)
                .start(step1())
                .build();
    }


//    private LineMapper<Transaction> lineMapper() {
//        DefaultLineMapper<Transaction> lineMapper = new DefaultLineMapper<>();
//
//        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
//        tokenizer.setDelimiter("|");
//        tokenizer.setStrict(false);
//        tokenizer.setNames("ACCOUNT_NUMBER", "TRX_AMOUNT", "DESCRIPTION", "TRX_DATE", "TRX_TIME", "CUSTOMER_ID");
//
//        BeanWrapperFieldSetMapper<Transaction> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
//        fieldSetMapper.setTargetType(Transaction.class);
//
//        lineMapper.setLineTokenizer(tokenizer);
//        lineMapper.setFieldSetMapper(fieldSetMapper);
//
//        return lineMapper;
//    }


}
