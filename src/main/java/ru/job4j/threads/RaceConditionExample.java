package ru.job4j.threads;

import java.util.concurrent.Semaphore;

public class RaceConditionExample {
    private static RaceConditionExample rc;
    private static RaceConditionExample oldrc;

    private RaceConditionExample() {
    }

    public static RaceConditionExample getInstance() {
        if (rc == null) {
            System.out.println(Thread.currentThread().getName());
            rc = new RaceConditionExample();
        }
        if(oldrc != null) {
            System.out.println(oldrc == rc);
        }
        oldrc = rc;
        return rc;
    }

    public static void main(String[] args) throws InterruptedException {
        Semaphore sem = new Semaphore(0);
        Runnable task = () -> {
            try {
                sem.acquire();
                System.out.println("Нить выполнила задачу");
                sem.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        new Thread(task).start();
        Thread.sleep(3000);
        //sem.release(1);
    }
}
