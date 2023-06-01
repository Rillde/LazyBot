package ru.lazybot;

import com.pengrad.telegrambot.model.Update;

import java.util.HashMap;
import java.util.Map;

public class LazyIncMessage extends BaseIncMessage {
    private String type;
    private Object chatID;
    private Map<String,String> data;

    public LazyIncMessage(Update update) {
        super(update);
        data = new HashMap();
        if (update.callbackQuery() != null) {
            type = "button";
            data.put("callback", update.callbackQuery().data());
            chatID = update.callbackQuery().message().chat().id();
        } else if(update.message() != null) {
            if (update.message().text() != null) {
                type = "text";
                data.put("text", update.message().text());
            } else if (update.message().photo() != null) {
                type = "photo";
                data.put("photo", update.message().photo()[0].fileId());
                data.put("caption", update.message().caption());
            } else if (update.message().document() != null) {
                type = "document";
                data.put("document", update.message().document().fileId());
                data.put("mimeType", update.message().document().mimeType());
            }
            chatID = update.message().chat().id();
        } else {
            type = "unknown";
        }
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Object getChatID() {
        return chatID;
    }

    @Override
    public Map<String, String> getData() {
        return data;
    }

    @Override
    public String getData(String dataType) {
        return data.get(dataType);
    }
}

