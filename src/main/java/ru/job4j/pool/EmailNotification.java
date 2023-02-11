package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private static final String SUBJECT_TEMPLATE = "Notification %s to email %s";
    private static final String BODY_TEMPLATE = "Add a new event to %s";
    private final ExecutorService pool = Executors.newFixedThreadPool(
        Runtime.getRuntime().availableProcessors()
    );

    public void emailTo(User user) {
        pool.submit(() -> send(
            String.format(SUBJECT_TEMPLATE, user.getUsername(), user.getEmail()),
            String.format(BODY_TEMPLATE, user.getUsername())
        ));
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void send(String subject, String username) {
    }
}
