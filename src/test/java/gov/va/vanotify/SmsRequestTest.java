package gov.va.vanotify;

import org.json.JSONObject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


public class SmsRequestTest {

    public static Object[][] missingArgumentsTestData() {
        return new Object[][]{
                {null, null},
                {"", null}
        };
    }

    @ParameterizedTest
    @MethodSource("missingArgumentsTestData")
    public void smsRequestRequiresEmailAddress(String phoneNumber, Identifier identifier){
        assertThrows(IllegalStateException.class, () -> {
            new SmsRequest.Builder()
                    .withTemplateId(UUID.randomUUID().toString())
                    .withPhoneNumber(phoneNumber)
                    .withRecipientIdentifier(identifier)
                    .build();
        });
    }

    @Test
    public void smsRequestRequiresTemplateId(){
        assertThrows(IllegalStateException.class, () -> {
            new SmsRequest.Builder().withPhoneNumber("+12223334445").build();
        });
    }

    public static Object[][] requiredArgumentsTestData() {
        return new Object[][]{
                {UUID.randomUUID().toString(), "+12223334445", null},
                {UUID.randomUUID().toString(), null, new Identifier(IdentifierType.ICN, "1234")},
        };
    }

    @ParameterizedTest
    @MethodSource("requiredArgumentsTestData")
    public void smsRequestCanBeCreatedWithOnlyRequiredFields(String templateId, String phoneNumber, Identifier identifier){
        SmsRequest request = new SmsRequest.Builder()
                .withTemplateId(templateId)
                .withPhoneNumber(phoneNumber)
                .withRecipientIdentifier(identifier)
                .build();

        assertEquals(phoneNumber, request.getPhoneNumber());
        assertEquals(templateId, request.getTemplateId());
        assertEquals(identifier, request.getRecipientIdentifier());
        assertNull(request.getBillingCode());
        assertNull(request.getSmsSenderId());
        assertNull(request.getReference());
        assertNull(request.getPersonalisation());
    }

    @Test
    public void smsRequestCanBeConvertedToJson() {
        String templateId = UUID.randomUUID().toString();
        String phoneNumber = "+12223334445";
        String smsSenderId = UUID.randomUUID().toString();
        String reference = "some-reference";
        String billingCode = "some-billing-code";
        Map<String, String> personalisation = new HashMap(){{
            put("foo", "bar");
        }};
        Identifier identifier = new Identifier(IdentifierType.ICN, "1234");


        SmsRequest request = new SmsRequest.Builder()
                .withTemplateId(templateId)
                .withPhoneNumber(phoneNumber)
                .withSmsSenderId(smsSenderId)
                .withReference(reference)
                .withBillingCode(billingCode)
                .withPersonalisation(personalisation)
                .withRecipientIdentifier(identifier)
                .build();

        JSONObject actual = request.asJson();
        assertEquals(templateId, actual.getString("template_id"));
        assertEquals(phoneNumber, actual.getString("phone_number"));
        assertEquals(smsSenderId, actual.getString("sms_sender_id"));
        assertEquals(reference, actual.getString("reference"));
        assertEquals(billingCode, actual.getString("billing_code"));
        assertEquals("bar", actual.getJSONObject("personalisation").getString("foo"));
        assertEquals("ICN", actual.getJSONObject("recipient_identifier").getString("id_type"));
        assertEquals(identifier.getValue(), actual.getJSONObject("recipient_identifier").getString("id_value"));
    }
}