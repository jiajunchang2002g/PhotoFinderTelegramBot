package tutorial;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PhotoSender {

    private static final Logger logger = Logger.getLogger(PhotoSender.class.getName());

    public static void sendPhoto(long chatId, String photoUrl, DogPictureBot bot) {
        SendPhoto photo = new SendPhoto();
        photo.setChatId(String.valueOf(chatId));
        photo.setPhoto(new InputFile(photoUrl));

        try {
            bot.execute(photo);
        } catch (TelegramApiException e) {
            logger.log(Level.SEVERE, "Error sending photo", e);
        }
    }
}
