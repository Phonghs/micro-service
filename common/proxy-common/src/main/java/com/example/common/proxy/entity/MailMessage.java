package com.example.common.proxy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailMessage {
	private String from;
	private String personal;
	private String subject;
	private String text;
	@Builder.Default
	private boolean isHtml = true;
	private Map<String, byte[]> attachments;
	private String[] to;
	private String[] cc;
	private String[] bcc;

	public static class MailMessageBuilder {

		public MailMessageBuilder to(String... to) {
			this.to = to;
			return this;
		}

		public MailMessageBuilder cc(String... cc) {
			this.cc = cc;
			return this;
		}

		public MailMessageBuilder bcc(String... bcc) {
			this.bcc = bcc;
			return this;
		}

	}
}
