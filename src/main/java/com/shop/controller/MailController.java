package com.shop.controller;

import com.shop.service.MailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

public class MailController {

}
    private final MailService mailService;

    @GetMapping("/mail")
    public String dispMail() {
        return "mail";
    }

    @PostMapping("/mail")
    public void execMail(MailDto mailDto) {
        mailService.mailSend(mailDto);
}
