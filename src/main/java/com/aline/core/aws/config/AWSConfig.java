package com.aline.core.aws.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.aws")
public class AWSConfig {

    @Bean
    public AWSStaticCredentialsProvider credentialsProvider() {
        String accessKeyId = this.getCredentials().getAccessKeyId();
        String secretAccessKey = this.getCredentials().getSecretAccessKey();
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKeyId, secretAccessKey));
    }

    /**
     * AWS Credentials
     */
    private Credentials credentials = new Credentials();

    @Getter
    @Setter
    public static class Credentials {
        /**
         * Access key ID provided by your AWS user
         */
        private String accessKeyId;

        /**
         * Secret access key provided by your AWS user
         */
        private String secretAccessKey;
    }

}
