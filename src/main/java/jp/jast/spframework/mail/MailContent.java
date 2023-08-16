package jp.jast.spframework.mail;

import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import lombok.Data;

@Data
public class MailContent {

	public MailContent(String to, 
			String from, 
			String cc, 
			String bcc, 
			String replyTo, 
			String subject, 
			String body,
			List<MailAttachFileStream> attachFileStreamList) {
		
		this.to = to;
		this.from = from;
		this.cc = cc;
		this.bcc = bcc;
		this.replyTo = replyTo;
		this.subject = subject;
		this.body = body;
		this.attachFileStreamList = attachFileStreamList;
		
	}
	
	private String to;
	private String from;
	private String cc;
	private String bcc;
	private String replyTo;
	private String subject;
	private String body;
	private List<MailAttachFileStream> attachFileStreamList;
	
	public InternetAddress[] getToAddress() throws AddressException{
		return InternetAddress.parse(to);
	}

	public InternetAddress getFromAddress() throws AddressException{
		return new InternetAddress(from);
	}

	public InternetAddress[] getCcAddress() throws AddressException{
		return InternetAddress.parse(cc);
	}
	
	public InternetAddress[] getBccAddress() throws AddressException{
		return InternetAddress.parse(bcc);
	}
	
	public InternetAddress getReplyToAddress() throws AddressException{
		return new InternetAddress(replyTo);
	}
	
}
