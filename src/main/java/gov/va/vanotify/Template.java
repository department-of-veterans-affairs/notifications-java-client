package gov.va.vanotify;

import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;
import org.json.JSONObject;

import java.util.Optional;
import java.util.UUID;
import java.util.Map;

public class Template {
    private UUID id;
    private String name;
    @SerializedName("type")
    private String templateType;
    private DateTime createdAt;
    private DateTime updatedAt;
    private String createdBy;
    private int version;
    private String body;
    private String subject;
    private Map<String, Object> personalisation;
    private String letterContactBlock;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Optional<DateTime> getUpdatedAt() {
        return Optional.ofNullable(updatedAt);
    }

    public void setUpdatedAt(DateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public Optional<String> getLetterContactBlock() {
        return Optional.ofNullable(letterContactBlock);
    }

    public void setLetterContactBlock(String letterContactBlock) {
        this.letterContactBlock = letterContactBlock;
    }

    public Optional<Map<String, Object>> getPersonalisation() {
        return Optional.ofNullable(personalisation);
    }

    public void setPersonalisation(Map<String, Object> personalisation) {
        this.personalisation = personalisation;
    }

    @Override
    public String toString() {
        return "Template{" +
                "id=" + id +
                ", templateType='" + templateType + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", createdBy='" + createdBy + '\'' +
                ", version=" + version +
                ", body='" + body + '\'' +
                ", subject='" + subject + '\'' +
                ", letterContactBlock='" + letterContactBlock + '\'' +
                ", personalisation='" + personalisation + '\'' +
                '}';
    }
}
