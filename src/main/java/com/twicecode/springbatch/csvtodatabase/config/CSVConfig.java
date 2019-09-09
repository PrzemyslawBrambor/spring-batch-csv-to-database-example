package com.twicecode.springbatch.csvtodatabase.config;


import com.twicecode.springbatch.csvtodatabase.entity.Person;
import com.twicecode.springbatch.csvtodatabase.inbox.Listener;
import com.twicecode.springbatch.csvtodatabase.processor.CsvToDatabaseProcessor;
import com.twicecode.springbatch.csvtodatabase.writer.CsvToDatabaseWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@EnableBatchProcessing
@Configuration
public class CSVConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public CSVConfig(StepBuilderFactory stepBuilderFactory, JobBuilderFactory jobBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean
    public FlatFileItemReader<Person> csvReader() {
        FlatFileItemReader<Person> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("csv/exampleData.csv"));
        DefaultLineMapper<Person> defaultLineMapper = new DefaultLineMapper<Person>();

        BeanWrapperFieldSetMapper<Person> personBeanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        personBeanWrapperFieldSetMapper.setTargetType(Person.class);

        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames("id", "name", "surname", "age", "email");

        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(personBeanWrapperFieldSetMapper);

        reader.setLineMapper(defaultLineMapper);
        return reader;
    }

    @Bean
    ItemProcessor<Person, Person> csvProcessor() {
        return new CsvToDatabaseProcessor();
    }

    @Bean
    public CsvToDatabaseWriter csvWriter() {
        return new CsvToDatabaseWriter();
    }

    @Bean
    public Step csvFileToDatabaseStep() {
        return stepBuilderFactory.get("csvFileToDatabaseStep")
                .<Person, Person>chunk(1)
                .reader(csvReader())
                .processor(csvProcessor())
                .writer(csvWriter())
                .build();
    }

    //
    @Bean
    Job csvFileToDatabaseJob(Listener listener) {
        return jobBuilderFactory.get("csvFileToDatabaseJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(csvFileToDatabaseStep())
                .end()
                .build();
    }
}