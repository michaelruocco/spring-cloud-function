package uk.co.mruoc;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
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
public class LocalDynamoDbConfig {

    @Value("${aws.dynamodb.endpoint:#{null}}")
    private String endpoint;

    @Value("${aws.region:#{null}")
    private String region;

    @Value("${aws.accesskey:#{null}}")
    private String accessKey;

    @Value("${aws.secretkey:#{null}}")
    private String secretKey;

    @Bean
    @Profile("local")
    public AmazonDynamoDB amazonDynamoDB(AWSCredentials credentials) {
        log.info("setting up local dynamodb config with endpoint {}", endpoint);
        return AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(new EndpointConfiguration(endpoint, region))
                .build();
    }

    @Bean
    @Profile("local")
    public AWSCredentials amazonAwsCredentials() {
        return new BasicAWSCredentials(accessKey, secretKey);
    }

}
