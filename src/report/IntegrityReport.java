package report;

import model.RiskLevel; // তোমার ফ্রেন্ডকে এই Enum টা বানাতে হবে
import java.util.List;

public class IntegrityReport {
    private String sessionId;
    private List<String> flaggedReasons;
    private double riskScore;
    private RiskLevel riskLevel; 

    // Constructor
    public IntegrityReport(String sessionId, List<String> flaggedReasons) {
        this.sessionId = sessionId;
        this.flaggedReasons = flaggedReasons;
        this.riskScore = calculateRiskScore();
        this.riskLevel = classifyRiskLevel();
    }

    // লজিক: যতগুলো রুল ব্রেক করেছে, তার ওপর ভিত্তি করে ১০০ এর মধ্যে স্কোর দেওয়া
    private double calculateRiskScore() {
        if (flaggedReasons == null || flaggedReasons.isEmpty()) {
            return 0.0; // কোনো চিটিং হয়নি, স্কোর ০!
        }
        
        // প্রতিটা ভায়োলেশনের জন্য ২৫ পয়েন্ট করে যোগ হবে (তুমি চাইলে এটা চেঞ্জ করতে পারো)
        double score = flaggedReasons.size() * 25.0; 
        
        return Math.min(score, 100.0); // ম্যাক্সিমাম স্কোর ১০০ এর বেশি হবে না
    }

    
    private RiskLevel classifyRiskLevel() {
        if (riskScore == 0) return RiskLevel.LOW;
        if (riskScore <= 50) return RiskLevel.MEDIUM;
        return RiskLevel.HIGH;
    }

    // Getter methods (পরে UI তে দেখানোর জন্য এগুলো লাগবে)
    public String getSessionId() { return sessionId; }
    public List<String> getFlaggedReasons() { return flaggedReasons; }
    public double getRiskScore() { return riskScore; }
    public RiskLevel getRiskLevel() { return riskLevel; }

    // রিপোর্ট প্রিন্ট করার জন্য একটি মেথড
    public void printReport() {
        System.out.println("\n=== INTEGRITY REPORT ===");
        System.out.println("Session ID: " + sessionId);
        System.out.println("Risk Score: " + riskScore + "/100");
        System.out.println("Risk Level: " + riskLevel);
        
        if (flaggedReasons.isEmpty()) {
            System.out.println("Status: CLEAN. No violations.");
        } else {
            System.out.println("Violations Found:");
            for (String reason : flaggedReasons) {
                System.out.println(" - " + reason);
            }
        }
        System.out.println("========================\n");
    }
}