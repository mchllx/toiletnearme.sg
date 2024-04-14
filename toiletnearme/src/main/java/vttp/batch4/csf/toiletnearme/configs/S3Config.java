package vttp.batch4.csf.toiletnearme.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {

    @Value("${s3.secret.key}")
    private String secretKey;

    @Value("${s3.access.key}")
    private String accessKey;

    //amazon s3 client
    @Bean
    public AmazonS3 createS3Client() {
        BasicAWSCredentials cred = new BasicAWSCredentials(accessKey, secretKey);

        // endpoint config, S3 compatible
        // endpoint, sgp1 (region)
        EndpointConfiguration epConfig = new EndpointConfiguration(
            "sgp1.digitaloceanspaces.com", "sgp1");
    
        return AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(epConfig)
            .withCredentials(new AWSStaticCredentialsProvider(cred))
            .build();
    }

    
}
