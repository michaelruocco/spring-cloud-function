package uk.co.mruoc;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.TableNameOverride;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverterFactory;
import lombok.extern.slf4j.Slf4j;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableDynamoDBRepositories(
        dynamoDBMapperConfigRef = "dynamoDBMapperConfig",
        basePackages = {"uk.co.mruoc.repository"}
)
@Slf4j
@Profile("!local")
public class DefaultDynamoDbConfig {

    @Value("${REGION}")
    private String region;

    @Value("${STAGE}")
    private String stage;

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        log.info("Setting up default dynamodb config");
        return AmazonDynamoDBClientBuilder.standard().withRegion(region).build();
    }

    @Bean
    public AWSCredentials amazonAwsCredentials() {
        return new DefaultAWSCredentialsProviderChain().getCredentials();
    }

    @Bean
    public DynamoDBMapperConfig dynamoDBMapperConfig(TableNameOverride tableNameOverrider) {
        return new DynamoDBMapperConfig.Builder()
                .withTableNameOverride(tableNameOverrider)
                .withTypeConverterFactory(DynamoDBTypeConverterFactory.standard())
                .build();
    }

    @Bean
    public TableNameOverride tableNameOverrider() {
        String prefix = stage + "-";
        return TableNameOverride.withTableNamePrefix(prefix);
    }

}