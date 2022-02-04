package gov.va.vanotify;

import java.util.Optional;

class Links {
    private final String current;
    private final String next;

    public Links(String current, String next) {
        this.current = current;
        this.next = next;
    }

    public String getCurrent() {
        return current;
    }

    public Optional<String> getNext() {
       return Optional.ofNullable(next);
    }
}
