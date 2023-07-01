package com.vti.trangwebxemphimv2.service;

public interface IMailSenderService {

    void sendSimpleMessage(String to, String subject, String text);

    void sendMessageWithHTML(String to, String subject, String text);

}
