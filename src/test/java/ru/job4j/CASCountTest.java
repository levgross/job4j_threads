package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {
    @Test
    public void whenTwoThreads() throws InterruptedException {
        CASCount count = new CASCount(0);
        Thread first = new Thread(
                () -> {
                    IntStream.range(0, 10).forEach(
                            (i) -> count.increment()
                    );
                }
        );
        Thread second = new Thread(
                () -> {
                    IntStream.range(0, 10).forEach(
                            (i) -> count.increment()
                    );
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(count.get()).isEqualTo(20);
    }

    @Test
    public void whenThreeThreads() throws InterruptedException {
        CASCount count = new CASCount(-128);
        Thread first = new Thread(
                () -> {
                    IntStream.range(0, 100).forEach(
                            (i) -> count.increment()
                    );
                }
        );
        Thread second = new Thread(
                () -> {
                    IntStream.range(0, 100).forEach(
                            (i) -> count.increment()
                    );
                }
        );
        Thread third = new Thread(
                () -> {
                    IntStream.range(0, 56).forEach(
                            (i) -> count.increment()
                    );
                }
        );
        first.start();
        second.start();
        third.start();
        first.join();
        second.join();
        third.join();
        assertThat(count.get()).isEqualTo(128);
    }
}