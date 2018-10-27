package uk.co.mruoc;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.TableNameOverride;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories(
        dynamoDBMapperConfigRef = "dynamoDBMapperConfig",
        basePackages = {"uk.co.mruoc.repository"}
)
public class DefaultDynamoDbConfig {

    @Value("${REGION}")
    private String region;

    @Value("${STAGE}")
    private String stage;

    @Bean
    @ConditionalOnMissingBean
    public AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder.standard().withRegion(region).build();
    }

    @Bean
    @ConditionalOnMissingBean
    public AWSCredentials amazonAwsCredentials() {
        return new DefaultAWSCredentialsProviderChain().getCredentials();
    }

    @Bean
    public DynamoDBMapperConfig dynamoDBMapperConfig(TableNameOverride tableNameOverrider) {
        DynamoDBMapperConfig.Builder builder = new DynamoDBMapperConfig.Builder();
        builder.setTableNameOverride(tableNameOverrider);
        return builder.build();
    }

    @Bean
    public TableNameOverride tableNameOverrider() {
        String prefix = stage + "-";
        return TableNameOverride.withTableNamePrefix(prefix);
    }

}