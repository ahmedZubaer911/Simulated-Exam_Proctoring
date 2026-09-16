package analysis;

import model.ExamSession;
import model.Event;
import model.KeyEvent;
import java.util.List;

public class ShortcutRule implements Rule {

    @Override
    public boolean evaluate(ExamSession session) {
        List<Event> events = session.getEvents();
        if (events == null || events.isEmpty()) return false;

        boolean isCtrlPressed = false;
        boolean isAltPressed = false;

        for (Event event : events) {
            if (event instanceof KeyEvent) {
                KeyEvent keyEvent = (KeyEvent) event;
                // সব কীকোড বড় হাতের অক্ষরে কনভার্ট করে নিচ্ছি যেন মেলাতে সুবিধা হয়
                String code = keyEvent.getKeyCode().toUpperCase();

                // ১. Blacklisted Keys চেক করা
                if (code.equals("PRINTSCREEN") || code.equals("WINDOWS") || code.equals("META")) {
                    System.out.println("Flagged: Forbidden Key (" + code + ") pressed!");
                    return true;
                }

                // Modifier keys ট্র্যাক করা
                if (code.equals("CTRL")) {
                    isCtrlPressed = true;
                    continue; // Ctrl পেলেও পরের ইভেন্টের জন্য অপেক্ষা করব
                } else if (code.equals("ALT")) {
                    isAltPressed = true;
                    continue;
                }

                // ২. Forbidden Combinations চেক করা (Ctrl + C / Ctrl + V / Alt + Tab)
                if (isCtrlPressed && (code.equals("C") || code.equals("V") || code.equals("A"))) {
                    System.out.println("Flagged: Copy/Paste Shortcut Used (Ctrl + " + code + ")");
                    return true;
                }

                if (isAltPressed && code.equals("TAB")) {
                    System.out.println("Flagged: Window Switch Shortcut Used (Alt + Tab)");
                    return true;
                }

                // যদি অন্য কোনো নরমাল বাটন প্রেস করে, তবে Modifier স্টেট রিসেট করে দিব
                isCtrlPressed = false;
                isAltPressed = false;
            }
        }

        return false;
    }
}