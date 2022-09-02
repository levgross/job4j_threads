package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class CacheTest {
    @Test
    public void whenAddThenDelete() {
        Cache cache = new Cache();
        Base first = new Base(1,0);
        first.setName("one");
        Base second = new Base(2,0);
        second.setName("two");
        Base third = new Base(3,0);
        third.setName("three");
        cache.add(first);
        cache.add(second);
        cache.add(third);
        cache.delete(first);
        cache.delete(third);
        Cache expected = new Cache();
        expected.add(second);
        assertThat(cache).isEqualTo(expected);
    }

    @Test
    public void whenUpdate() {
        Cache cache = new Cache();
        Base first = new Base(1,0);
        first.setName("one");
        cache.add(first);
        first.setName("two");
        cache.update(first);
        Base second = new Base(1,1);
        second.setName("two");
        Cache expected = new Cache();
        expected.add(second);
        assertThat(cache).isEqualTo(expected);
    }
    @Test
    public void when2Updates() {
        Cache cache = new Cache();
        Base first = new Base(1,0);
        first.setName("one");
        cache.add(first);
        first.setName("two");
        cache.update(first);
        Base second = new Base(1,1);
        cache.update(second);
        Base third = new Base(1,2);
        Cache expected = new Cache();
        expected.add(third);
        assertThat(cache).isEqualTo(expected);
    }
}