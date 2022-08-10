package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryMealStorage implements MealStorage {

    private final Map<Integer, Meal> storage = new ConcurrentHashMap<>();
    private final AtomicInteger mealId = new AtomicInteger(0);

    {
        MealsUtil.mealsList.forEach(this::save);
    }

    public Meal get(int id) {
        return storage.get(id);
    }

    @Override
    public Meal save(Meal m) {
        if (m.isNew()) {
            m.setId(mealId.incrementAndGet());
            storage.put(m.getId(), m);
            return m;
        } else {
            return storage.replace(m.getId(), m) == null ? null : m;
        }
    }

    @Override
    public void delete(int id) {
        storage.remove(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return storage.values();
    }
}
