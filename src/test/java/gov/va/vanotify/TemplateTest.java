package gov.va.vanotify;

import com.google.gson.JsonObject;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static gov.va.vanotify.GsonConfiguration.gsonInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemplateTest {

    @Test
    public void testTemplate_canCreateObjectFromJson() {
        JsonObject content = new JsonObject();
        String id = UUID.randomUUID().toString();
        content.addProperty("id", id);
        content.addProperty("name", "my template");
        content.addProperty("type", "email");
        content.addProperty("created_at", "2017-05-01T08:30:00.000Z");
        content.addProperty("updated_at", "2017-05-01T08:34:00.000Z");
        content.addProperty("version", 3);
        content.addProperty("body", "The body of the template. For ((name)) eyes only.");
        content.addProperty("subject", "Private email");

        JsonObject required = new JsonObject();
        required.addProperty("required", true);

        JsonObject personalisation = new JsonObject();
        personalisation.add("placeholder", required);
        personalisation.add("conditional", required);

        content.add("personalisation", personalisation);

        Template template = gsonInstance.fromJson(content.toString(), Template.class);
        assertEquals(UUID.fromString(id), template.getId());
        assertEquals("my template", template.getName());
        assertEquals("email", template.getTemplateType());
        assertEquals(new DateTime("2017-05-01T08:30:00.000Z"), template.getCreatedAt());
        assertEquals(Optional.of(new DateTime("2017-05-01T08:34:00.000Z")), template.getUpdatedAt());
        assertEquals(3, template.getVersion());
        assertEquals("The body of the template. For ((name)) eyes only.", template.getBody());
        assertEquals(Optional.of("Private email"), template.getSubject());

        Map<String, Object> expectedPersonalisation = new HashMap<>();
        Map<String, Object> expectedPersonalisationProperty = new HashMap<>();
        expectedPersonalisationProperty.put("required", true);
        expectedPersonalisation.put("placeholder", expectedPersonalisationProperty);
        expectedPersonalisation.put("conditional", expectedPersonalisationProperty);

        assertEquals(Optional.of(expectedPersonalisation), template.getPersonalisation());
    }

    @Test
    public void testTemplate_canCreateObjectFromJsonWithOptionals() {
        JsonObject content = new JsonObject();
        String id = UUID.randomUUID().toString();
        content.addProperty("id", id);
        content.addProperty("name", "my template");
        content.addProperty("type", "email");
        content.addProperty("created_at", "2017-05-01T08:30:00.000Z");
        content.add("updated_at", null);
        content.addProperty("version", 3);
        content.addProperty("body", "The body of the template. For ((name)) eyes only.");
        content.add("subject", null);
        content.add("personalisation", null);

        Template template = gsonInstance.fromJson(content.toString(), Template.class);
        assertEquals(UUID.fromString(id), template.getId());
        assertEquals("my template", template.getName());
        assertEquals("email", template.getTemplateType());
        assertEquals(new DateTime("2017-05-01T08:30:00.000Z"), template.getCreatedAt());
        assertEquals(Optional.empty(), template.getUpdatedAt());
        assertEquals(3, template.getVersion());
        assertEquals("The body of the template. For ((name)) eyes only.", template.getBody());
        assertEquals(Optional.empty(), template.getSubject());
        assertEquals(Optional.empty(), template.getPersonalisation());
    }

}
