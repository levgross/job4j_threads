package ru.job4j.cache;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {

        return memory.computeIfPresent(model.getId(), (key, val) -> {
            if (val.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            Base newBase = new Base(key, val.getVersion() + 1);
            newBase.setName(model.getName());
            return newBase;

        }) != null;
    }

    public boolean delete(Base model) {
        return memory.remove(model.getId()) != null;
    }

    @Override
    public String toString() {
        return "Cache{"
                + "memory=" + memory
                + '}';
    }
}
