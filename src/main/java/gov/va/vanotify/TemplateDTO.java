package gov.va.vanotify;

import java.util.UUID;

class TemplateDTO {
    private UUID id;
    private int version;
    private String uri;

    public TemplateDTO(UUID id, int version, String uri) {
        this.id = id;
        this.version = version;
        this.uri = uri;
    }

    public UUID getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public String getUri() {
        return uri;
    }
}
