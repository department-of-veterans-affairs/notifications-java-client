package gov.va.vanotify;

import java.util.List;
import java.util.Optional;

public class NotificationList {
    private final List<Notification> notifications;
    private final Links links;

    public NotificationList(List<Notification> notifications, Links links) {
        this.notifications = notifications;
        this.links = links;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public Optional<String> getNextPageLink() {
        return links.getNext();
    }

    public String getCurrentPageLink() {
        return links.getCurrent();
    }

    @Override
    public String toString() {
        StringBuilder notifications_string = new StringBuilder("\n");
        for (Notification notification : notifications){
            notifications_string.append(notification.toString()).append("\n");
        }
        return "NotificationList{" +
                "notifications=" + notifications_string.toString() +
                ", currentPageLink='" + getCurrentPageLink() + '\'' +
                ", nextPageLink='" + getNextPageLink() + '\'' +
                '}';
    }

}
