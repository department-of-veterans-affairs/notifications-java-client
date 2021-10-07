package gov.va.vanotify;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static gov.va.vanotify.GsonConfiguration.gsonInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemplatePreviewTest {

    @Test
    public void testTemplatePreview_canCreateObjectFromJson() {
        JsonObject content = new JsonObject();
        String id = UUID.randomUUID().toString();
        content.addProperty("id", id);
        content.addProperty("type", "email");
        content.addProperty("version", 3);
        content.addProperty("body", "The body of the template. For ((name)) eyes only.");
        content.addProperty("subject", "Private email");

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
        JsonObject content = new JsonObject();
        String id = UUID.randomUUID().toString();
        content.addProperty("id", id);
        content.addProperty("type", "email");
        content.addProperty("version", 3);
        content.addProperty("body", "The body of the template. For ((name)) eyes only.");
        content.addProperty("subject", "Private email");
        content.addProperty("html", "html version of the body");

        TemplatePreview template = gsonInstance.fromJson(content.toString(), TemplatePreview.class);
        assertEquals(UUID.fromString(id), template.getId());
        assertEquals("email", template.getTemplateType());
        assertEquals(3, template.getVersion());
        assertEquals("The body of the template. For ((name)) eyes only.", template.getBody());
        assertEquals(Optional.of("Private email"), template.getSubject());
        assertEquals(Optional.of("html version of the body"), template.getHtml());
    }
}