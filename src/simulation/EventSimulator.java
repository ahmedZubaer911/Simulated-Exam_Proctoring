package simulation;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import model.ExamSession;
import java.util.Random;

import model.Event;
import model.MouseEvent;
import model.SessionEvent;
import model.KeyEvent;
import model.FocusEvent;

public class EventSimulator {
    private ExamSession examSession;
    private boolean running;
    private  ScheduledExecutorService scheduler;
    private Random random;

    public EventSimulator(ExamSession examSession) {
        this.examSession = examSession;
        this.running = false;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.random = new Random();
    }
    
    public void start(){
        running = true;

        if (scheduler.isShutdown()) {
            scheduler = Executors.newSingleThreadScheduledExecutor();
        }
        
        examSession.addEvent(
            new SessionEvent(
                LocalDateTime.now(),
                "SESSION_START"
            )
        );

        scheduler.scheduleAtFixedRate(
            () -> {
                if (!running) {
                    return;
                }

                long elapsed = java.time.Duration.between(
                    examSession.getStartTime(),
                    java.time.LocalDateTime.now()
                ).getSeconds();

                if (elapsed >= examSession.getSessionTime()) {
                    stop();
                    return;
                }

                Event event = generateEvent();
                examSession.addEvent(event);
            },
            0,
            1,
            java.util.concurrent.TimeUnit.SECONDS
        );

        System.out.println("Simulation started.");
    }

    private Event generateEvent(){
        int eventType = random.nextInt(3);
        LocalDateTime timestamp = LocalDateTime.now();

        switch (eventType) {
            case 0:
                return new MouseEvent(timestamp, 
                    random.nextInt(1920), 
                    random.nextInt(1080)
                );
            case 1:
                String[] keys = {"A", "B", "C", "D", "E", "H", "I", "J", "R", "T", "V", "W", "X", "Y", "Z", 
                                "0", "1", "2", "3", "7", "9",
                                "Enter", "Space", "Ctrl", "PrtSc", "Tab", "Escape", "Backspace",
                                "Shift", "Alt", "Meta", "Windows"};
                return new KeyEvent(timestamp,
                    keys[random.nextInt(keys.length)]
                );
            
            case 2:
                String[] windows = {"Exam Window", "Browser", "Other Window"};
                return new FocusEvent(
                    timestamp,
                    windows[random.nextInt(windows.length)]
                );

            default:
                return null;
        }
    }

    public void stop(){

        if(!running){
            return;
        }

        examSession.addEvent(
            new SessionEvent(
                LocalDateTime.now(),
                "SESSION_END"
            )
        );
        running = false;

        scheduler.shutdown();
        System.out.println("Simulation stopped.");
    }

    public boolean isRunning(){
        return running;
    }
}
