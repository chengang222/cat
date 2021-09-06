package com.dianping.cat;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.junit.Test;

import java.net.URLDecoder;

public class TestMail {

	public static void main(String[] args) throws EmailException {
		HtmlEmail email = new HtmlEmail();
		try {
			/**
			 * 使用发电子邮件的邮件服务器
			 * qq邮件服务器： smtp.qq.com
			 * 163个人版： smtp.163.com
			 * 163企业用户付费版： smtp.qiye.163.com
			 */
			email.setHostName("smtp.163.com");
			email.setCharset("UTF-8");// 设置字符编码
			//邮件服务器验证：用户名/授权码
			email.setAuthentication("printersend@163.com", "BEMBROJZTXOWXONL");
			// 设置发件人邮箱(与用户名保持一致) 并且 设置发件人昵称
			email.setFrom("printersend@163.com","昵称");

			// 发电子邮件的邮件服务器端口号,默认是25
			email.setSmtpPort(25);
			// 邮件主题
			email.setSubject("测试主题");
			// 邮件内容：由于使用了HtmlEmail，可以在邮件内容中使用HTML标签
			email.setMsg("<h1 style='color:red'>邮件测试，</h1>" + " 请忽略！");
			// 收件人地址
			String emails="cheng@imways.cn,312883198@qq.com";
			for ( String em: emails.split(",")) {
				email.addTo(em);
			}

			//email.addCc("");// 邮件抄送
			// 邮件发送
			email.send();
			System.out.println("邮件发送成功！");
		}catch (EmailException e){
			e.printStackTrace();
			System.err.println("邮件发送失败！");
		}
	}
	@Test
	public void urldec() {
		String urlPars="type=1500&key=title,body&re=test@imways.com&to=Tcheng1@imways.cn,Tcheng2@imways.cn&value=%5BCAT+Transaction%E5%91%8A%E8%AD%A6%5D+%5B%E9%A1%B9%E7%9B%AE%3A+cat%5D+%5B%E7%9B%91%E6%8E%A7%E9%A1%B9%3A+URL-All-count%5D,%5BCAT+Transaction%E5%91%8A%E8%AD%A6%3A+cat+URL+All%5D+%3A+%5B%E5%AE%9E%E9%99%85%E5%80%BC%3A6+%5D+%5B%E6%9C%80%E5%A4%A7%E9%98%88%E5%80%BC%3A+1+%5D%5B%E5%91%8A%E8%AD%A6%E6%97%B6%E9%97%B4%3A2021-09-03+18%3A15%3A00%5D%3Cbr%2F%3E%5B%E6%97%B6%E9%97%B4%3A+2021-09-03+18%3A15%5D+%0D%0A%3Ca+href%3D%27http%3A%2F%2Fcat-web-server%2Fcat%2Fr%2Ft%3Fdomain%3Dcat%26type%3DURL%26name%3DAll%26date%3D2021090318%27%3E%E7%82%B9%E5%87%BB%E6%AD%A4%E5%A4%84%E6%9F%A5%E7%9C%8B%E8%AF%A6%E6%83%85%3C%2Fa%3E%3Cbr%2F%3E%3Cbr%2F%3E%5B%E5%91%8A%E8%AD%A6%E9%97%B4%E9%9A%94%E6%97%B6%E9%97%B4%5D5%E5%88%86%E9%92%9F";
		urlPars= URLDecoder.decode(urlPars);
		System.out.println(urlPars);
	}

	@Test
	public void testr(){
		String alterType="ransaction告警";
		if(!alterType.equals("异常告警")&&!alterType.equals("Transaction告警")){
			System.out.println("123");
		}else {
			System.out.println("456");
		}
	}

}