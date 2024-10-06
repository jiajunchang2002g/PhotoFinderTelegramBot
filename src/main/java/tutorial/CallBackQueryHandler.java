package tutorial;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CallBackQueryHandler {

    private static final Logger logger = Logger.getLogger(CallBackQueryHandler.class.getName());

    public static void handleCallbackQuery(CallbackQuery callbackQuery, DogPictureBot bot) {
        String callbackData = callbackQuery.getData();
        long chatId = callbackQuery.getMessage().getChatId();
        int messageId = callbackQuery.getMessage().getMessageId();

        if (callbackData.equals("get_dog_picture")) {
            String photoUrl = PhotoFetcher.fetchDogPhoto();
            if (photoUrl != null) {
                PhotoSender.sendPhoto(chatId, photoUrl, bot);
            } else {
                editMessageText(chatId, messageId, "Failed to fetch dog photo.", bot);
            }
        }
    }

    private static void editMessageText(long chatId, int messageId, String text, DogPictureBot bot) {
        EditMessageText newMessage = new EditMessageText();
        newMessage.setChatId(String.valueOf(chatId));
        newMessage.setMessageId(messageId);
        newMessage.setText(text);

        try {
            bot.execute(newMessage);
        } catch (TelegramApiException e) {
            logger.log(Level.SEVERE, "Error editing message text", e);
        }
    }
}

