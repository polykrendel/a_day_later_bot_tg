package org.UwbIStudios;

import io.github.cdimascio.dotenv.Dotenv;

public class BotConfig {

    private static final Dotenv dotenv = Dotenv.configure()
            .filename(".env")
            .load();

    public static final String TOKEN = dotenv.get("BOT_TOKEN");
    public static final String USERNAME = dotenv.get("BOT_USERNAME");
}
