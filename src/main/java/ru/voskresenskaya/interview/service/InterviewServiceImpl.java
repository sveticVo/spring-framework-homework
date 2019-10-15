package ru.voskresenskaya.interview.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.voskresenskaya.interview.InterviewException;
import ru.voskresenskaya.interview.dao.InterviewDao;
import ru.voskresenskaya.interview.util.Utils;

import java.util.List;
import java.util.Scanner;

import static ru.voskresenskaya.interview.Constants.GOODBYE_MSG;

@Service("interviewService")
public class InterviewServiceImpl implements InterviewService {
    private final InterviewDao dao;
    private final MessageSourceService messageSourceService;
    private final ScannerService scannerService;

    private final Logger logger = Logger.getLogger(InterviewServiceImpl.class);

    private String READY_WORD = "+";

    public InterviewServiceImpl(InterviewDao dao, MessageSourceService messageSourceService, ScannerService scannerService) {
        this.dao = dao;
        this.messageSourceService = messageSourceService;
        this.scannerService = scannerService;
    }

    public void spendTest() {
        try {
            System.out.println(Utils.addNewLine(messageSourceService.getMessage("header")));
            Thread.sleep(2000);
            System.out.println(Utils.addNewLine(messageSourceService.getMessage("greeting")));
            String name = scannerService.getNotNullInput();
            System.out.println(Utils.replaceSeparator(messageSourceService.getMessage("hello", new String[] {name})));

            Thread.sleep(1200);
            System.out.println(Utils.addNewLine(messageSourceService.getMessage("ready")));

            scannerService.compareUserInput(READY_WORD);
            System.out.println(Utils.replaceSeparator(messageSourceService.getMessage("start")));

            List<String> choices = dao.interview();
            if (CollectionUtils.isEmpty(choices)) {
                throw new InterviewException(Utils.replaceSeparator(messageSourceService.getMessage("answers.empty")));
            }

            System.out.println(dao.calculateAnswer(choices));

        } catch(Exception ex) {
            System.out.println(Utils.addNewLine(messageSourceService.getMessage("technical.error", new String[] {ex.getMessage()})));
            System.out.println(GOODBYE_MSG);
            logger.error(ex.getMessage(), ex);
        }
    }
}
