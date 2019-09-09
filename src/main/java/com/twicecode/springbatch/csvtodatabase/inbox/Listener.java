package com.twicecode.springbatch.csvtodatabase.inbox;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class Listener extends JobExecutionListenerSupport {

    private static final Logger logger = LoggerFactory.getLogger(Listener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("Job with id: {} has been started", jobExecution.getJobId());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        logger.info("Job with id: {} has been  finished with status {}", jobExecution.getJobId(), jobExecution.getStatus());
    }
}