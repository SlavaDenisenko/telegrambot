package com.denisenko.telegram_bot.components;

import com.denisenko.telegram_bot.models.entities.Category;
import com.denisenko.telegram_bot.models.entities.Meal;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class Buttons {
    public static ReplyKeyboardMarkup getMainMenuKeyboard(List<Category> categories) {
        int countOfRows;
        int countOfButtons = categories.size();
        int countOfLongNames = 0;
        List<KeyboardRow> keyboard = new ArrayList<>();

        for (Category category : categories) {
            if (category.getName().length() > 20) {
                countOfLongNames++;
            }
        }

        if (countOfButtons < 4) {
            if (countOfLongNames > 0) {
                for (Category category : categories) {
                    KeyboardRow row = new KeyboardRow();
                    row.add(category.getName());
                    keyboard.add(row);
                }
            } else {
                KeyboardRow row = new KeyboardRow();

                for (Category category : categories) {
                    row.add(category.getName());
                }

                keyboard.add(row);
            }
        }

        if (countOfLongNames < 1) {
            countOfRows = (categories.size() + 2) / 3;

            if (countOfButtons % 3 == 0) {
                while (countOfRows > 0) {
                    KeyboardRow row = new KeyboardRow();

                    for (int i = 0; i < 3; i++) {
                        row.add(categories.get(i).getName());
                    }

                    categories = categories.stream().skip(3).toList();
                    keyboard.add(row);
                    countOfRows--;
                }
            }

            if (countOfButtons % 3 == 1) {
                while (countOfRows > 0) {
                    KeyboardRow row = new KeyboardRow();

                    if (countOfRows < 3) {
                        for (int i = 0; i < 2; i++) {
                            row.add(categories.get(i).getName());
                        }

                        categories = categories.stream().skip(2).toList();
                    } else {
                        for (int i = 0; i < 3; i++) {
                            row.add(categories.get(i).getName());
                        }

                        categories = categories.stream().skip(3).toList();
                    }

                    keyboard.add(row);
                    countOfRows--;
                }
            }

            if (countOfButtons % 3 == 2) {
                while (countOfRows > 0) {
                    KeyboardRow row = new KeyboardRow();

                    if (countOfRows < 2) {
                        for (int i = 0; i < 2; i++) {
                            row.add(categories.get(i).getName());
                        }
                    } else {
                        for (int i = 0; i < 3; i++) {
                            row.add(categories.get(i).getName());
                        }

                        categories = categories.stream().skip(3).toList();
                    }

                    keyboard.add(row);
                    countOfRows--;
                }
            }
        }

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup getSecondLevelMenuKeyboard(List<Meal> meals) {
        KeyboardRow row = new KeyboardRow();
        List<KeyboardRow> keyboard = new ArrayList<>();

        for (Meal meal : meals) {
            if (row.size() < 3) {
                row.add(meal.getName());
            } else {
                KeyboardRow nextRow = new KeyboardRow();
                nextRow.add(meal.getName());
                keyboard.add(nextRow);
            }
        }

        keyboard.add(row);

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }
}
