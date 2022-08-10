package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.storage.MemoryMealStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger Log = getLogger(MealServlet.class);

    private MealStorage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new MemoryMealStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "list" : request.getParameter("action");

        switch (action) {
            case "delete":
                int id = getId(request);
                Log.info("deleting meal {}", id);
                storage.delete(id);
                response.sendRedirect("meals");
                break;
            case "edit":
            case "add":
                final Meal meal = action.equals("add") ? new Meal(LocalDateTime.now(), "", 500) : storage.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("mealsEditForm.jsp").forward(request, response);
                break;
            case "list":
            default:
                Log.info("redirect to meals list");
                request.setAttribute("mealsList", MealsUtil.getListWithExceed(storage.getAll(), MealsUtil.CALORIES_LIMIT));
                request.getRequestDispatcher("meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws javax.servlet.ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        Meal meal = new Meal(
                id.isEmpty() ? null : Integer.parseInt(id),
                LocalDateTime.parse(req.getParameter("dateTime")),
                req.getParameter("description"),
                Integer.parseInt(req.getParameter("calories"))
        );
        Log.info(meal.isNew() ? "Creating {}" : "Updating {}", meal);
        storage.save(meal);
        resp.sendRedirect("meals");
    }

    private int getId(HttpServletRequest req) {
        String param = Objects.requireNonNull(req.getParameter("id"));
        return Integer.parseInt(param);
    }
}