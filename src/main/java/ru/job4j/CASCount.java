package ru.job4j;

import java.util.concurrent.atomic.AtomicReference;

public class CASCount {
    private final AtomicReference<Integer> count;

    public CASCount(int num) {
        this.count = new AtomicReference<>(num);
    }

    public void increment() {
        int ref;
        int counted;
        do {
            ref = count.get();
            counted = ref + 1;
        } while (!count.compareAndSet(ref, counted));
    }

    public int get() {
        int ref;
        do {
            ref = count.get();
        } while (!count.compareAndSet(ref, ref));
        return ref;
    }
}
