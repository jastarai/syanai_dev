package jp.co.fukuya_k.system.form.bean;

import java.io.IOException;
import java.util.Date;

import jp.co.fukuya_k.system.form.components.CheckBox;
import jp.co.fukuya_k.system.interfaces.FileUsableInterface;
import jp.co.fukuya_k.system.utils.CheckUtil;
import jp.jast.spframework.form.FileForm;
import lombok.Getter;
import lombok.Setter;
public class ListDataBean implements FileUsableInterface {
	/* 画面側のチェックボックス部品 */
	@Setter@Getter private CheckBox checkBox;
	private FileDataBean fileData;
	@Setter@Getter private Integer seq;
	private String insUserId;
	private Date insDate;
	private String updUserId;
	private Date updDate;
	/* (非 Javadoc)
	 * @see jp.co.fukuya_k.web.common.interfaces.FileUsableInterface#getFileForm()
	 */
	@Override
	public FileForm getFileForm() {
		if(CheckUtil.isNull(fileData)){ return null; }
		return fileData.getFileForm();
	}
	/* (非 Javadoc)
	 * @see jp.co.fukuya_k.web.common.interfaces.FileUsableInterface#setFileForm(FileForm)
	 */
	@Override
	public void setFileForm(FileForm fileForm) {
		fileData = new FileDataBean();
		fileData.setFileForm(fileForm);
	}
	/* (非 Javadoc)
	 * @see jp.co.fukuya_k.web.common.interfaces.FileUsableInterface#getFileSeq()
	 */
	@Override
	public String getFileSeq() {
		if(CheckUtil.isNull(fileData)){ return ""; }
		return fileData.getFileSeq();
	}
	/* (非 Javadoc)
	 * @see jp.co.fukuya_k.web.common.interfaces.FileUsableInterface#getFileData()
	 */
	@Override
	public byte[] getFileData() throws IOException {
		if(CheckUtil.isNull(fileData)){ return new byte[0]; }
		return fileData.getFileData();
	}

	/* (非 Javadoc)
	 * @see jp.co.fukuya_k.web.common.interfaces.FileUsableInterface#getFileName()
	 */
	@Override
	public String getFileName() {
		if(CheckUtil.isNull(fileData)){ return ""; }
		return fileData.getFileName();
	}

	/* (非 Javadoc)
	 * @see jp.co.fukuya_k.web.common.interfaces.FileUsableInterface#getFileType()
	 */
	@Override
	public Integer getFileType() {
		if(CheckUtil.isNull(fileData)){ return 0; }
		return fileData.getFileType();
	}

	/* (非 Javadoc)
	 * @see jp.co.fukuya_k.web.common.interfaces.FileUsableInterface#setFileType(Integer)
	 */
	@Override
	public void setFileType(Integer type) {
		if(CheckUtil.isNull(fileData)){ return; }
		fileData.setFileType(type);
	}
	/**
	 * 
	 * @return
	 */
	public String getInsUserId() {
		return insUserId;
	}
	/**
	 * 
	 * @param insUserId
	 */
	public void setInsUserId(String insUserId) {
		if(CheckUtil.isNotNull(fileData)){
			this.fileData.setInsUserId(insUserId);
		}
		this.insUserId = insUserId;
	}
	/**
	 * 
	 * @return
	 */
	public Date getInsDate() {
		return insDate;
	}
	/**
	 * 
	 * @param insDate
	 */
	public void setInsDate(Date insDate) {
		if(CheckUtil.isNotNull(fileData)){
			this.fileData.setInsDate(insDate);
		}
		this.insDate = insDate;
	}
	/**
	 * 
	 * @return
	 */
	public String getUpdUserId() {
		return updUserId;
	}
	/**
	 * 
	 * @param updUserId
	 */
	public void setUpdUserId(String updUserId) {
		if(CheckUtil.isNotNull(fileData)){
			this.fileData.setUpdUserId(updUserId);
		}
		this.updUserId = updUserId;
	}
	/* (非 Javadoc)
	 * @see jp.co.fukuya_k.web.common.interfaces.FileUsableInterface#getUpdDate()
	 */
	@Override
	public Date getUpdDate() {
		return updDate;
	}
	/* (非 Javadoc)
	 * @see jp.co.fukuya_k.web.common.interfaces.FileUsableInterface#setUpdDate(Date)
	 */
	@Override
	public void setUpdDate(Date date) {
		if(CheckUtil.isNotNull(fileData)){
			this.fileData.setUpdDate(date);
		}
		this.updDate = date;
	}
}
