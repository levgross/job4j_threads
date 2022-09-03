package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class CacheTest {
    @Test
    public void whenAddThenDelete() {
        Cache cache = new Cache();
        Base first = new Base(1, 0);
        first.setName("one");
        Base second = new Base(2, 0);
        second.setName("two");
        Base third = new Base(3, 0);
        third.setName("three");
        assertThat(cache.add(first)).isTrue();
        assertThat(cache.add(first)).isFalse();
        assertThat(cache.add(second)).isTrue();
        assertThat(cache.delete(first)).isTrue();
        assertThat(cache.delete(first)).isFalse();
        assertThat(cache.delete(third)).isFalse();
    }

    @Test
    public void whenUpdate() {
        Cache cache = new Cache();
        Base first = new Base(1, 0);
        first.setName("one");
        cache.add(first);
        first.setName("two");
        assertThat(cache.update(first)).isTrue();
    }
}
