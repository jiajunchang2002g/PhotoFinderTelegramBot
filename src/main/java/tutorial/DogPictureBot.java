package tutorial;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DogPictureBot extends TelegramLongPollingBot {
    private static String BOT_TOKEN ;
    private static String BOT_USERNAME ;


    public DogPictureBot() {

        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/main/resources/bot.properties")) {
            properties.load(input);
            BOT_TOKEN = properties.getProperty("token");
            BOT_USERNAME = properties.getProperty("username");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error accessing resource file", e);
        }
    }

    private static final Logger logger = Logger.getLogger(DogPictureBot.class.getName());

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            MessageHandler.handleMessage(update.getMessage(), this);
        } else if (update.hasCallbackQuery()) {
            CallBackQueryHandler.handleCallbackQuery(update.getCallbackQuery(), this);
        } else if (update.hasInlineQuery()) {
            InlineQueryHandler.handleInlineQuery(update.getInlineQuery(), this);
        }
    }
}
