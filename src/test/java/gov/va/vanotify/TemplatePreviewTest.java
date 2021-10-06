package gov.va.vanotify;

import org.jose4j.json.internal.json_simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static gov.va.vanotify.GsonConfiguration.gsonInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemplatePreviewTest {

    @Test
    public void testTemplatePreview_canCreateObjectFromJson() {
        JSONObject content = new JSONObject();
        String id = UUID.randomUUID().toString();
        content.put("id", id);
        content.put("type", "email");
        content.put("version", 3);
        content.put("body", "The body of the template. For ((name)) eyes only.");
        content.put("subject", "Private email");

        TemplatePreview template = gsonInstance.fromJson(content.toString(), TemplatePreview.class);
        assertEquals(UUID.fromString(id), template.getId());
        assertEquals("email", template.getTemplateType());
        assertEquals(3, template.getVersion());
        assertEquals("The body of the template. For ((name)) eyes only.", template.getBody());
        assertEquals(Optional.of("Private email"), template.getSubject());
        assertEquals(Optional.empty(), template.getHtml());
    }

    @Test
    public void testTemplatePreview_canCreateObjectFromJsonWithHtml() {
        JSONObject content = new JSONObject();
        String id = UUID.randomUUID().toString();
        content.put("id", id);
        content.put("type", "email");
        content.put("version", 3);
        content.put("body", "The body of the template. For ((name)) eyes only.");
        content.put("subject", "Private email");
        content.put("html", "html version of the body");

        TemplatePreview template = gsonInstance.fromJson(content.toString(), TemplatePreview.class);
        assertEquals(UUID.fromString(id), template.getId());
        assertEquals("email", template.getTemplateType());
        assertEquals(3, template.getVersion());
        assertEquals("The body of the template. For ((name)) eyes only.", template.getBody());
        assertEquals(Optional.of("Private email"), template.getSubject());
        assertEquals(Optional.of("html version of the body"), template.getHtml());
    }
}