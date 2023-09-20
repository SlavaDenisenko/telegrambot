package com.denisenko.telegram_bot.service.impl;

import com.denisenko.telegram_bot.models.entities.Meal;
import com.denisenko.telegram_bot.repository.MealRepository;
import com.denisenko.telegram_bot.service.MealService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MealServiceImpl implements MealService {
    private final MealRepository mealRepository;

    @Override
    public Meal getMeal(Long id) {
        return mealRepository.getMealById(id);
    }

    @Override
    public List<Meal> getAllMeals() {
        return mealRepository.findAll();
    }

    @Override
    public void createMeal(Meal meal) {
        mealRepository.save(meal);
    }

    @Override
    public void deleteMeal(Long id) {
        mealRepository.deleteById(id);
    }
}
