package uk.co.mruoc;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverterFactory;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import lombok.extern.slf4j.Slf4j;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import uk.co.mruoc.model.Widget;

@Configuration
@EnableDynamoDBRepositories(
        dynamoDBMapperConfigRef = "dynamoDBMapperConfig",
        basePackages = {"uk.co.mruoc.repository"}
)
@Slf4j
@Profile("local")
public class LocalDynamoDbConfig {

    private static final String ACCESS_KEY = "access123";
    private static final String SECRET_KEY = "secret123";
    private static final String REGION = "eu-west-1";
    private static final String STAGE = "local";

    @Value("${aws.dynamodb.endpoint:http://db:8000/}")
    private String endpoint;

    @Bean
    @Profile("local")
    public AmazonDynamoDB amazonDynamoDB(AWSCredentials credentials) {
        log.info("Setting up local dynamodb config with endpoint {}", endpoint);
        return AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(new EndpointConfiguration(endpoint, REGION))
                .build();
    }

    @Bean
    public AWSCredentials amazonAwsCredentials() {
        return new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
    }

    @Bean
    public ApplicationListener<ContextRefreshedEvent> localDbTableSetup(AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig dynamoDBMapperConfig) {
        return new LocalTableSetup(amazonDynamoDB, dynamoDBMapperConfig);
    }

    @Bean
    public DynamoDBMapperConfig dynamoDBMapperConfig(DynamoDBMapperConfig.TableNameOverride tableNameOverrider) {
        return new DynamoDBMapperConfig.Builder()
                .withTableNameOverride(tableNameOverrider)
                .withTypeConverterFactory(DynamoDBTypeConverterFactory.standard())
                .build();
    }

    @Bean
    public DynamoDBMapperConfig.TableNameOverride tableNameOverrider() {
        String prefix = STAGE + "-";
        return DynamoDBMapperConfig.TableNameOverride.withTableNamePrefix(prefix);
    }

    @Slf4j
    private static class LocalTableSetup implements ApplicationListener<ContextRefreshedEvent> {

        private final AmazonDynamoDB amazonDynamoDB;
        private final DynamoDBMapperConfig dynamoDBMapperConfig;

        public LocalTableSetup(AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig dynamoDBMapperConfig) {
            this.amazonDynamoDB = amazonDynamoDB;
            this.dynamoDBMapperConfig = dynamoDBMapperConfig;
        }

        @Override
        public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
            log.info("setting up local dynamo db tables if they dont already exist");
            DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB, dynamoDBMapperConfig);

            CreateTableRequest request = dynamoDBMapper.generateCreateTableRequest(Widget.class);
            request.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
            log.info("running create table request {}", request);
            TableUtils.createTableIfNotExists(amazonDynamoDB, request);
        }

    }

}
