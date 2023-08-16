package jp.jast.spframework.form;

import java.util.List;

import lombok.Data;

@Data
public class AbstractMainForm{

	private List<String> commonErrMsg;

	private List<String> commonInfoMsg;
	
}
