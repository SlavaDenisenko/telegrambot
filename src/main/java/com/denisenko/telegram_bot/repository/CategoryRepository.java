package com.denisenko.telegram_bot.repository;

import com.denisenko.telegram_bot.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category getCategoryById(Long id);

    Category findByName(String name);
}
