package jp.co.fukuya_k.system.form.components;

import lombok.Data;

@Data
public class SelectOption {

	private String label;
	private String value;
	private boolean selected;
	private boolean disabled;
}