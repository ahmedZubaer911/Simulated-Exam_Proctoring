package analysis;

import model.ExamSession;
import model.Event;
import model.KeyEvent; // তোমার ফ্রেন্ডকে এই ক্লাসটি বানাতে হবে
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class KeyboardRule implements Rule {
    private int burstKeyCount; 
    private long burstTimeFrameMillis; 

    // Constructor: কতগুলো কি (burstKeyCount) কত মিলি-সেকেন্ডের (burstTimeFrameMillis) মধ্যে প্রেস করলে সাসপিসিয়াস ধরব
    public KeyboardRule(int burstKeyCount, long burstTimeFrameMillis) {
        this.burstKeyCount = burstKeyCount;
        this.burstTimeFrameMillis = burstTimeFrameMillis;
    }

    @Override
    public boolean evaluate(ExamSession session) {
        List<Event> events = session.getEvents();
        if (events == null || events.isEmpty()) return false;

        // স্টেপ ১: সেশন থেকে শুধুমাত্র KeyEvent গুলো আলাদা করে একটা নতুন লিস্টে রাখছি
        List<KeyEvent> keyEvents = new ArrayList<>();
        for (Event event : events) {
            if (event instanceof KeyEvent) {
                keyEvents.add((KeyEvent) event);
            }
        }

        // স্টেপ ২: Rapid Key Burst লজিক চেক করা
        // আমরা চেক করব i নম্বর কি এবং তার আগের (burstKeyCount - 1) নম্বর কি এর মাঝে সময়ের গ্যাপ কত
        for (int i = burstKeyCount - 1; i < keyEvents.size(); i++) {
            KeyEvent currentKey = keyEvents.get(i);
            KeyEvent pastKey = keyEvents.get(i - (burstKeyCount - 1));

            // Duration.between() দিয়ে মিলি-সেকেন্ডে গ্যাপ বের করছি
            long timeDifference = Duration.between(pastKey.getTimestamp(), currentKey.getTimestamp()).toMillis();

            // যদি নির্দিষ্ট সময়ের চেয়েও কম সময়ে এতগুলো কি প্রেস হয়ে যায়, তারমানে এটা বট বা কপি-পেস্ট!
            if (timeDifference < burstTimeFrameMillis) {
                System.out.println("Flagged: Rapid key burst detected! " + burstKeyCount + " keys pressed in " + timeDifference + " ms.");
                return true; 
            }
        }

        return false;
    }
}