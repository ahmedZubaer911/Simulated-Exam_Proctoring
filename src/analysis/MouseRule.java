package analysis;

import model.ExamSession;
import model.Event;
import model.MouseEvent;
import java.util.List;

public class MouseRule implements Rule {
    private int minAllowedX, minAllowedY, maxAllowedX, maxAllowedY;
    private double maxJumpDistance; 

    // Constructor: Bounds ar Jump Distance set korar jonno
    public MouseRule(int minAllowedX, int minAllowedY, int maxAllowedX, int maxAllowedY, double maxJumpDistance) {
        this.minAllowedX = minAllowedX;
        this.minAllowedY = minAllowedY;
        this.maxAllowedX = maxAllowedX;
        this.maxAllowedY = maxAllowedY;
        this.maxJumpDistance = maxJumpDistance;
    }

    @Override
    public boolean evaluate(ExamSession session) {
        List<Event> events = session.getEvents();
        if (events == null || events.isEmpty()) return false;

        MouseEvent previousMouseEvent = null;
        int sameSpotCount = 0; // Auto-clicker track korar jonno

        for (Event event : events) {
            if (event instanceof MouseEvent) {
                MouseEvent currentMouseEvent = (MouseEvent) event;
                int x = currentMouseEvent.getX();
                int y = currentMouseEvent.getY();

                // Logic 1: Out of Bounds
                if (x < minAllowedX || x > maxAllowedX || y < minAllowedY || y > maxAllowedY) {
                    System.out.println("Flagged: Mouse Out of Bounds!");
                    return true;
                }

                if (previousMouseEvent != null) {
                    int prevX = previousMouseEvent.getX();
                    int prevY = previousMouseEvent.getY();

                    // Logic 2: Teleportation / Too Fast Jump (Distance Formula)
                    double distance = Math.sqrt(Math.pow(x - prevX, 2) + Math.pow(y - prevY, 2));
                    if (distance > maxJumpDistance) {
                        System.out.println("Flagged: Suspicious Mouse Jump (" + distance + " pixels)!");
                        return true;
                    }

                    // Logic 3: Bot / Auto-Clicker Detection
                    if (x == prevX && y == prevY) {
                        sameSpotCount++;
                        if (sameSpotCount > 10) { // Jodi 10 barer beshi exact same pixel e event hoy
                            System.out.println("Flagged: Bot or Auto-Clicker Detected!");
                            return true;
                        }
                    } else {
                        sameSpotCount = 0; // Coordinate change hole count reset kore dilam
                    }
                }
                
                // Current event ke next loop er jonno previous baniye dilam
                previousMouseEvent = currentMouseEvent; 
            }
        }

        return false; 
    }
}