package com.example.user.service;

import com.example.common.proxy.entity.MailMessage;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface EmailService {
	void send(MailMessage mailMessage) throws MessagingException, UnsupportedEncodingException;
}
