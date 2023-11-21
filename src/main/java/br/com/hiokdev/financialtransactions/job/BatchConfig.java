package br.com.hiokdev.financialtransactions.job;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import br.com.hiokdev.financialtransactions.domain.entity.CNABTransaction;
import br.com.hiokdev.financialtransactions.domain.entity.Transaction;
import br.com.hiokdev.financialtransactions.domain.entity.TransactionType;

@Configuration
public class BatchConfig {
  
  private final PlatformTransactionManager platformTransactionManager;
  private final JobRepository jobRepository;

  public BatchConfig(
    PlatformTransactionManager platformTransactionManager,
    JobRepository jobRepository
  ) {
    this.platformTransactionManager = platformTransactionManager;
    this.jobRepository = jobRepository;
  }

  @Bean
  public Job job(Step step) {
    return new JobBuilder("job", jobRepository)
      .start(step)
      .incrementer(new RunIdIncrementer())
      .build();
  }

  @Bean
  public Step step(
    ItemReader<CNABTransaction> reader,
    ItemProcessor<CNABTransaction, Transaction> processor,
    ItemWriter<Transaction> writer
  ) {
    return new StepBuilder("step", jobRepository)
      .<CNABTransaction, Transaction>chunk(1000, platformTransactionManager)
      .reader(reader)
      .processor(processor)
      .writer(writer)
      .build();
  }

  @Bean
  @StepScope
  public FlatFileItemReader<CNABTransaction> reader(
    @Value("#{jobParameters['cnabFile']}") Resource resource
  ) {
    return new FlatFileItemReaderBuilder<CNABTransaction>()
      .name("reader")
      .resource(resource)
      .fixedLength()
      .columns(
        new Range(1,1),
        new Range(2, 9),
        new Range(10, 19),
        new Range(20, 30),
        new Range(31, 42),
        new Range(43, 48),
        new Range(49, 62),
        new Range(63,80)
      )
      .names(
        "type",
        "date",
        "amount",
        "cpf",
        "card",
        "time",
        "storeOwner",
        "storeName"
      )
      .targetType(CNABTransaction.class)
      .build();
  }

  @Bean
  public ItemProcessor<CNABTransaction, Transaction> processor() {
    return item -> {
      var transactionType = TransactionType.findByType(item.type());
      var normalizedAmount = item.amount()
        .divide(BigDecimal.valueOf(100))
        .multiply(transactionType.getSignal());

      var transaction = new Transaction(
        null,
        item.type(),
        null,
        normalizedAmount,
        item.cpf(),
        item.card(),
        null,
        item.storeOwner().trim(),
        item.storeName().trim()
      )
      .withDate(item.date())
      .withTime(item.time());

      return transaction;
    };
  }

  @Bean
  public JdbcBatchItemWriter<Transaction> writer(DataSource dataSource) {
    return new JdbcBatchItemWriterBuilder<Transaction>()
      .dataSource(dataSource)
      .sql("""
        INSERT INTO transactions (type, date, amount, cpf, card, time, store_owner, store_name)
        VALUES(:type, :date, :amount, :cpf, :card, :time, :storeOwner, :storeName)
      """)
      .beanMapped()
      .build();
  }

  @Bean
  public JobLauncher jobLauncherAsync(JobRepository jobRepository) throws Exception {
    var jobLauncher = new TaskExecutorJobLauncher();
    jobLauncher.setJobRepository(jobRepository);
    jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
    jobLauncher.afterPropertiesSet();
    
    return jobLauncher;
  }

}
