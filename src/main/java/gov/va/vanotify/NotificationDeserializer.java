package gov.va.vanotify;

import com.google.gson.*;

import java.lang.reflect.Type;

import static gov.va.vanotify.GsonConfiguration.baseGson;

public class NotificationDeserializer implements JsonDeserializer<Notification> {
    @Override
    public Notification deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonObject template = jsonObject.getAsJsonObject("template");
        if (!template.isJsonNull()) {
            jsonObject.add("template_id", template.get("id"));
            jsonObject.add("template_version", template.get("version"));
            jsonObject.add("template_uri", template.get("uri"));
        }
        try {
            return baseGson.fromJson(jsonObject, Notification.class);
        } catch (JsonSyntaxException e ) {
            throw new JsonParseException(e);
        }
    }
}
