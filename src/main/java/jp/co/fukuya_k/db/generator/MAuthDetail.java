package jp.co.fukuya_k.db.generator;

import java.util.Date;

import jp.co.fukuya_k.system.interfaces.FukuyaEntityInterface;

public class MAuthDetail extends MAuthDetailKey implements FukuyaEntityInterface {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column KENSYU.M_AUTH_DETAIL.APPROVAL
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	private String approval;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column KENSYU.M_AUTH_DETAIL.AUTH_NAME
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	private String authName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column KENSYU.M_AUTH_DETAIL.UPD_FUNC_ID
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	private String updFuncId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column KENSYU.M_AUTH_DETAIL.INS_USER_ID
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	private String insUserId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column KENSYU.M_AUTH_DETAIL.INS_DATE
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	private Date insDate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column KENSYU.M_AUTH_DETAIL.UPD_USER_ID
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	private String updUserId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column KENSYU.M_AUTH_DETAIL.UPD_DATE
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	private Date updDate;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column KENSYU.M_AUTH_DETAIL.APPROVAL
	 * @return  the value of KENSYU.M_AUTH_DETAIL.APPROVAL
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public String getApproval() {
		return approval;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column KENSYU.M_AUTH_DETAIL.APPROVAL
	 * @param approval  the value for KENSYU.M_AUTH_DETAIL.APPROVAL
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public void setApproval(String approval) {
		this.approval = approval;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column KENSYU.M_AUTH_DETAIL.AUTH_NAME
	 * @return  the value of KENSYU.M_AUTH_DETAIL.AUTH_NAME
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public String getAuthName() {
		return authName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column KENSYU.M_AUTH_DETAIL.AUTH_NAME
	 * @param authName  the value for KENSYU.M_AUTH_DETAIL.AUTH_NAME
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public void setAuthName(String authName) {
		this.authName = authName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column KENSYU.M_AUTH_DETAIL.UPD_FUNC_ID
	 * @return  the value of KENSYU.M_AUTH_DETAIL.UPD_FUNC_ID
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public String getUpdFuncId() {
		return updFuncId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column KENSYU.M_AUTH_DETAIL.UPD_FUNC_ID
	 * @param updFuncId  the value for KENSYU.M_AUTH_DETAIL.UPD_FUNC_ID
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public void setUpdFuncId(String updFuncId) {
		this.updFuncId = updFuncId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column KENSYU.M_AUTH_DETAIL.INS_USER_ID
	 * @return  the value of KENSYU.M_AUTH_DETAIL.INS_USER_ID
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public String getInsUserId() {
		return insUserId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column KENSYU.M_AUTH_DETAIL.INS_USER_ID
	 * @param insUserId  the value for KENSYU.M_AUTH_DETAIL.INS_USER_ID
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public void setInsUserId(String insUserId) {
		this.insUserId = insUserId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column KENSYU.M_AUTH_DETAIL.INS_DATE
	 * @return  the value of KENSYU.M_AUTH_DETAIL.INS_DATE
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public Date getInsDate() {
		return insDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column KENSYU.M_AUTH_DETAIL.INS_DATE
	 * @param insDate  the value for KENSYU.M_AUTH_DETAIL.INS_DATE
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column KENSYU.M_AUTH_DETAIL.UPD_USER_ID
	 * @return  the value of KENSYU.M_AUTH_DETAIL.UPD_USER_ID
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public String getUpdUserId() {
		return updUserId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column KENSYU.M_AUTH_DETAIL.UPD_USER_ID
	 * @param updUserId  the value for KENSYU.M_AUTH_DETAIL.UPD_USER_ID
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column KENSYU.M_AUTH_DETAIL.UPD_DATE
	 * @return  the value of KENSYU.M_AUTH_DETAIL.UPD_DATE
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public Date getUpdDate() {
		return updDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column KENSYU.M_AUTH_DETAIL.UPD_DATE
	 * @param updDate  the value for KENSYU.M_AUTH_DETAIL.UPD_DATE
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}
}