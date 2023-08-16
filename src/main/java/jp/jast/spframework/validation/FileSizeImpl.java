package jp.jast.spframework.validation;

import java.lang.annotation.Annotation;

import javax.validation.Payload;

@SuppressWarnings(value = { "all" })
public class FileSizeImpl implements FileSize {

	String overrideSize;
	
	public FileSizeImpl(String size) {
		overrideSize = size;
	}
	
	@Override
	public Class<? extends Annotation> annotationType() {
		return null;
	}

	@Override
	public String size() {
		return overrideSize;
	}

	@Override
	public String message() {
		return null;
	}

	@Override
	public Class<?>[] groups() {
		return null;
	}

	@Override
	public Class<? extends Payload>[] payload() {
		return null;
	}

}
