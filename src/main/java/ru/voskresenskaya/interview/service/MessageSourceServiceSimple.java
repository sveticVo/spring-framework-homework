package ru.voskresenskaya.interview.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import java.util.Locale;
import static ru.voskresenskaya.interview.Constants.*;

@Service
public class MessageSourceServiceSimple implements MessageSourceService {
    private final MessageSource messageSource;
    private final LocaleService localeService;

    public MessageSourceServiceSimple(MessageSource messageSource, LocaleService localeService) {
        this.messageSource = messageSource;
        this.localeService = localeService;
    }

    public Locale getLocale(){
        return localeService.getLocale();
    }

    public String getMessage(String var1) {
        return getMessage(var1, EMPTY_ARGS) ;
    }

    public String getMessage(String var1, Object[] var2) {
        if (StringUtils.isBlank(var1) || var2 == null) {
            return null;
        }
        return messageSource.getMessage(var1, var2, localeService.getLocale()) ;
    }
}
