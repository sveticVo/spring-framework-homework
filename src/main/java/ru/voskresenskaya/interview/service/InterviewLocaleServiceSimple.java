package ru.voskresenskaya.interview.service;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.voskresenskaya.interview.InterviewException;
import ru.voskresenskaya.interview.util.Utils;

import java.util.*;

import static ru.voskresenskaya.interview.Constants.EMPTY_ARGS;

@Service("interviewLocaleService")
public class InterviewLocaleServiceSimple implements InterviewLocaleService {

    private Locale defaultLocale;
    private List<LocaleItem> predefinedLocales;
    private Locale currentLocale;
    private MessageSource messageSource;

    public InterviewLocaleServiceSimple(@Value("#{systemProperties['user.country']}") String userCountry,
                                        @Value("#{systemProperties['user.language']}") String userLanguage,
                                        MessageSource messageSource) {
        this.messageSource = messageSource;
        this.defaultLocale = new Locale(userLanguage, userCountry);
        initLocale();
    }

    public Locale getLocale() {
        if (currentLocale == null) {
            currentLocale = defaultLocale;
        }
        return currentLocale;
    }

    private void initLocale() {
        Scanner in = new Scanner(System.in);
        List<LocaleItem> locales = getPredefinedLocales();
        if (CollectionUtils.isEmpty(locales)) {
            return;
        }
        System.out.println(Utils.replaceSeparator(messageSource.getMessage("choose.locale", EMPTY_ARGS, defaultLocale)));
        Map<String, Locale> localeMap = new HashMap<>();

        for (int i = 1; i <= locales.size(); i++) {
            LocaleItem item = locales.get(i-1);
            System.out.println(item.getName() + ": " + i);
            localeMap.put(String.valueOf(i), item.getLocale());
        }

        try {
            String tryCountDefaultMessage = messageSource.getMessage("try.count.error", EMPTY_ARGS, defaultLocale);
            String tryAgainDefaultMessage = messageSource.getMessage("try.again", EMPTY_ARGS, defaultLocale);
            String answer = Utils.compareFewUserInputDefault(in, tryCountDefaultMessage, tryAgainDefaultMessage,
                    localeMap.keySet().toArray(new String[] {}));
            currentLocale = localeMap.get(answer);
        } catch (InterviewException e) {
            System.out.println(Utils.replaceSeparator(messageSource.getMessage("default.locale",
                    new String[] {defaultLocale.getDisplayName()}, defaultLocale)));
        }
    }

    protected List<LocaleItem> getPredefinedLocales() {
        if (CollectionUtils.isEmpty(predefinedLocales)) {
            predefinedLocales = new ArrayList<>();
            predefinedLocales.add(new LocaleItem(
                    messageSource.getMessage("locale.russian", EMPTY_ARGS, defaultLocale),
                    new Locale("ru", "RU")));
            predefinedLocales.add(new LocaleItem(
                    messageSource.getMessage("locale.english", EMPTY_ARGS, defaultLocale),
                    new Locale("en", "US")));
        }
        return predefinedLocales;
    }

    public boolean addLocale(String name, Locale locale) {
        if (locale == null || StringUtils.isBlank(name)) {
            return false;
        }
        if (predefinedLocales == null) {
            predefinedLocales = new ArrayList<>();
        }
        predefinedLocales.add(new LocaleItem(name, locale));
        return true;
    }

    private static class LocaleItem {
        private final String name;
        private final Locale locale;


        public LocaleItem(String name, Locale locale) {
            this.name = name;
            this.locale = locale;
        }

        public String getName() {
            return name;
        }

        public Locale getLocale() {
            return locale;
        }
    }
}
