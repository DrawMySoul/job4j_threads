package ru.job4j.wait;

import net.jcip.annotations.ThreadSafe;
import net.jcip.annotations.GuardedBy;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private final int total;

    public SimpleBlockingQueue(int total) {
        this.total = total;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == total) {
            wait();
        }
        queue.offer(value);
        notify();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T result = queue.poll();
        notify();
        return result;
    }
}
