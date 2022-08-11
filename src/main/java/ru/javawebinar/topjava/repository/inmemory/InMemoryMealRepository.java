package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {

    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(m -> save(m, 1));
        MealsUtil.adminMeals.forEach(m -> save(m, 2));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> userMeals = repository.getOrDefault(userId, new HashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            userMeals.put(meal.getId(), meal);
            return meal;
        }
        return userMeals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        return userMeals != null && userMeals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        return userMeals == null ? null : userMeals.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        return CollectionUtils.isEmpty(userMeals) ? Collections.emptyList() :
                userMeals.values().stream()
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        .collect(Collectors.toList());
    }

    public List<Meal> getBetweenHalfOpen(LocalDate dateFrom, LocalDate dateTo, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        return userMeals.values()
                .stream()
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDate(), dateFrom, dateTo))
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }
}

