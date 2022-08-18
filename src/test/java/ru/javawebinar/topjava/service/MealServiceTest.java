package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal actual = service.get(mealId, USER_ID);
        assertMatch(actual, meal1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getNotOwn() {
        assertThrows(NotFoundException.class, () -> service.get(mealId, ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(mealId, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(mealId, USER_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void deleteNotOwn() {
        assertThrows(NotFoundException.class, () -> service.delete(mealId, ADMIN_ID));
    }

    @Test
    public void getBetweenInBounds() {
        assertMatch(service.getBetweenInclusive(
                        LocalDate.of(2020, Month.AUGUST, 13),
                        LocalDate.of(2020, Month.AUGUST, 13), USER_ID),
                meal7, meal6, meal5);
    }

    @Test
    public void getBetweenNoDates() {
        assertMatch(service.getBetweenInclusive(null, null, USER_ID), meals);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER_ID), meals);
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), USER_ID);
        int newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void createSameDateTime() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(null, meal1.getDateTime(), "meal at the same time", 650), USER_ID));
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(mealId + 2, USER_ID), getUpdated());
    }

    @Test
    public void updateNotOwn() {
        assertThrows(NotFoundException.class, () -> service.update(meal1, ADMIN_ID));
        assertMatch(service.get(mealId, USER_ID), meal1);
    }
}

