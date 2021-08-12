package com.aline.core.aws.email;

import com.aline.core.aws.config.AWSConfig;
import com.aline.core.aws.config.AWSEmailConfig;
import com.aline.core.config.AppConfig;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemailv2.AmazonSimpleEmailServiceV2;
import com.amazonaws.services.simpleemailv2.AmazonSimpleEmailServiceV2ClientBuilder;
import com.amazonaws.services.simpleemailv2.model.Body;
import com.amazonaws.services.simpleemailv2.model.Content;
import com.amazonaws.services.simpleemailv2.model.Destination;
import com.amazonaws.services.simpleemailv2.model.EmailContent;
import com.amazonaws.services.simpleemailv2.model.Message;
import com.amazonaws.services.simpleemailv2.model.SendEmailRequest;
import com.amazonaws.services.simpleemailv2.model.SendEmailResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Service
@Slf4j(topic = "Email Service")
@RequiredArgsConstructor
@ConditionalOnBean(AWSEmailConfig.class)
public class EmailService {

    private final AppConfig appConfig;

    private final AmazonSimpleEmailServiceV2 client;

    private String fromEmail;

    @PostConstruct
    public void init() {
        fromEmail = appConfig.getEmail().getFrom();
    }

    public void sendEmail(String subject, String body, String... to) {
        log.info("Building email content: [subject={}, body={}, to={}, from={}]",
                subject, body, Arrays.toString(to), fromEmail);

        Destination destination = new Destination().withToAddresses(to);

        Content emailSubject = new Content().withData(subject);
        Content textBody = new Content().withData(body);
        Body emailBody = new Body().withHtml(textBody);

        Message message = new Message()
                .withSubject(emailSubject)
                .withBody(emailBody);

        EmailContent emailContent = new EmailContent()
                .withSimple(message);

        SendEmailRequest request = new SendEmailRequest()
                .withFromEmailAddress(fromEmail)
                .withDestination(destination)
                .withContent(emailContent);

        log.info("Attempting to send email...");

        SendEmailResult result = client.sendEmail(request);
        log.info("Email successfully sent to: {}", Arrays.toString(to));
        log.info("HTTP Data: {}", result.getSdkHttpMetadata());

    }
}
