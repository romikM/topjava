package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ + 3;
    public static final int ADMIN_MEAL_ID = MEAL_ID + 7;
    public static final int NOT_FOUND = 100;

    public static final Meal meal1 = new Meal(MEAL_ID, LocalDateTime.of(2020, Month.AUGUST, 12, 9, 30), "Завтрак", 500);
    public static final Meal meal2 = new Meal(MEAL_ID + 1, LocalDateTime.of(2020, Month.AUGUST, 12, 13, 0), "Обед", 1000);
    public static final Meal meal3 = new Meal(MEAL_ID + 2, LocalDateTime.of(2020, Month.AUGUST, 12, 19, 10), "Ужин", 500);
    public static final Meal meal4 = new Meal(MEAL_ID + 3, LocalDateTime.of(2020, Month.AUGUST, 12, 0, 0), "Еда на граничное значение", 100);
    public static final Meal meal5 = new Meal(MEAL_ID + 4, LocalDateTime.of(2020, Month.AUGUST, 13, 9, 0), "Завтрак", 1000);
    public static final Meal meal6 = new Meal(MEAL_ID + 5, LocalDateTime.of(2020, Month.AUGUST, 13, 12, 30), "Обед", 500);
    public static final Meal meal7 = new Meal(MEAL_ID + 6, LocalDateTime.of(2020, Month.AUGUST, 13, 18, 30), "Ужин", 410);
    public static final Meal adminMeal1 = new Meal(ADMIN_MEAL_ID, LocalDateTime.of(2020, Month.AUGUST, 13, 13, 35), "Обед админа", 500);
    public static final Meal adminMeal2 = new Meal(ADMIN_MEAL_ID + 1, LocalDateTime.of(2020, Month.AUGUST, 13, 19, 45), "Ужин админа", 410);

    public static final List<Meal> meals = Arrays.asList(meal7, meal6, meal5, meal3, meal2, meal1, meal4);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2020, Month.AUGUST, 15, 15, 15), "Полдник", 250);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal3);
        updated.setDateTime(LocalDateTime.of(2020, Month.AUGUST, 12, 14, 0));
        updated.setDescription("Файв'О'клок");
        updated.setCalories(330);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }

}
