package gov.va.vanotify;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.Objects;

public class Identifier {
    @SerializedName("id_type")
    private final IdentifierType identifierType;
    @SerializedName("id_value")
    private final String value;

    public Identifier(IdentifierType identifierType, String value) {
        if (value == null || value.isEmpty()) throw new IllegalStateException("Identifier value cannot be empty");
        if (identifierType == null) throw new IllegalStateException("IdentifierType cannot be empty");

        this.identifierType = identifierType;

        if (value.endsWith(identifierType.suffix())) { this.value = value; }
        else { this.value = value + identifierType.suffix(); }
    }

    public IdentifierType getIdentifierType() {
        return identifierType;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identifier that = (Identifier) o;
        return identifierType == that.identifierType && value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifierType, value);
    }

    @Override
    public String toString() {
        return "Identifier{" +
                "identifierType=" + identifierType +
                ", value=" + value +
                '}';
    }

    public static class IdentifierDeserializer implements JsonDeserializer<Identifier> {

        @Override
        public Identifier deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return new Identifier(
                    IdentifierType.valueOf(jsonElement.getAsJsonObject().get("id_type").getAsString()),
                    jsonElement.getAsJsonObject().get("id_value").getAsString()
            );
        }
    }
}
