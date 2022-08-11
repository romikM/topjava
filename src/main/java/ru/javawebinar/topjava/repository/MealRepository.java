package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.List;

public interface MealRepository {

    Meal save(Meal meal, int userId);

    boolean delete(int id, int UserId);

    Meal get(int id, int UserId);

    List<Meal> getAll(int UserId);

    List<Meal> getBetweenHalfOpen(LocalDate dateFrom, LocalDate dateTo, int userId);
}
