package ru.voskresenskaya.interview.service;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service("scannerKeeper")
public class ScannerKeeperImpl implements ScannerKeeper {
    private final Scanner in;

    public ScannerKeeperImpl() {
        this.in = new Scanner(System.in);
    }

    @Override
    public Scanner getScanner() {
        return in;
    }

    @Override
    public void close() {
        if (in != null) {
            in.close();
        }
    }
}
