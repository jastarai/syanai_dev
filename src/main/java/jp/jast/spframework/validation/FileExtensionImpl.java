package jp.jast.spframework.validation;

import java.lang.annotation.Annotation;

import javax.validation.Payload;

@SuppressWarnings(value = { "all" })
public class FileExtensionImpl implements FileExtension {

	String overrideExtension;
	boolean overrideConsistent;
	
	public FileExtensionImpl(String extension, boolean consistent) {
		overrideExtension = extension;
		overrideConsistent = consistent;
	}
	
	@Override
	public Class<? extends Annotation> annotationType() {
		return null;
	}

	@Override
	public String extension() {
		return overrideExtension;
	}

	@Override
	public boolean consistent() {
		return overrideConsistent;
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
