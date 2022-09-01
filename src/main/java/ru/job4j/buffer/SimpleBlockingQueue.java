package ru.job4j.buffer;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    private final int capacity;

    public SimpleBlockingQueue(final int capacity) {
        this.capacity = capacity;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    public void offer(T value) throws InterruptedException {
        synchronized (this) {
            while (capacity == queue.size()) {
                this.wait();
            }
            queue.offer(value);
            this.notify();
        }
    }

    public T poll() throws InterruptedException {
        synchronized (this) {
            while (queue.isEmpty()) {
                    this.wait();
            }
            T res = queue.poll();
            this.notify();
            return res;
        }
    }
}
