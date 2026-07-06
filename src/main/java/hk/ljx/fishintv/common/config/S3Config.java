package hk.ljx.fishintv.common.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
public class S3Config {

    @Resource
    private StorageConfigProperties storageConfigProperties;

    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(
                storageConfigProperties.getAccessKey(),
                storageConfigProperties.getSecretKey()
        );

        return S3Client.builder()
                .endpointOverride(URI.create(storageConfigProperties.getEndpoint()))
                .region(Region.of(storageConfigProperties.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .forcePathStyle(true)
                .build();
    }
}
