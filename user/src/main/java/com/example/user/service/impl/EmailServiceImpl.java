package com.example.user.service.impl;

import com.example.common.proxy.entity.MailMessage;
import com.example.user.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender mailSender;

	private final String DEFAULT_FROM = "phamphong0521@gmail.com";
	private final String DEFAULT_PERSONAL = "LMS System";

	@Override
	public void send(MailMessage mailMessage) throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true, CharEncoding.UTF_8);
		helper.setFrom(
				StringUtils.defaultString(mailMessage.getFrom(), DEFAULT_FROM),
				StringUtils.defaultString(mailMessage.getPersonal(), DEFAULT_PERSONAL)
		);
		helper.setSubject(mailMessage.getSubject());
		helper.setText(mailMessage.getText(), mailMessage.isHtml());
		if (Objects.nonNull(mailMessage.getAttachments())){
			for (Map.Entry<String, byte[]> attachment : mailMessage.getAttachments().entrySet()) {
				helper.addAttachment(attachment.getKey(), new ByteArrayResource(attachment.getValue()));
			}
		}
		helper.setTo(mailMessage.getTo());
		helper.setCc(ArrayUtils.nullToEmpty(mailMessage.getCc()));
		helper.setBcc(ArrayUtils.nullToEmpty(mailMessage.getBcc()));

		mailSender.send(message);
	}
}
