package com.denisenko.telegram_bot.config;

import com.denisenko.telegram_bot.components.BotCommands;
import com.denisenko.telegram_bot.components.Buttons;
import com.denisenko.telegram_bot.models.entities.Category;
import com.denisenko.telegram_bot.models.entities.Meal;
import com.denisenko.telegram_bot.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot implements BotCommands {
    private final BotConfig botConfig;
    private final CategoryService categoryService;

    public TelegramBot(BotConfig botConfig, CategoryService categoryService) {
        this.botConfig = botConfig;
        this.categoryService = categoryService;

        try {
            this.execute(new SetMyCommands(LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Long chatId;
        String username;
        String receivedMessage;

        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            username = update.getMessage().getFrom().getFirstName();

            if (update.getMessage().hasText()) {
                receivedMessage = update.getMessage().getText();
                botAnswerUtils(receivedMessage, chatId, username);
            }
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            username = update.getCallbackQuery().getFrom().getFirstName();
            receivedMessage = update.getCallbackQuery().getData();

            botAnswerUtils(receivedMessage, chatId, username);
        }
    }

    private void botAnswerUtils(String receivedMessage, Long chatId, String username) {
        if (receivedMessage.equals("/start")) {
            startBot(chatId, username);
        } else if (receivedMessage.equals("/help")) {
            sendMessage(chatId, HELP_TEXT);
        } else {
            secondLevel(chatId, receivedMessage);
        }
    }

    //TODO
    //если пользователь будет вводить много сообщений

    //TODO
    //кнопка назад

    //TODO
    //полная распечатка меню для стажеров

    //TODO
    //создание новой позиции (доступно только для админов)

    //TODO
    //изменение текущей позиции

    //TODO
    //если в названии кнопки больше определенного количества букв для сайза больше трех,
    // кнопка должна быть широкой, чтобы текст не вытягивал ее слишком, растягивая тем самым экран

    //TODO
    //вынести логику определения расположения кнопок в отдельный метод

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }

    private void startBot(Long chatId, String name) {
        String answer = "Привет, " + name + ", рад видеть тебя!" + "\n" +
                "Выбери категорию блюда, чтобы продолжить.";
        SendMessage message = new SendMessage();
        List<Category> categories = categoryService.getAllCategories();
        message.setChatId(chatId);
        message.setText(answer);
        message.setReplyMarkup(Buttons.getMainMenuKeyboard(categories));

        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void secondLevel(Long chatId, String receivedMessage) {
        Category category = categoryService.getCategory(receivedMessage);
        List<Meal> meals = category.getMeals();
        String answer = "Окей, теперь выбери блюдо";
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(answer);
        message.setReplyMarkup(Buttons.getSecondLevelMenuKeyboard(meals));

        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void sendMessage(Long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(textToSend);

        try {
            execute(sendMessage);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
