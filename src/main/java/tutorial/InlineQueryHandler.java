package tutorial;

import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultPhoto;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InlineQueryHandler {

    private static final Logger logger = Logger.getLogger(InlineQueryHandler.class.getName());

    public static void handleInlineQuery(InlineQuery inlineQuery, DogPictureBot bot) {
        String query = inlineQuery.getQuery();
        String inlineQueryId = inlineQuery.getId();

        List<InlineQueryResult> results = new ArrayList<>();
        String photoUrl = PhotoFetcher.fetchDogPhoto();
        if (photoUrl != null) {
            InlineQueryResultPhoto result = new InlineQueryResultPhoto();
            result.setId("1");
            result.setPhotoUrl(photoUrl);
            result.setThumbUrl(photoUrl);
            results.add(result);
        }

        AnswerInlineQuery answerInlineQuery = new AnswerInlineQuery();
        answerInlineQuery.setInlineQueryId(inlineQueryId);
        answerInlineQuery.setResults(results);

        try {
            bot.execute(answerInlineQuery);
        } catch (TelegramApiException e) {
            logger.log(Level.SEVERE, "Error executing AnswerInlineQuery", e);
        }
    }
}

