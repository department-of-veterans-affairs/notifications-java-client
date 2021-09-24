package gov.va.vanotify;

import org.json.JSONObject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


public class EmailRequestTest {

    public static Object[][] missingArgumentsTestData() {
        return new Object[][]{
                {null, null},
                {"", null}
        };
    }

    @ParameterizedTest
    @MethodSource("missingArgumentsTestData")
    public void emailRequestRequiresEmailAddressOrIdentifier(String emailAddress, Identifier identifier){
        assertThrows(IllegalStateException.class, () -> {
            new EmailRequest.Builder()
                    .withTemplateId(UUID.randomUUID().toString())
                    .withEmailAddress(emailAddress)
                    .withRecipientIdentifier(identifier)
                    .build();
        });
    }

    @Test
    public void emailRequestRequiresTemplateId(){
        assertThrows(IllegalStateException.class, () -> {
            new EmailRequest.Builder().withEmailAddress("user@email.com").build();
        });
    }

    public static Object[][] requiredArgumentsTestData() {
        return new Object[][]{
                {UUID.randomUUID().toString(), "user@email.com", null},
                {UUID.randomUUID().toString(), null, new Identifier(IdentifierType.ICN, "1234")},
        };
    }

    @ParameterizedTest
    @MethodSource("requiredArgumentsTestData")
    public void emailRequestCanBeCreatedWithOnlyRequiredFields(String templateId, String emailAddress, Identifier identifier){
        EmailRequest request = new EmailRequest.Builder()
                .withTemplateId(templateId)
                .withEmailAddress(emailAddress)
                .withRecipientIdentifier(identifier)
                .build();

        assertEquals(emailAddress, request.getEmailAddress());
        assertEquals(templateId, request.getTemplateId());
        assertEquals(identifier, request.getRecipientIdentifier());
        assertNull(request.getBillingCode());
        assertNull(request.getEmailReplyToId());
        assertNull(request.getReference());
        assertNull(request.getPersonalisation());
    }

    @Test
    public void emailRequestCanBeConvertedToJson() {
        String templateId = UUID.randomUUID().toString();
        String emailAddress = "user@email.com";
        String emailReplyToId = UUID.randomUUID().toString();
        String reference = "some-reference";
        String billingCode = "some-billing-code";
        Map<String, String> personalisation = new HashMap(){{
            put("foo", "bar");
        }};
        Identifier identifier = new Identifier(IdentifierType.ICN, "1234");


        EmailRequest request = new EmailRequest.Builder()
                .withTemplateId(templateId)
                .withEmailAddress(emailAddress)
                .withEmailReplyToId(emailReplyToId)
                .withReference(reference)
                .withBillingCode(billingCode)
                .withPersonalisation(personalisation)
                .withRecipientIdentifier(identifier)
                .build();

        JSONObject actual = request.asJson();
        assertEquals(templateId, actual.getString("template_id"));
        assertEquals(emailAddress, actual.getString("email_address"));
        assertEquals(emailReplyToId, actual.getString("email_reply_to_id"));
        assertEquals(reference, actual.getString("reference"));
        assertEquals(billingCode, actual.getString("billing_code"));
        assertEquals("bar", actual.getJSONObject("personalisation").getString("foo"));
        assertEquals("ICN", actual.getJSONObject("recipient_identifier").getString("id_type"));
        assertEquals(identifier.getValue(), actual.getJSONObject("recipient_identifier").getString("id_value"));
    }
}