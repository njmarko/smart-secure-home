package com.example.demo.service.impl;

import com.example.demo.service.CertificatesService;
import com.example.demo.service.KeystoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class CertificatesServiceImpl implements CertificatesService {


    private final KeystoreService reader;

    @Autowired
    public CertificatesServiceImpl(KeystoreService reader) {
        this.reader = reader;
    }

    public void showKeyStoreContent() {
        Scanner keyboard = new Scanner(System.in);
        String keystoreName = "";
        String keystorePassword = "";
        System.out.println("===== Unesite ime za keystore =====");
        keystoreName = keyboard.nextLine();
        System.out.println("===== Unesite password za keystore =====");
        keystorePassword = keyboard.nextLine();

        reader.listContent("src/main/resources/static/" + keystoreName, keystorePassword.toCharArray());
        System.out.println("Kraj");
    }
}
