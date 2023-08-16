/**
 * 
 */
package jp.co.fukuya_k.system.form.bean;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import jp.co.fukuya_k.system.interfaces.FileUsableInterface;
import jp.co.fukuya_k.system.utils.CheckUtil;
import jp.jast.spframework.form.FileForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class FileDataBean extends FileForm implements FileUsableInterface {
	@NotBlank
	private String id;
	@Length(max=10)
	private String biko;
	@NotBlank
	private String insUserId;
	/* 登録日 */
	@NotBlank
	private Date insDate;
	private String updUserId;
	/* 更新日 */
	private Date updDate;
	/**
	 * ファイル種別
	 */
	private int fileType;
	/* (非 Javadoc)
	 * @see jp.co.fukuya_k.web.common.interfaces.FileUsableInterface#getFileForm()
	 */
	@Override
	public FileForm getFileForm() {
		return (FileForm)this;
	}
	/* (非 Javadoc)
	 * @see jp.co.fukuya_k.web.common.interfaces.FileUsableInterface#setFileForm()
	 */
	@Override
	public void setFileForm(FileForm fileForm) {
		super.setFile(fileForm.getFile());
		super.setFileSeq(fileForm.getFileSeq());
		super.setFileName(fileForm.getFileName());
		super.setFileError(fileForm.isFileError());
	}
	/* (非 Javadoc)
	 * @see jp.co.fukuya_k.web.common.interfaces.FileUsableInterface#getFileSeq()
	 */
	@Override
	public String getFileSeq() {
		if(CheckUtil.isNull(super.getFileSeq())){ return ""; }
		return super.getFileSeq();
	}
	/* (非 Javadoc)
	 * @see jp.co.fukuya_k.web.common.interfaces.IFileUsable#getFileData()
	 */
	@Override
	public byte[] getFileData() throws IOException {
		if(CheckUtil.isNull(super.getFile())){
			return new byte[0];
		}
		return super.getFile().getBytes();
	}

	/* (非 Javadoc)
	 * @see jp.co.fukuya_k.web.common.interfaces.IFileUsable#getFileName()
	 */
	@Override
	public String getFileName() {
		if(CheckUtil.isNull(super.getFile())){
			return super.getFileName();
		}
		return Paths.get(super.getFile().getOriginalFilename()).getFileName().toString();
	}

	/* (非 Javadoc)
	 * @see jp.co.fukuya_k.web.common.interfaces.IFileUsable#getFileType()
	 */
	@Override
	public Integer getFileType() {
		return fileType;
	}

	/* (非 Javadoc)
	 * @see jp.co.fukuya_k.web.common.interfaces.IFileUsable#setFileType(int)
	 */
	@Override
	public void setFileType(Integer type) {
		this.fileType = type;
	}
	/**
	 * 
	 * 
	 * @return
	 */
	public String getOriginalFilename() {
		if(CheckUtil.isNull(super.getFile())){
			return "";
		}
		return super.getFile().getOriginalFilename();
	}
}
