package com.company.telegrambot;

import com.company.telegrambot.model.Politician;
import com.company.telegrambot.repository.PoliticianRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
public class PoliticianDescriptionBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.username}")
    private String username;
    @Value("${telegram.bot.token}")
    private String token;
    private final PoliticianRepository repository;

    public PoliticianDescriptionBot(PoliticianRepository repository) {
        this.repository = repository;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                String text = message.getText();
                Politician politician = repository.getBySurname(text);

                SendMessage sendMessage = new SendMessage();
                sendMessage.setText(text + politician.getMessage());
                sendMessage.setChatId(String.valueOf(message.getChatId()));
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
