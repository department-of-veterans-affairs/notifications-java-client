package gov.va.vanotify;

import com.google.gson.annotations.SerializedName;
import org.json.JSONObject;

import java.util.Optional;
import java.util.UUID;

public class TemplatePreview {
        private UUID id;
        @SerializedName("type")
        private String templateType;
        private int version;
        private String body;
        private String subject;
        private String html;

    public TemplatePreview(UUID id, String templateType, int version, String body, String subject, String html) {
        this.id = id;
        this.templateType = templateType;
        this.version = version;
        this.body = body;
        this.subject = subject;
        this.html = html;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Optional<String> getSubject() {
        return Optional.ofNullable(subject);
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Optional<String> getHtml() {
        return Optional.ofNullable(html);
    }
    public void setHtml(String html) {
        this.html = html;
    }


    @Override
    public String toString() {
        return "Template{" +
                "id=" + id +
                ", templateType='" + templateType + '\'' +
                ", version=" + version +
                ", body='" + body + '\'' +
                ", subject='" + subject + '\'' +
                ", html='" + html + '\'' +
                '}';
    }
}
