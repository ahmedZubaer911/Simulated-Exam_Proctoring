package model;

import java.time.LocalDateTime;

public  abstract class Event {
    private LocalDateTime timestamp;
    private EventType type;

    public Event(LocalDateTime timestamp, EventType type) {
        this.timestamp = timestamp;
        this.type = type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public EventType getType() {
        return type;
    }
}
