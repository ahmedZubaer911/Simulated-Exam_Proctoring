package analysis;

import model.ExamSession; // Tomar friend ei class banabe

public interface Rule {
    // Return true if suspicious behavior is detected, false otherwise
    boolean evaluate(ExamSession session);
}