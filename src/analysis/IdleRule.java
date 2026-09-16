package analysis;

import model.ExamSession;
import model.Event;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class IdleRule implements Rule {
    private long maxIdleMinutes;

    // Constructor: Koto minute idle thakle suspicious dhorbo sheta set kora
    public IdleRule(long maxIdleMinutes) {
        this.maxIdleMinutes = maxIdleMinutes;
    }

    @Override
    public boolean evaluate(ExamSession session) {
        List<Event> events = session.getEvents();
        LocalDateTime sessionStart = session.getStartTime();

        // Case 1: Jodi ekhono kono event i na thake
        if (events == null || events.isEmpty()) {
            return true; // Kono event nai mane pura somoytai idle chilo!
        }

        // Case 2: Session shuru theke prothom event er majhkhaner gap
        Event firstEvent = events.get(0);
        if (sessionStart != null) {
            long startToFirstEventGap = Duration.between(sessionStart, firstEvent.getTimestamp()).toMinutes();
            if (startToFirstEventGap > maxIdleMinutes) {
                return true; 
            }
        }

        // Case 3: Duita por-por event er majhkhaner gap (Main Logic)
        for (int i = 1; i < events.size(); i++) {
            Event previousEvent = events.get(i - 1);
            Event currentEvent = events.get(i);

            long gap = Duration.between(previousEvent.getTimestamp(), currentEvent.getTimestamp()).toMinutes();
            
            if (gap > maxIdleMinutes) {
                return true; // Limit cross korlei flagged!
            }
        }

        // Shob test pass korle false (not suspicious)
        return false;
    }
}