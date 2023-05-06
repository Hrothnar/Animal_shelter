package re.st.animalshelter.model.response;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import re.st.animalshelter.dto.ActionDTO;
import re.st.animalshelter.service.StorageService;
import re.st.animalshelter.utility.ButtonUtil;

@Component
public class EditTextResponse {
    private final StorageService storageService;
    private final ButtonUtil buttonUtil;
    private final TelegramBot telegramBot;

    @Autowired
    public EditTextResponse(StorageService storageService, ButtonUtil buttonUtil, TelegramBot telegramBot) {
        this.storageService = storageService;
        this.buttonUtil = buttonUtil;
        this.telegramBot = telegramBot;
    }

    public void sendEditedTextResponse(ActionDTO actionDTO) {
        long chatId = actionDTO.getChatId();
        int messageId = actionDTO.getMessageId();
        String text = storageService.getText(actionDTO);
        InlineKeyboardMarkup keyboard = buttonUtil.getKeyboard(actionDTO);
        EditMessageText response = new EditMessageText(chatId, messageId, text).replyMarkup(keyboard);
        BaseResponse execute = telegramBot.execute(response);
        if (!execute.isOk()) {
            System.out.println(execute.description());
        }
    }
}
