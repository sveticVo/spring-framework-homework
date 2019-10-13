package ru.voskresenskaya.interview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.voskresenskaya.interview.InterviewException;
import ru.voskresenskaya.interview.dao.ScannerDao;
import ru.voskresenskaya.interview.util.Utils;
import java.util.*;

import static ru.voskresenskaya.interview.Constants.*;

@Service("scannerService")
public class ScannerServiceImpl implements ScannerService {
    private ScannerDao dao;
    private MessageSourceService messageSourceService;
    private Utils util;

    private String READY_WORD = "+";

    @Autowired
    public ScannerServiceImpl(ScannerDao dao, MessageSourceService messageSourceService, Utils util) {
        this.dao = dao;
        this.messageSourceService = messageSourceService;
        this.util = util;
    }

    public void spendTest() {
        Scanner in = null;
        try {
            in = new Scanner(System.in);
            System.out.println(Utils.addNewLine(messageSourceService.getMessage("header")));
            Thread.sleep(2000);
            System.out.println(Utils.addNewLine(messageSourceService.getMessage("greeting")));
            String name = util.getNotNullInput(in);
            System.out.println(Utils.replaceSeparator(messageSourceService.getMessage("hello", new String[] {name})));

            Thread.sleep(1200);
            System.out.println(Utils.addNewLine(messageSourceService.getMessage("ready")));

            util.compareUserInput(in, READY_WORD);
            System.out.println(Utils.replaceSeparator(messageSourceService.getMessage("start")));

            List<String> choices = dao.interview(in);
            if (CollectionUtils.isEmpty(choices)) {
                throw new InterviewException(Utils.replaceSeparator(messageSourceService.getMessage("answers.empty")));
            }

            System.out.println(dao.calculateAnswer(choices));
            in.close();

        } catch(Exception ex) {
            try {
                System.out.println(Utils.addNewLine(messageSourceService.getMessage("technical.error", new String[] {ex.getMessage()})));
                System.out.println(GOODBYE_MSG);
                ex.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }
}
