package jp.co.fukuya_k.system.form.components;

import java.util.List;

import lombok.Data;

/**
 * 
 * @author
 *
 */
@Data
public class MenuItem {

	private String text;
	private String href;
	private String[] tags;
	private int levels;
	private List<MenuItem> nodes;
}
