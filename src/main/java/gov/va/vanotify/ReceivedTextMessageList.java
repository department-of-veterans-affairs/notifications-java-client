package gov.va.vanotify;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReceivedTextMessageList {
    private final List<ReceivedTextMessage> receivedTextMessages;
    private final String currentPageLink;
    private final String nextPageLink;

    public ReceivedTextMessageList(String json) {
        JSONObject data = new JSONObject(json);
        JSONObject links = data.getJSONObject("links");
        currentPageLink = links.getString("current");
        nextPageLink = links.isNull("next") ? null : links.getString("next");
        receivedTextMessages =  new ArrayList<>();

        JSONArray receivedTextMessagesData = data.getJSONArray("received_text_messages");
        for(int i = 0; i < receivedTextMessagesData.length(); i++){
            JSONObject receivedTextMessage = receivedTextMessagesData.getJSONObject(i);
            receivedTextMessages.add(new ReceivedTextMessage(receivedTextMessage));
        }
    }
    public List<ReceivedTextMessage> getReceivedTextMessages(){
        return receivedTextMessages;
    }

    public Optional<String> getNextPageLink() {
        return Optional.ofNullable(nextPageLink);
    }

    public String getCurrentPageLink() {
        return currentPageLink;
    }
}
