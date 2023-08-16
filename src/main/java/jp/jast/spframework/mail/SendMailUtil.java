package jp.jast.spframework.mail;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class SendMailUtil  {

	  private final JavaMailSender mailSender;

	  @Autowired
	  SendMailUtil(JavaMailSender javaMailSender) {
	    this.mailSender = javaMailSender;
	  }
	
	public void sendMail(MailContent mailContent) throws MessagingException, IOException {
		
		MimeMessage message = mailSender.createMimeMessage(); 

		boolean isMultiPart = false;
		
		int attachFileCount = mailContent.getAttachFileStreamList().size();
		
		// 添付ファイルの有無を判定
		// ※添付ファイルがないメールに対し、multipartとして送信すると、本文が添付ファイルとして
		//   自動的に添付されるため
		if (attachFileCount > 0) {
			isMultiPart = true;
		}
		
		MimeMessageHelper helper = new MimeMessageHelper(message, isMultiPart);
		
		helper.setTo(mailContent.getToAddress());
		helper.setFrom(mailContent.getFromAddress());
		helper.setCc(mailContent.getCcAddress());
		helper.setBcc(mailContent.getBccAddress());
		helper.setReplyTo(mailContent.getReplyToAddress());
		helper.setSubject(mailContent.getSubject());
		helper.setText(mailContent.getBody());
		
		if (attachFileCount > 0) {
			for (MailAttachFileStream fileStream : mailContent.getAttachFileStreamList()) {
				helper.addAttachment(fileStream.getFileName(), fileStream.getByteArrayResource());
			}
		}
	
		mailSender.send(message);
		
		helper = null;
		
	}
	
}
