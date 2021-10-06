package gov.va.vanotify;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TemplateList {
    private final List<Template> templates;


    public TemplateList(List<Template> templates) {
        this.templates = templates;
    }

    public List<Template> getTemplates() {
        return templates;
    }

    @Override
    public String toString() {
        return "TemplateList{" +
                "templates=" + templates +
                '}';
    }
}
