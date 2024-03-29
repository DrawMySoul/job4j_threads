package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (k, v) -> {
                if (model.getVersion() != v.getVersion()) {
                    throw new OptimisticException("Model's versions aren't equals");
                }
                Base newModel = new Base(v.getId(), v.getVersion() + 1);
                newModel.setName(v.getName());
                return newModel;
            }
        ) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public Base get(Base model) {
        return memory.get(model.getId());
    }
}
