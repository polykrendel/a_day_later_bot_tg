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
import java.util.ArrayList;
import java.util.Date;

public class Bot implements LongPollingSingleThreadUpdateConsumer {

    public ArrayList<String> anime = new ArrayList<>();

    //список аниме-позорв
    public void setAnime(ArrayList<String> anime) {
        anime.add("LINE_HATSUNE_MIKU_Pom_Ver");
        anime.add("s53361352efcd40c398a93c94e67eb794_by_zamenasrikedotjbot");
        anime.add("Bocchi_the_Rock_Part_1_by_Fix_x_Fox");
        anime.add("pihhta_by_fStikBot");
        anime.add("TYANSBER");
        anime.add("Tensura_Manga_Novel");
    }

    public String getBotToken() {
        return BotConfig.TOKEN;
    }

    private final TelegramClient telegramClient;

    public Bot() {
        telegramClient = new OkHttpTelegramClient(getBotToken());
        setAnime(anime);
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
                String stickerPackName = sticker.getSetName();

                log(sticker);

                if (anime.contains(stickerPackName) || sticker.getFileId().equals("CAACAgIAAxkBAANXaShp6x6WeGFi0dmd7OYUvKPtzrIAAhVmAAKCK3BJJFB05GO7KCs2BA")) {
                    sendSticker(chatId);
                }
            }
        }
    }

    //функция отправки стикера
    private void sendSticker(String chatId) {
        InputFile stickerFile = new InputFile("CAACAgIAAxkBAnwAAalpKAl9kVv1DpyorR5xkPsxGxUVbQACd3AAAqXluUtj3trBZjkoPDYE");
        SendSticker send = new SendSticker(chatId, stickerFile);

        try {
            telegramClient.execute(send);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    //логирование названия набора стикеров
    private void log(Sticker sticker) {
        System.out.println("\n-----------------------------");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println("Набор стикеров:\n" + sticker.getSetName());
        System.out.println(sticker.getFileId());
    }
}
