package ru.job4j.buffer;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    public void offer(T value) {
        synchronized (this) {
            while (!queue.offer(value)) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            this.notify();
        }
    }

    public T poll() throws InterruptedException {
        synchronized (this) {
            while (queue.peek() == null) {
                    this.wait();
            }
            T res = queue.poll();
            this.notify();
            return res;
        }
    }
}
