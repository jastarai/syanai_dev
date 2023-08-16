package jp.jast.spframework.form;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class FileForm {

	private MultipartFile file;
	private String fileSeq;
	private String fileName;
	private boolean fileError;
	
}
