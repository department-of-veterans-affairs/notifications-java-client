package gov.va.vanotify;

import org.json.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SmsRequestTest {
    @Test(expected = IllegalStateException.class)
    public void smsRequestRequiresEmailAddress(){
        new SmsRequest.Builder().withTemplateId(UUID.randomUUID().toString()).build();
    }

    @Test(expected = IllegalStateException.class)
    public void smsRequestRequiresTemplateId(){
        new SmsRequest.Builder().withPhoneNumber("+12223334445").build();
    }

    @Test
    public void smsRequestCanBeCreatedWithOnlyRequiredFields(){
        String templateId = UUID.randomUUID().toString();
        String phoneNumber = "+12223334445";
        SmsRequest request = new SmsRequest.Builder()
                .withTemplateId(templateId)
                .withPhoneNumber(phoneNumber)
                .build();

        assertEquals(phoneNumber, request.getPhoneNumber());
        assertEquals(templateId, request.getTemplateId());
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


        SmsRequest request = new SmsRequest.Builder()
                .withTemplateId(templateId)
                .withPhoneNumber(phoneNumber)
                .withSmsSenderId(smsSenderId)
                .withReference(reference)
                .withBillingCode(billingCode)
                .withPersonalisation(personalisation)
                .build();

        JSONObject actual = request.asJson();
        assertEquals(templateId, actual.getString("template_id"));
        assertEquals(phoneNumber, actual.getString("phone_number"));
        assertEquals(smsSenderId, actual.getString("sms_sender_id"));
        assertEquals(reference, actual.getString("reference"));
        assertEquals(billingCode, actual.getString("billing_code"));
        assertEquals("bar", actual.getJSONObject("personalisation").getString("foo"));
    }
}