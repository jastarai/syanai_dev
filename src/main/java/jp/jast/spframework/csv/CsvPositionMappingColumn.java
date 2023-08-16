package jp.jast.spframework.csv;

import java.text.Format;

import lombok.Data;

@Data
public class CsvPositionMappingColumn {

	public CsvPositionMappingColumn(int position, String field){
		this.position = position;
		this.field = field;
	};

	public CsvPositionMappingColumn(int position, String field, Format format){
		this.position = position;
		this.field = field;
		this.format = format;
	};
	
	private int position;
	private String field;
	private Format format;
	
}
