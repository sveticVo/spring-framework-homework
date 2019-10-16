package ru.voskresenskaya.interview.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.voskresenskaya.interview.InterviewException;
import ru.voskresenskaya.interview.util.Utils;
import java.util.function.Predicate;

@Service("scannerService")
public class ScannerServiceImpl implements ScannerService {
    private final ScannerKeeper scannerKeeper;
    private final MessageSourceService messageSourceService;

    public ScannerServiceImpl(MessageSourceService messageSourceService, ScannerKeeper scannerKeeper) {
        this.messageSourceService = messageSourceService;
        this.scannerKeeper = scannerKeeper;
    }

    public String getNotNullInput() throws InterviewException {
        return compareUserInput(StringUtils::isNotBlank);
    }

    public String compareUserInput(final String value) throws InterviewException {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return compareUserInput(value::equalsIgnoreCase);
    }

    public String compareUserInput(Predicate<String> predicate) throws InterviewException {
        return Utils.compareUserInput(scannerKeeper.getScanner(), messageSourceService.getMessage("try.count.error"),
                messageSourceService.getMessage("try.again"), predicate);
    }
}
