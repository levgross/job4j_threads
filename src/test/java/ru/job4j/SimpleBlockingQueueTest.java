package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {
    @Test
    public void whenOfferThenPoll() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(() -> queue.offer(1));
        Thread consumer = new Thread(queue::poll);
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }
}
