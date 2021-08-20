package com.aline.core.aws.sms;

import com.aline.core.aws.config.AWSSMSConfig;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@ConditionalOnBean(AWSSMSConfig.class)
@RequiredArgsConstructor
@Slf4j(topic = "SMS Service")
public class SMSService {

    /**
     * Amazon SNS Client
     */
    private final AmazonSNS snsClient;

    /**
     * Send an SMS text message to the provided
     * phone number.
     * @param phoneNumber Phone number string
     * @param message Message string
     * @param type The SMSType (Transactional, Promotional)
     */
    public void sendSMSMessage(String phoneNumber, String message, SMSType type) {
        log.info("Building SMS Message [Phone number: {}, Message: {}, Type: {}]",
                phoneNumber, message, type.getType());
        Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
        messageAttributes.put("AWS.SNS.SMS.SenderID",
                new MessageAttributeValue()
                        .withStringValue("Aline")
                        .withDataType("String"));
        messageAttributes.put("AWS.SNS.SMS.MaxPrice",
                new MessageAttributeValue()
                        .withStringValue("0.50")
                        .withDataType("Number"));
        messageAttributes.put("AWS.SNS.SMS.SMSType",
                new MessageAttributeValue()
                        .withStringValue(type.getType())
                        .withDataType("String"));
        PublishRequest publishRequest = new PublishRequest()
                .withPhoneNumber("+1 " + phoneNumber)
                .withMessage(message)
                .withMessageAttributes(messageAttributes);
        log.info("Attempting to send SMS message...");
        snsClient.publish(publishRequest);
        log.info("Successfully sent SMS message...");
    }

}
