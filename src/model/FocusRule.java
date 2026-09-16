package analysis;

import model.ExamSession;
import model.Event;
import model.FocusEvent;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class FocusRule implements Rule {
    private int maxAllowedSwitches;
    private long maxSecondsOutside; // Notun parameter: Onno window te max koto second thakte parbe

    public FocusRule(int maxAllowedSwitches, long maxSecondsOutside) {
        this.maxAllowedSwitches = maxAllowedSwitches;
        this.maxSecondsOutside = maxSecondsOutside;
    }

    @Override
    public boolean evaluate(ExamSession session) {
        int focusLossCount = 0;
        LocalDateTime awayStartTime = null; // Student kokhon exam window theke ber holo

        List<Event> events = session.getEvents(); 

        for (Event event : events) {
            
            if (event instanceof FocusEvent) {
                FocusEvent focusEvent = (FocusEvent) event;
                String currentWindow = focusEvent.getFocusedWindow();
                
                // Case 1: Student "Exam Window" theke onno window te gelo
                if (!currentWindow.equals("Exam Window") && awayStartTime == null) {
                    focusLossCount++;
                    awayStartTime = focusEvent.getTimestamp(); // Ber howar time ta save korlam
                } 
                // Case 2: Student onno window theke abar "Exam Window" te fire ashlo
                else if (currentWindow.equals("Exam Window") && awayStartTime != null) {
                    // Fire ashar por check kortesi koto khon baire chilo
                    Duration timeOutside = Duration.between(awayStartTime, focusEvent.getTimestamp());
                    
                    // Jodi allowed time er beshi baire thake, sathe sathe suspicious!
                    if (timeOutside.getSeconds() > maxSecondsOutside) {
                        return true; 
                    }
                    
                    // Reset kore dilam porer barer jonno
                    awayStartTime = null; 
                }
            }
        }

        // Check: Switch count limit cross koreche kina
        return focusLossCount > maxAllowedSwitches;
    }
}