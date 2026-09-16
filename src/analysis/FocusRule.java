package analysis;

import model.ExamSession;
// import model.Event; (Need to import these when your friend pushes their code)
// import model.FocusEvent; 

public class FocusRule implements Rule {
    private int maxAllowedSwitches;

    // Constructor e amra threshold set kore dibo
    public FocusRule(int maxAllowedSwitches) {
        this.maxAllowedSwitches = maxAllowedSwitches;
    }

    @Override
    public boolean evaluate(ExamSession session) {
        int focusLossCount = 0;

        // Ekhane amader logic hobe:
        // 1. Session theke shob events get kora
        // 2. Loop chalaye check kora konta FocusEvent
        // 3. Jodi FocusEvent paoi, count++ kora
        // 4. focusLossCount > maxAllowedSwitches hole true (suspicious) return kora

        return false; // temporary return
    }
}