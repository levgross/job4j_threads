package ru.job4j.buffer;

import org.junit.jupiter.api.Test;
import ru.job4j.buffer.SimpleBlockingQueue;

class SimpleBlockingQueueTest {
    @Test
    public void whenOfferThenPoll() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(() -> {
            try {
                queue.offer(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                queue.poll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }
}
