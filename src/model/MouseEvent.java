package model;

import java.time.LocalDateTime;

public  class MouseEvent extends Event {
    private int  x;
    private int y;

    public MouseEvent(LocalDateTime timestamp, int x, int y) {
        super(timestamp, EventType.MOUSE);
        this.x= x;
        this.y= y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
