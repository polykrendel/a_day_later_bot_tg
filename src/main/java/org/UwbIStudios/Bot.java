package org.UwbIStudios;

import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bot implements LongPollingSingleThreadUpdateConsumer {

    public String getBotToken() {
        return BotConfig.TOKEN;
    }

    private final TelegramClient telegramClient;

    public Bot() {
        telegramClient = new OkHttpTelegramClient(getBotToken());
    }

    public void consume(Update update) {

        if (update.hasMessage()) {
            var msg = update.getMessage();
            Long chatId1 = msg.getChatId();
            String chatId = chatId1.toString();

            // реакция на текст
            if (msg.hasText()) {
                String text = msg.getText();

                if (text.toLowerCase().contains("аниме") || text.toLowerCase().contains("когда сландер")) {
                    sendSticker(chatId);
                }
            }

            // реакция на стикер
            if (msg.hasSticker()) {
                var sticker = msg.getSticker();

                log(sticker);

                if (sticker.getSetName().equals("LINE_HATSUNE_MIKU_Pom_Ver")) {
                    sendSticker(chatId);
                }
            }
        }
    }

    private void sendSticker(String chatId) {
        InputFile stickerFile = new InputFile("CAACAgIAAxkBAnwAAalpKAl9kVv1DpyorR5xkPsxGxUVbQACd3AAAqXluUtj3trBZjkoPDYE");
        SendSticker send = new SendSticker(chatId, stickerFile);

        try {
            telegramClient.execute(send);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void log(Sticker sticker) {
        System.out.println("\n-----------------------------");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println("Набор стикеров:\n" + sticker.getSetName());
    }
}
