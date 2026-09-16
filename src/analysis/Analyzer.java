package analysis;

import model.ExamSession;
import java.util.ArrayList;
import java.util.List;

public class Analyzer {
    private List<Rule> rules;

    public Analyzer() {
        this.rules = new ArrayList<>();
    }

    public void addRule(Rule rule) {
        rules.add(rule);
    }

    public void analyzeSession(ExamSession session) {
        // This will loop through all added rules and test the session
        for (Rule rule : rules) {
            boolean isSuspicious = rule.evaluate(session);
            if (isSuspicious) {
                System.out.println("Flagged by: " + rule.getClass().getSimpleName());
                // Later, we will store these results in the IntegrityReport
            }
        }
    }
}