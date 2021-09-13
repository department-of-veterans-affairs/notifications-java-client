package gov.va.vanotify;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class JsonUtilsTest {

    private Map<String, Object> expectedMap;
    private List<Object> expectedList;

    @Before
    public void setUp() {
        setUpMap();
        setUpList();
    }

    /**
     * test map contains: string, map with string value, map with boolean value, list
     */
    private void setUpMap() {
        expectedMap = new HashMap<>();
        expectedMap.put("aKey", "aValue");

        Map<String, Object> mapValue = new HashMap<>();
        mapValue.put("bbKey", "bbValue");
        expectedMap.put("bKey", mapValue);

        Map<String, Object> mapValue2 = new HashMap<>();
        mapValue2.put("ccKey", true);
        expectedMap.put("cKey", mapValue2);

        expectedMap.put("xKey", Arrays.asList("x1", "x2"));
    }

    /**
     * test array contains: string, boolean, map with string value, map with boolean value,
     *   map with mixed value types
     */
    private void setUpList() {
        expectedList = new ArrayList<>();
        expectedList.add("dValue");
        expectedList.add(true);
        expectedList.add(false);

        Map<String, Object> mapValue = new HashMap<>();
        mapValue.put("eeKey", "eeValue");
        expectedList.add(mapValue);

        Map<String, Object> mapValue2 = new HashMap<>();
        mapValue2.put("ffKey", true);
        expectedList.add(mapValue2);

        expectedList.add(expectedMap);
    }

    @Test
    public void testJsonToMap_ifNull() {
        Map map = JsonUtils.jsonToMap(null);

        assertEquals(new HashMap<>(), map);
    }

    @Test
    public void testJsonToMap_ifJSONNull() {
        JSONObject json = new JSONObject(JSONObject.NULL);

        Map map = JsonUtils.jsonToMap(json);

        assertEquals(new HashMap<>(), map);
    }

    @Test
    public void testJsonToMap_ifMapNull() {
        JSONObject json = new JSONObject((Map)null);

        Map map = JsonUtils.jsonToMap(json);

        assertEquals(new HashMap<>(), map);
    }

    @Test
    public void testJsonToMap_fromJsonObject() {
        JSONObject json = new JSONObject();
        json.put("aKey", "aValue");
        json.put("bKey", new JSONObject("{\"bbKey\": \"bbValue\"}"));
        json.put("cKey", new JSONObject().put("ccKey", true));
        json.put("xKey", new JSONArray(Arrays.asList("x1", "x2")));

        Map<String, Object> map = JsonUtils.jsonToMap(json);

        assertEquals(expectedMap, map);
   }

    @Test
    public void testJsonToMap_fromJsonObjectFromMap() {

        JSONObject json = new JSONObject(expectedMap);

        Map<String, Object> map = JsonUtils.jsonToMap(json);

        assertEquals(expectedMap, map);
    }

    @Test
    public void testJsonToMap_fromString_ifEmpty() {
        JSONObject json = new JSONObject("{}");

        Map map = JsonUtils.jsonToMap(json);

        assertEquals(new HashMap<>(), map);
    }

    @Test
    public void testJsonToMap_fromString() {
        JSONObject json = new JSONObject("{\n" +
                "\"aKey\": \"aValue\",\n" +
                "\"bKey\": {\"bbKey\": \"bbValue\"},\n" +
                "\"cKey\": {\"ccKey\": True},\n" +
                "\"xKey\": [\"x1\", \"x2\"],\n" +
                "}\n");

        Map map = JsonUtils.jsonToMap(json);

        assertEquals(expectedMap, map);
    }

    @Test
    public void testJsonToMap_orderDoesNotMatter() {
        JSONObject json = new JSONObject("{\n" +
                "\"bKey\": {\"bbKey\": \"bbValue\"},\n" +
                "\"aKey\": \"aValue\",\n" +
                "\"cKey\": {\"ccKey\": True},\n" +
                "\"xKey\": [\"x1\", \"x2\"],\n" +
                "}\n");

        Map map = JsonUtils.jsonToMap(json);

        assertEquals(expectedMap, map);
    }

    @Test
    public void testJsonToList_ifNull() {
        List list = JsonUtils.jsonToList(null);

        assertEquals(new ArrayList<>(), list);
    }

    @Test
    public void testJsonToList_ifListNull() {
        JSONArray json = new JSONArray((List)null);

        List list = JsonUtils.jsonToList(json);

        assertEquals(new ArrayList<>(), list);
    }

    @Test
    public void testJsonToList_fromJsonArray() {
        JSONArray json = new JSONArray();
        json.put("dValue");
        json.put(true);
        json.put(false);
        json.put(new JSONObject("{\"eeKey\": \"eeValue\"}"));
        json.put(new JSONObject().put("ffKey", true));
        json.put(expectedMap);

        List<Object> list = JsonUtils.jsonToList(json);

        assertEquals(expectedList, list);
    }

    @Test
    public void testJsonToList_fromJsonArrayFromList() {
        JSONArray json = new JSONArray(expectedList);

        List list = JsonUtils.jsonToList(json);

        assertEquals(expectedList, list);
    }

    @Test
    public void testJsonToList_fromString_ifEmpty() {
        JSONArray json = new JSONArray("[]");

        List list = JsonUtils.jsonToList(json);

        assertEquals(new ArrayList(), list);
    }

    @Test
    public void testJsonToList_fromString() {
        JSONArray json = new JSONArray("[\n" +
                "\"dValue\", \n" +
                "true, \n" +
                "false, \n" +
                "{\"eeKey\":\"eeValue\"}, \n" +
                "{\"ffKey\":true}, \n" +
                "{\n" +
                "  \"aKey\":\"aValue\", \n" +
                "  \"bKey\":{\"bbKey\":\"bbValue\"},\n" +
                "  \"cKey\":{\"ccKey\":true},\n" +
                "  \"xKey\": [\"x1\", \"x2\"]\n" +
                "}\n" +
                "]");

        List list = JsonUtils.jsonToList(json);

        assertEquals(expectedList, list);
    }

    @Test
    public void testJsonToList_orderMatters() {
        JSONArray json = new JSONArray("[\n" +
                "\"dValue\", \n" +
                "true, \n" +
                "false, \n" +
                "]");

        List list = JsonUtils.jsonToList(json);

        assertEquals(Arrays.asList("dValue", true, false), list);
        assertNotEquals(Arrays.asList("dValue", false, true), list);
    }

}