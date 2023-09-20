package com.denisenko.telegram_bot.service;

import com.denisenko.telegram_bot.models.entities.Category;

import java.util.List;

public interface CategoryService {
    Category getCategory(Long id);

    Category getCategory(String name);

    List<Category> getAllCategories();

    void createCategory(Category category);

    void deleteCategory(Long id);
}
