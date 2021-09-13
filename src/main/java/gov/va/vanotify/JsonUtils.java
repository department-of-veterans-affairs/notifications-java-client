package gov.va.vanotify;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JsonUtils {
    public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<>();

        if (json != null && json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    private static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<>();

        object.keySet().forEach(key -> map.put(key, parseValue(object.get(key))));

        return map;
    }

    public static List<Object> jsonToList(JSONArray json) throws JSONException {
        List<Object> retList = new ArrayList<>();

        if (json != null) {
            retList = toList(json);
        }
        return retList;
    }

    private static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<>();

        array.forEach(item -> list.add(parseValue(item)));

        return list;
    }

    private static Object parseValue(Object value) {
        if (value instanceof JSONArray) {
            return toList((JSONArray) value);
        } else if (value instanceof JSONObject) {
            return toMap((JSONObject) value);
        } else return value;
    }
}
