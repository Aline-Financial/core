package com.aline.core.aws.email;

import com.aline.core.aws.config.AWSEmailConfig;
import com.aline.core.config.AppConfig;
import com.amazonaws.services.simpleemailv2.AmazonSimpleEmailServiceV2;
import com.amazonaws.services.simpleemailv2.model.Body;
import com.amazonaws.services.simpleemailv2.model.Content;
import com.amazonaws.services.simpleemailv2.model.Destination;
import com.amazonaws.services.simpleemailv2.model.EmailContent;
import com.amazonaws.services.simpleemailv2.model.Message;
import com.amazonaws.services.simpleemailv2.model.SendEmailRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

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

    /**
     * Send a simple test email.
     * @param subject The subject of the email.
     * @param body The plain text message.
     * @param to Email address(es) to send this email to.
     */
    public void sendEmail(String subject, String body, String... to) {
        log.info("Building email content: [subject={}, body={}, to={}, from={}]",
                subject, body, Arrays.toString(to), fromEmail);
        Content textBody = new Content().withData(body);
        sendEmailHelper(subject, textBody, to);
    }

    /**
     * Send an email with a custom content body.
     * @param subject The subject of the email.
     * @param content The content of the email.
     * @param to The email address(es) to send this email to.
     */
    public void sendEmail(String subject, Content content, String... to) {
        log.info("Building email content: [subject={}, body={}, to={}, from={}]",
                subject, content.toString(), Arrays.toString(to), fromEmail);
        sendEmailHelper(subject, content, to);
    }

    public void sendHtmlEmail(String subject, String htmlFile, String to, Map<String, String> templateVariables) {
        try {
            ClassPathResource resource = new ClassPathResource(htmlFile);
            InputStreamReader fileReader = new InputStreamReader(resource.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            final String[] htmlData = {bufferedReader.lines().collect(Collectors.joining())};
            templateVariables.forEach((key, val) -> htmlData[0] = htmlData[0].replaceAll("\\{\\{" + key + "}}", val));
            sendEmail(subject, htmlData[0], to);
        } catch (IOException e) {
            log.error("Could not read html template file: {}", htmlFile);
        }
    }

    private void sendEmailHelper(String subject, Content content, String... to) {

        Content emailSubject = new Content().withData(subject);

        Destination destination = new Destination().withToAddresses(to);

        Body emailBody = new Body().withHtml(content);

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

        client.sendEmail(request);

        log.info("Email successfully sent to: {}", Arrays.toString(to));
    }
}
