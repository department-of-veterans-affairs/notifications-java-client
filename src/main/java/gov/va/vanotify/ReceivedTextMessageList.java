package gov.va.vanotify;

import java.util.List;
import java.util.Optional;

public class ReceivedTextMessageList {
    private final List<ReceivedTextMessage> receivedTextMessages;
    private final Links links;

    public ReceivedTextMessageList(List<ReceivedTextMessage> receivedTextMessages, Links links) {
        this.receivedTextMessages = receivedTextMessages;
        this.links = links;
    }

    public List<ReceivedTextMessage> getReceivedTextMessages(){
        return receivedTextMessages;
    }

    public Optional<String> getNextPageLink() {
        return links.getNext();
    }

    public String getCurrentPageLink() {
        return links.getCurrent();
    }
}
