package gov.va.vanotify;

import org.json.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;

public class EmailRequestTest {
    @Test(expected = IllegalStateException.class)
    public void emailRequestRequiresEmailAddress(){
        new EmailRequest.Builder().withTemplateId(UUID.randomUUID().toString()).build();
    }

    @Test(expected = IllegalStateException.class)
    public void emailRequestRequiresTemplateId(){
        new EmailRequest.Builder().withEmailAddress("user@email.com").build();
    }

    @Test
    public void emailRequestCanBeCreatedWithOnlyRequiredFields(){
        String templateId = UUID.randomUUID().toString();
        String emailAddress = "user@email.com";
        EmailRequest request = new EmailRequest.Builder()
                .withTemplateId(templateId)
                .withEmailAddress(emailAddress)
                .build();

        assertEquals(emailAddress, request.getEmailAddress());
        assertEquals(templateId, request.getTemplateId());
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


        EmailRequest request = new EmailRequest.Builder()
                .withTemplateId(templateId)
                .withEmailAddress(emailAddress)
                .withEmailReplyToId(emailReplyToId)
                .withReference(reference)
                .withBillingCode(billingCode)
                .withPersonalisation(personalisation)
                .build();

        JSONObject actual = request.asJson();
        assertEquals(templateId, actual.getString("template_id"));
        assertEquals(emailAddress, actual.getString("email_address"));
        assertEquals(emailReplyToId, actual.getString("email_reply_to_id"));
        assertEquals(reference, actual.getString("reference"));
        assertEquals(billingCode, actual.getString("billing_code"));
        assertEquals("bar", actual.getJSONObject("personalisation").getString("foo"));
    }
}