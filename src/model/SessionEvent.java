package model;

import java.time.LocalDateTime;

public class SessionEvent extends Event {
    private String action;

    public SessionEvent(LocalDateTime timestamp, String action) {
        super(timestamp, EventType.SESSION);
        this.action = action;
    }

    public String getAction() {
        return action;
    }

}
