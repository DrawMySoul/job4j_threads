package ru.job4j.wait;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    void whenOfferAndPoll() throws InterruptedException {
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(3);
        List<Integer> expected = new ArrayList<>();
        Thread producer = new Thread(
            () -> {
                for (int i = 0; i < 10; i++) {
                    try {
                        sbq.offer(i);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        );

        Thread consumer = new Thread(
            () -> {
                for (int i = 0; i < 10; i++) {
                    try {
                        expected.add(sbq.poll());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(expected).isNotEmpty()
            .startsWith(0)
            .endsWith(9)
            .containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    void whenOfferAndPollParallelSearch() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        Thread producer = new Thread(
            () -> IntStream.range(0, 10).forEach(
                value -> {
                    try {
                        queue.offer(value);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            )
        );
        Thread consumer = new Thread(
            () -> {
                while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                    try {
                        buffer.add(queue.poll());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).isNotEmpty()
            .startsWith(0)
            .endsWith(9)
            .containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    }
}