package tutorial;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageHandler {

    private static final Logger logger = Logger.getLogger(MessageHandler.class.getName());

    public static void handleMessage(Message message, DogPictureBot bot) {
        String messageText = message.getText();
        long chatId = message.getChatId();

        if (messageText.equals("/start")) {
            sendInlineKeyboard(chatId, "Welcome! Click the button below to get a random dog picture:", bot);
        } else {
            sendTextMessage(chatId, "Unknown command. Use /start to see the menu.", bot);
        }
    }

    private static void sendTextMessage(long chatId, String text, DogPictureBot bot) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            logger.log(Level.SEVERE, "Error sending text message", e);
        }
    }

    private static void sendInlineKeyboard(long chatId, String text, DogPictureBot bot) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Get Dog Picture");
        button.setCallbackData("get_dog_picture");

        rowInline.add(button);
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);

        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            logger.log(Level.SEVERE, "Error sending inline keyboard", e);
        }
    }
}

