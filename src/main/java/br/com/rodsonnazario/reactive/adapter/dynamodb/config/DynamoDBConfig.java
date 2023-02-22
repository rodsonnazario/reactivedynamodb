package br.com.rodsonnazario.reactive.adapter.dynamodb.config;


import br.com.rodsonnazario.reactive.core.domain.Cliente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

import java.net.URI;

@Configuration
public class DynamoDBConfig {

    private final String endpoint;
    private final String region;

    public DynamoDBConfig(
            @Value("${aws.dynamodb.endpoint}") String endpoint,
            @Value("${aws.region}") String region
    ) {
        this.endpoint = endpoint;
        this.region = region;
    }

    @Bean
    public DynamoDbAsyncClient getDynamoDbAsyncClient() {
        return DynamoDbAsyncClient.builder()
                .credentialsProvider(DefaultCredentialsProvider.builder().build())
                .region(Region.of(region))
                .endpointOverride(URI.create(endpoint))
                .build();
    }

    @Bean
    public DynamoDbEnhancedAsyncClient getDynamoDbEnhancedAsyncClient(DynamoDbAsyncClient dynamoDbAsyncClient) {
        return DynamoDbEnhancedAsyncClient.builder()
                .dynamoDbClient(dynamoDbAsyncClient)
                .build();
    }

    @Bean
    public DynamoDbAsyncTable<Cliente> getDynamoDbAsyncCliente(DynamoDbEnhancedAsyncClient asyncClient) {
        return asyncClient.table(Cliente.class.getSimpleName(), TableSchema.fromBean(Cliente.class));
    }
}
