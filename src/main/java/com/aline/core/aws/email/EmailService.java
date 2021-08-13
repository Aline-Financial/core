package com.aline.core.aws.email;

import com.aline.core.aws.config.AWSEmailConfig;
import com.aline.core.config.AppConfig;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.simpleemailv2.AmazonSimpleEmailServiceV2;
import com.amazonaws.services.simpleemailv2.model.Body;
import com.amazonaws.services.simpleemailv2.model.Content;
import com.amazonaws.services.simpleemailv2.model.Destination;
import com.amazonaws.services.simpleemailv2.model.EmailContent;
import com.amazonaws.services.simpleemailv2.model.Message;
import com.amazonaws.services.simpleemailv2.model.NotFoundException;
import com.amazonaws.services.simpleemailv2.model.SendEmailRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Map;

@Service
@Slf4j(topic = "Email Service")
@RequiredArgsConstructor
@ConditionalOnBean(AWSEmailConfig.class)
public class EmailService {

    private final AWSStaticCredentialsProvider credentialsProvider;
    private final AppConfig appConfig;
    private final AWSEmailConfig emailConfig;

    private final AmazonSimpleEmailServiceV2 client;
    private final AmazonS3 s3;

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

    public void sendHtmlEmail(String subject, String templateName, String to, Map<String, String> templateVariables) {
        String templateData = getEmailTemplateData(templateName);
        String htmlData = interpolateStringVariables(templateData, templateVariables);
        sendEmail(subject, htmlData, to);
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

    /**
     * Retrieves an email template from the AWS S3 Bucket
     * @param templateName The email template name.
     * @return The email template as a string.
     */
    public String getEmailTemplateData(String templateName) {

        String bucketName = emailConfig.getTemplateBucketName();
        try {
            return s3.getObjectAsString(bucketName, templateName);
        } catch (SdkClientException e) {
            log.error("Could not retrieve template from S3: {}", templateName);
        } catch (IllegalArgumentException e) {
            throw new NotFoundException(String.format("Template '%s' in bucket '%s' was not found.", templateName, bucketName));
        }
        return null;
    }

    /**
     * Replaces template variables <em>(formatted as so: ${variable})</em> with
     * the specified string value in a HashMap of string keys and string values.
     * @param template The template to replace variables in.
     * @param variables The HashMap of string keys and string values.
     * @return A template with all the specified variables replaced.
     */
    public String interpolateStringVariables(String template, Map<String, String> variables) {
        String[] atomicTemplate = {template};
        variables.forEach((variable, value) -> {
            atomicTemplate[0] = atomicTemplate[0].replaceAll("\\$\\{" + variable + "}", value);
        });
        return atomicTemplate[0];
    }
}
