/*
 * Copyright (c) 2011-2018, Meituan Dianping. All Rights Reserved.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dianping.cat.alarm.spi.sender;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.dianping.cat.Cat;
import com.dianping.cat.alarm.sender.entity.Sender;
import com.dianping.cat.alarm.spi.AlertChannel;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;


public class MailSender extends AbstractSender {

	public static final String ID = AlertChannel.MAIL.getName();

	@Override
	public String getId() {
		return ID;
	}

	@Override
	public boolean send(SendMessageEntity message) {
		Sender sender = querySender();
		boolean batchSend = sender.isBatchSend();
		boolean result = false;

		if (batchSend) {
			String emails = message.getReceiverString();

			result = sendEmail(message, emails, sender);
		} else {
			List<String> emails = message.getReceivers();

			for (String email : emails) {
				boolean success = sendEmail(message, email, sender);
				result = result || success;
			}
		}
		return result;
	}
	private boolean sendEmail(SendMessageEntity message, String receiver, Sender sender) {
		String title = message.getTitle().replaceAll(",", " ");
		String content = message.getContent().replaceAll(",", " ");
		String urlPrefix = sender.getUrl();
		String urlPars = m_senderConfigManager.queryParString(sender);
		String time = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());

//		try {
//			urlPars = urlPars.replace("${receiver}", receiver).replace("${title}", URLEncoder.encode(title, "utf-8"))
//					.replace("${content}", URLEncoder.encode(content, "utf-8"))
//					.replace("${time}", URLEncoder.encode(time, "utf-8"));
//
//		} catch (Exception e) {
//			Cat.logError(e);
//		}
		HtmlEmail email = new HtmlEmail();
		try {

			email.setHostName("smtp.163.com");
			email.setCharset("UTF-8");// 设置字符编码
			//邮件服务器验证：用户名/授权码
			email.setAuthentication("printersend@163.com", "BEMBROJZTXOWXONL");
			// 设置发件人邮箱(与用户名保持一致) 并且 设置发件人昵称
			email.setFrom("printersend@163.com","告警邮件");

			// 发电子邮件的邮件服务器端口号,默认是25
			email.setSmtpPort(25);
			// 邮件主题
			email.setSubject(title);
			// 邮件内容：由于使用了HtmlEmail，可以在邮件内容中使用HTML标签

			email.setMsg(content);
			// 收件人地址
			for ( String em: receiver.split(",")) {
				email.addTo(em);
			}
			//email.addCc("");// 邮件抄送
			// 邮件发送
			email.send();

		}catch (EmailException e){
			Cat.logError(e);
		}
		return true;
	}

/*	private boolean sendEmail(SendMessageEntity message, String receiver, Sender sender) {
		String title = message.getTitle().replaceAll(",", " ");
		String content = message.getContent().replaceAll(",", " ");
		String urlPrefix = sender.getUrl();
		String urlPars = m_senderConfigManager.queryParString(sender);
		String time = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());

		try {
			urlPars = urlPars.replace("${receiver}", receiver).replace("${title}", URLEncoder.encode(title, "utf-8"))
									.replace("${content}", URLEncoder.encode(content, "utf-8"))
									.replace("${time}", URLEncoder.encode(time, "utf-8"));

		} catch (Exception e) {
			Cat.logError(e);
		}

		return httpSend(sender.getSuccessCode(), sender.getType(), urlPrefix, urlPars);
	}*/
}
