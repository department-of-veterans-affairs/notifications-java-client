package gov.va.vanotify;

import com.google.gson.annotations.SerializedName;
import org.json.JSONObject;

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

    public static Identifier fromJson(JSONObject json) {
        return new Identifier(
                IdentifierType.valueOf(json.getString("id_type")),
                json.getString("id_value")
        );
    }

    public IdentifierType getIdentifierType() {
        return identifierType;
    }

    public String getValue() {
        return value;
    }

    public JSONObject asJson() {
        JSONObject body = new JSONObject();
        body.put("id_type", this.identifierType.toString());
        body.put("id_value", this.value);
        return body;
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
}
