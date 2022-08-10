package ru.javawebinar.topjava.repository;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    // null if updated meal does not belong to userId
    Meal save(Meal meal, int userId);

    // false if meal does not belong to userId
    boolean delete(int id, int UserId);

    // null if meal does not belong to userId
    Meal get(int id, int UserId);

    // ORDERED dateTime desc
    List<Meal> getAll(int UserId);
}
