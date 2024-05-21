package tutorial;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class Bot extends TelegramLongPollingBot {

    private boolean screaming = false;
    private InlineKeyboardMarkup keyboardM1;
    private InlineKeyboardMarkup keyboardM2;

    @Override
    public String getBotUsername() {
        return "Chang_2002";
    }

    @Override
    public String getBotToken() {
        return "7164195520:AAHQMBXOMHghUmz6_6--3B4UZeduN847TcI";
    }

    public void sendText(Long who, String what) {
        SendMessage sm = SendMessage.builder().chatId(who.toString()) //Who are we sending a message to
                .text(what).build();    //Message content
        try {
            execute(sm);                        //Actually sending the message
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);      //Any error will be printed here
        }
    }

    public void copyMessage(Long who, Integer msgId) {
        CopyMessage cm = CopyMessage.builder().fromChatId(who.toString())  //We copy from the user
                .chatId(who.toString())      //And send it back to him
                .messageId(msgId)            //Specifying what message
                .build();
        try {
            execute(cm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void scream(Long id, Message msg) {
        if (msg.hasText()) sendText(id, msg.getText().toUpperCase());
        else copyMessage(id, msg.getMessageId());
    }

    public void sendMenu(Long who, String txt, InlineKeyboardMarkup kb) {
        SendMessage sm = SendMessage.builder().chatId(who.toString()).parseMode("HTML").text(txt).replyMarkup(kb).build();

        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void buttonTap(Long id, String queryId, String data, int msgId) {

        EditMessageText newTxt = EditMessageText.builder().chatId(id.toString()).messageId(msgId).text("").build();

        EditMessageReplyMarkup newKb = EditMessageReplyMarkup.builder().chatId(id.toString()).messageId(msgId).build();

        if (data.equals("next")) {
            newTxt.setText("MENU 2");
            newKb.setReplyMarkup(keyboardM2);
        } else if (data.equals("back")) {
            newTxt.setText("MENU 1");
            newKb.setReplyMarkup(keyboardM1);
        }

        AnswerCallbackQuery close = AnswerCallbackQuery.builder().callbackQueryId(queryId).build();

        try {
            execute(close);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        try {
            execute(newTxt);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        try {
            execute(newKb);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

        //create buttons
        var next = InlineKeyboardButton.builder().text("Next").callbackData("next").build();
        var back = InlineKeyboardButton.builder().text("Back").callbackData("back").build();
        var url = InlineKeyboardButton.builder().text("Tutorial").url("https://core.telegram.org/bots/api").build();
        //built and assign keyboards
        keyboardM1 = InlineKeyboardMarkup.builder().keyboardRow(List.of(next)).build();
        keyboardM2 = InlineKeyboardMarkup.builder().keyboardRow(List.of(back)).keyboardRow(List.of(url)).build();
        if (update.hasMessage()) {
            // handle messages
            var msg = update.getMessage();
            var user = msg.getFrom();
            var id = user.getId();
            var text = msg.getText();
            if (!msg.isCommand()) {
                if (screaming) {
                    scream(id, msg);
                } else {
                    copyMessage(id, msg.getMessageId());
                }
            } else {
                if (text.equals("/whisper")){
                    screaming = false;
                } else if ( text.equals("/scream")){
                    screaming = true;
                } else if (text.equals("/menu")){
                    sendMenu(id, "<b>Menu 1</b>", keyboardM1);
                }
            }
        } else if (update.hasCallbackQuery()){
            // handle button tap
            var query = update.getCallbackQuery();
            var msg = query.getMessage();
            buttonTap(msg.getChatId(), query.getId(), query.getData(), msg.getMessageId());
        }
    }
}