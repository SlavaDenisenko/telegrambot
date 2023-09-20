package com.denisenko.telegram_bot.service;

import com.denisenko.telegram_bot.models.entities.Meal;

import java.util.List;

public interface MealService {
    Meal getMeal(Long id);

    List<Meal> getAllMeals();

    void createMeal(Meal meal);

    void deleteMeal(Long id);
}
