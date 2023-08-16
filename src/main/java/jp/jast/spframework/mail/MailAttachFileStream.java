package jp.jast.spframework.mail;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class MailAttachFileStream {

	private String fileName;
	private InputStream attachFileStream;
	
	MailAttachFileStream(String nm, InputStream strm) {
		fileName = nm;
		attachFileStream = strm;
	}
	
	MailAttachFileStream(MultipartFile multipartFile) throws IOException {
		fileName = Paths.get(multipartFile.getOriginalFilename()).getFileName().toString();
		attachFileStream = multipartFile.getInputStream();
	}
	
	public ByteArrayResource getByteArrayResource() throws IOException {
		return new ByteArrayResource(StreamUtils.copyToByteArray(attachFileStream));
	}
	
}
