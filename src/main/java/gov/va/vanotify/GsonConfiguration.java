package gov.va.vanotify;

import com.google.gson.*;
import org.joda.time.DateTime;

import java.lang.reflect.Type;
import java.util.Date;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;

public class GsonConfiguration {

    public static final Gson gsonInstance = new GsonBuilder()
            .setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(DateTime.class, new LocalDateTimeTypeConverter())
            .create();

    private static class LocalDateTimeTypeConverter
            implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {
        @Override
        public JsonElement serialize(DateTime src, Type srcType, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }

        @Override
        public DateTime deserialize(JsonElement json, Type type, JsonDeserializationContext context)
                throws JsonParseException {
            try {
                return new DateTime(json.getAsString());
            } catch (IllegalArgumentException e) {
                // May be it came in formatted as a java.util.Date, so try that
                Date date = context.deserialize(json, Date.class);
                return new DateTime(date);
            }
        }
    }

}
