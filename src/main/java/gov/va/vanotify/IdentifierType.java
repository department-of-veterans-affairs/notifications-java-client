package gov.va.vanotify;

public enum IdentifierType {
    VA_PROFILE_ID("VAPROFILEID", "^PI^200VETS^USDVA"),
    PARTICIPANT_ID("PID", "^PI^200CORP^USVBA"),
    ICN("ICN", "^NI^200M^USVHA"),
    BIRLS_ID("BIRLSID", "^PI^200BRLS^USVBA"),
    EDIPI("EDIPI", "^NI^200DOD^USDOD")
    ;

    private final String type;
    private final String suffix;


    IdentifierType(String type, String suffix) {
        this.type = type;
        this.suffix = suffix;
    }

    public String suffix() {
        return suffix;
    }

    @Override
    public String toString() {
        return type;
    }
}
