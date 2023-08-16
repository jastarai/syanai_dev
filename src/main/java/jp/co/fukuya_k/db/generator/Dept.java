package jp.co.fukuya_k.db.generator;

import java.util.Date;

import jp.co.fukuya_k.system.interfaces.FukuyaEntityInterface;

public class Dept implements FukuyaEntityInterface {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column KENSYU.DEPT.DEPTNO
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	private Short deptno;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column KENSYU.DEPT.DNAME
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	private String dname;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column KENSYU.DEPT.LOC
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	private String loc;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column KENSYU.DEPT.DEPTNO
	 * @return  the value of KENSYU.DEPT.DEPTNO
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public Short getDeptno() {
		return deptno;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column KENSYU.DEPT.DEPTNO
	 * @param deptno  the value for KENSYU.DEPT.DEPTNO
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public void setDeptno(Short deptno) {
		this.deptno = deptno;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column KENSYU.DEPT.DNAME
	 * @return  the value of KENSYU.DEPT.DNAME
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public String getDname() {
		return dname;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column KENSYU.DEPT.DNAME
	 * @param dname  the value for KENSYU.DEPT.DNAME
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public void setDname(String dname) {
		this.dname = dname;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column KENSYU.DEPT.LOC
	 * @return  the value of KENSYU.DEPT.LOC
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public String getLoc() {
		return loc;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column KENSYU.DEPT.LOC
	 * @param loc  the value for KENSYU.DEPT.LOC
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public void setLoc(String loc) {
		this.loc = loc;
	}

		// ADD START FW都合 研修には影響なし
		private String insUserId;
		private Date insDate;
		private String updUserId;
		private Date updDate;
		private String updFuncId;
	    
		public String getInsUserId() {
			return insUserId;
		}

		public void setInsUserId(String insUserId) {
			this.insUserId = insUserId;
		}

		public Date getInsDate() {
			return insDate;
		}

		public void setInsDate(Date insDate) {
			this.insDate = insDate;
		}

		public String getUpdUserId() {
			return updUserId;
		}

		public void setUpdUserId(String updUserId) {
			this.updUserId = updUserId;
		}

		public Date getUpdDate() {
			return updDate;
		}

		public void setUpdDate(Date updDate) {
			this.updDate = updDate;
		}

		public String getUpdFuncId() {
			return updFuncId;
		}

		public void setUpdFuncId(String updFuncId) {
			this.updFuncId = updFuncId;
		}
		// ADD END FW都合 研修には影響なし
}