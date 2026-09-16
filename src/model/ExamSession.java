package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ExamSession {
    private String studentName;
    private String studentID;
    private LocalDateTime startTime;
    private int sessionTime;
    private List<Event> events;

    public ExamSession(String studentName, String studentID, int sessionTime) {
        this.studentName = studentName;
        this.studentID = studentID;
        this.sessionTime = sessionTime;
        this.startTime = LocalDateTime.now();
        this.events = new ArrayList<>();
    }

    // Getter Methods
    public String getStudentName() {
        return studentName;
    }

    public String getStudentID() {
        return studentID;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public int getSessionTime() {
        return sessionTime;
    }

    //Other methods
    public void addEvent(Event event){
        events.add(event);
    }
    public List<Event> getEvents(){
        return events;
    }
}
