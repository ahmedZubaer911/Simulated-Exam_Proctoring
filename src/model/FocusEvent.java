package model;

import java.time.LocalDateTime;

public class FocusEvent extends Event{
    private  String focusedWindow;

    public FocusEvent(LocalDateTime timestamp, String focusedWindow) {
        super(timestamp, EventType.FOCUS);
        this.focusedWindow= focusedWindow;
    }

    public String getFocusedWindow() {
        return focusedWindow;
    }
}
