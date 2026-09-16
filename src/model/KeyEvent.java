package model;

import java.time.LocalDateTime;

public class KeyEvent extends Event {
    private String keyCode;

    public KeyEvent(LocalDateTime  timestamp, String keyCode) {
        super(timestamp, EventType.KEYBOARD);
        this.keyCode= keyCode;
    }

    public String getKeyCode() {
        return keyCode;
    }
    
}
