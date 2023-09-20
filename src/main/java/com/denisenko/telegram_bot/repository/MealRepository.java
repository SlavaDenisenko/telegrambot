package com.denisenko.telegram_bot.repository;

import com.denisenko.telegram_bot.models.entities.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    Meal getMealById(Long id);
}
