package gov.va.vanotify;

public enum IdentifierType {
    VAPROFILEID("^PI^200VETS^USDVA"),
    PID("^PI^200CORP^USVBA"),
    ICN("^NI^200M^USVHA"),
    BIRLSID("^PI^200BRLS^USVBA"),
    EDIPI("^NI^200DOD^USDOD")
    ;

    private final String suffix;


    IdentifierType(String suffix) {
        this.suffix = suffix;
    }

    public String suffix() {
        return suffix;
    }
}
