package com.denisenko.telegram_bot.components;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface BotCommands {
    List<BotCommand> LIST_OF_COMMANDS = List.of(
            new BotCommand("/start", "start bot"),
            new BotCommand("/help", "bot info")
    );

    String HELP_TEXT = """
            Этот бот поможет Вам выучить меню или вспомнить то, что забыли.\s
            Выберите одну из следующих команд:

            /start - запуск бота
            /help - помощь""";
}
