package gov.va.vanotify;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;

import static gov.va.vanotify.GsonConfiguration.gsonInstance;
import static org.junit.jupiter.api.Assertions.*;

public class IdentifierTest {

    public static Object[][] missingArgumentsTestData() {
        return new Object[][]{
                {null, UUID.randomUUID().toString()},
                {IdentifierType.ICN, null},
                {IdentifierType.ICN, ""}
        };
    }

    @ParameterizedTest
    @MethodSource("missingArgumentsTestData")
    public void shouldThrowErrorOnMissingValue(IdentifierType identifierType, String value) {
        assertThrows(IllegalStateException.class, () -> {
            new Identifier(identifierType, value);
        });
    }


    public static Object[][] fhirFormatTestData() {
        return new Object[][]{
                {IdentifierType.ICN, "1234", "1234^NI^200M^USVHA"},
                {IdentifierType.ICN, "4567^NI^200M^USVHA", "4567^NI^200M^USVHA"},
                {IdentifierType.PID, "abc", "abc"+IdentifierType.PID.suffix()}
        };
    }

    @ParameterizedTest
    @MethodSource("fhirFormatTestData")
    public void shouldConvertsValueToFHIRFormat(IdentifierType identifierType, String value, String expectedValue) {
        Identifier identifier = new Identifier(identifierType, value);
        assertEquals(identifier.getValue(), expectedValue);

    }

    @Test
    public void shouldCorrectlyRecognizeEqualIdentifiers() {
        String value = UUID.randomUUID().toString();
        Identifier first = new Identifier(IdentifierType.ICN, value);
        Identifier second = new Identifier(IdentifierType.ICN, value+"^NI^200M^USVHA");
        assertTrue(first.equals(second));
    }

    @Test
    public void shouldCorrectlyRecognizeDifferentIdentifiers() {
        Identifier first = new Identifier(IdentifierType.ICN, "some-id");
        Identifier second = new Identifier(IdentifierType.ICN, "another-id");
        assertFalse(first.equals(second));
    }

    public static Object[][] asJsonTestData() {
        return new Object[][]{
                {IdentifierType.ICN, "1234"},
                {IdentifierType.PID, UUID.randomUUID().toString()}
        };
    }

    @ParameterizedTest
    @MethodSource("asJsonTestData")
    public void shouldSerializeToExpectedJsonStructure(IdentifierType identifierType, String value) {
        Identifier identifier = new Identifier(identifierType, value);
        JsonObject actual = gsonInstance.toJsonTree(identifier).getAsJsonObject();
        assertEquals(actual.get("id_type").getAsString(), identifier.getIdentifierType().toString());
        assertEquals(actual.get("id_value").getAsString(), identifier.getValue());
    }

    @Test
    public void shouldCreateFromJson() {
        JsonObject json = new JsonObject();
        json.addProperty("id_type", IdentifierType.ICN.toString());
        String identifierValue = UUID.randomUUID().toString();
        json.addProperty("id_value", identifierValue);

        Identifier actual = gsonInstance.fromJson(json, Identifier.class);
        assertEquals(IdentifierType.ICN, actual.getIdentifierType());
        assertEquals(identifierValue + IdentifierType.ICN.suffix(), actual.getValue());
    }


}