package ru.voskresenskaya.interview.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import java.util.Locale;
import static ru.voskresenskaya.interview.Constants.*;

@Service("messageSourceService")
public class MessageSourceServiceSimple implements MessageSourceService {
    private MessageSource messageSource;
    private InterviewLocaleService localeService;

    @Autowired
    public MessageSourceServiceSimple(MessageSource messageSource, InterviewLocaleService localeService) {
        this.messageSource = messageSource;
        this.localeService = localeService;
    }

    public Locale getLocale(){
        return localeService.getLocale();
    }

    public String getMessage(String var1) {
        return getMessage(var1, EMPTY_ARGS) ;
    }

    public String getMessage(String var1, @Nullable Object[] var2) {
        if (StringUtils.isBlank(var1) || var2 == null) {
            return null;
        }
        return messageSource.getMessage(var1, var2, localeService.getLocale()) ;
    }
}
