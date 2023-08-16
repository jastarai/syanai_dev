package jp.co.fukuya_k.system.interfaces;

import java.util.Date;

import jp.jast.spframework.form.FileForm;

public interface FileUsableInterface {
	/** HTML_ID_FILE_CTRL_PARTS */
	public static final String HTML_ID_FILE_CTRL_PARTS = "id.fileCtrlParts";
	/** HTML_ID_FILE_SELECT */
	public static final String HTML_ID_FILE_SELECT = "id.fileSelect";
	/** HTML_ID_FILE_SELECT_BTN */
	public static final String HTML_ID_FILE_SELECT_BTN = "id.fileSelectBtn";
	/** HTML_ID_FILE_CLEAR_BTN */
	public static final String HTML_ID_FILE_CLEAR_BTN = "id.fileClearBtn";
	/** HTML_ID_FILE_LINK */
	public static final String HTML_ID_FILE_LINK = "id.fileLink";
	/** HTML_ID_FILE_DUMMY */
	public static final String HTML_ID_FILE_DUMMY = "id.fileDummy";
	/** HTML_ID_FILE */
	public static final String HTML_ID_FILE = "id.file";

	public FileForm getFileForm();
	public void setFileForm(FileForm fileForm);
	public String getFileSeq();
	public byte[] getFileData() throws Exception;
	public String getFileName();
	public Integer getFileType();
	public void setFileType(Integer fileType);
	public Date getUpdDate();
	public void setUpdDate(Date date);
}
