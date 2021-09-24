package gov.va.vanotify;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;

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
        JSONObject actual = identifier.asJson();
        assertEquals(actual.getString("id_type"), identifier.getIdentifierType().toString());
        assertEquals(actual.getString("id_value"), identifier.getValue());
    }

    @Test
    public void shouldCreateFromJson() {
        JSONObject json = new JSONObject();
        json.put("id_type", IdentifierType.ICN.toString());
        String identifierValue = UUID.randomUUID().toString();
        json.put("id_value", identifierValue);

        Identifier actual = Identifier.fromJson(json);
        assertEquals(actual.getIdentifierType(), IdentifierType.ICN);
        assertEquals(actual.getValue(), identifierValue + IdentifierType.ICN.suffix());
    }


}