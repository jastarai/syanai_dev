package jp.co.fukuya_k.db.generator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MAuthUserHelper {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table KENSYU.M_AUTH_USER
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table KENSYU.M_AUTH_USER
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table KENSYU.M_AUTH_USER
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table KENSYU.M_AUTH_USER
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public MAuthUserHelper() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table KENSYU.M_AUTH_USER
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table KENSYU.M_AUTH_USER
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table KENSYU.M_AUTH_USER
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table KENSYU.M_AUTH_USER
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table KENSYU.M_AUTH_USER
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table KENSYU.M_AUTH_USER
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table KENSYU.M_AUTH_USER
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table KENSYU.M_AUTH_USER
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table KENSYU.M_AUTH_USER
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table KENSYU.M_AUTH_USER
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table KENSYU.M_AUTH_USER
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	protected abstract static class GeneratedCriteria {
		protected List<Criterion> criteria;

		protected GeneratedCriteria() {
			super();
			criteria = new ArrayList<Criterion>();
		}

		public boolean isValid() {
			return criteria.size() > 0;
		}

		public List<Criterion> getAllCriteria() {
			return criteria;
		}

		public List<Criterion> getCriteria() {
			return criteria;
		}

		protected void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteria.add(new Criterion(condition));
		}

		protected void addCriterion(String condition, Object value, String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value));
		}

		protected void addCriterion(String condition, Object value1, Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value1, value2));
		}

		public Criteria andUserIdIsNull() {
			addCriterion("USER_ID is null");
			return (Criteria) this;
		}

		public Criteria andUserIdIsNotNull() {
			addCriterion("USER_ID is not null");
			return (Criteria) this;
		}

		public Criteria andUserIdEqualTo(String value) {
			addCriterion("USER_ID =", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdNotEqualTo(String value) {
			addCriterion("USER_ID <>", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdGreaterThan(String value) {
			addCriterion("USER_ID >", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdGreaterThanOrEqualTo(String value) {
			addCriterion("USER_ID >=", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdLessThan(String value) {
			addCriterion("USER_ID <", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdLessThanOrEqualTo(String value) {
			addCriterion("USER_ID <=", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdLike(String value) {
			addCriterion("USER_ID like", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdNotLike(String value) {
			addCriterion("USER_ID not like", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdIn(List<String> values) {
			addCriterion("USER_ID in", values, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdNotIn(List<String> values) {
			addCriterion("USER_ID not in", values, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdBetween(String value1, String value2) {
			addCriterion("USER_ID between", value1, value2, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdNotBetween(String value1, String value2) {
			addCriterion("USER_ID not between", value1, value2, "userId");
			return (Criteria) this;
		}

		public Criteria andPasswordIsNull() {
			addCriterion("PASSWORD is null");
			return (Criteria) this;
		}

		public Criteria andPasswordIsNotNull() {
			addCriterion("PASSWORD is not null");
			return (Criteria) this;
		}

		public Criteria andPasswordEqualTo(String value) {
			addCriterion("PASSWORD =", value, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordNotEqualTo(String value) {
			addCriterion("PASSWORD <>", value, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordGreaterThan(String value) {
			addCriterion("PASSWORD >", value, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordGreaterThanOrEqualTo(String value) {
			addCriterion("PASSWORD >=", value, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordLessThan(String value) {
			addCriterion("PASSWORD <", value, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordLessThanOrEqualTo(String value) {
			addCriterion("PASSWORD <=", value, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordLike(String value) {
			addCriterion("PASSWORD like", value, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordNotLike(String value) {
			addCriterion("PASSWORD not like", value, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordIn(List<String> values) {
			addCriterion("PASSWORD in", values, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordNotIn(List<String> values) {
			addCriterion("PASSWORD not in", values, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordBetween(String value1, String value2) {
			addCriterion("PASSWORD between", value1, value2, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordNotBetween(String value1, String value2) {
			addCriterion("PASSWORD not between", value1, value2, "password");
			return (Criteria) this;
		}

		public Criteria andAuthCdIsNull() {
			addCriterion("AUTH_CD is null");
			return (Criteria) this;
		}

		public Criteria andAuthCdIsNotNull() {
			addCriterion("AUTH_CD is not null");
			return (Criteria) this;
		}

		public Criteria andAuthCdEqualTo(String value) {
			addCriterion("AUTH_CD =", value, "authCd");
			return (Criteria) this;
		}

		public Criteria andAuthCdNotEqualTo(String value) {
			addCriterion("AUTH_CD <>", value, "authCd");
			return (Criteria) this;
		}

		public Criteria andAuthCdGreaterThan(String value) {
			addCriterion("AUTH_CD >", value, "authCd");
			return (Criteria) this;
		}

		public Criteria andAuthCdGreaterThanOrEqualTo(String value) {
			addCriterion("AUTH_CD >=", value, "authCd");
			return (Criteria) this;
		}

		public Criteria andAuthCdLessThan(String value) {
			addCriterion("AUTH_CD <", value, "authCd");
			return (Criteria) this;
		}

		public Criteria andAuthCdLessThanOrEqualTo(String value) {
			addCriterion("AUTH_CD <=", value, "authCd");
			return (Criteria) this;
		}

		public Criteria andAuthCdLike(String value) {
			addCriterion("AUTH_CD like", value, "authCd");
			return (Criteria) this;
		}

		public Criteria andAuthCdNotLike(String value) {
			addCriterion("AUTH_CD not like", value, "authCd");
			return (Criteria) this;
		}

		public Criteria andAuthCdIn(List<String> values) {
			addCriterion("AUTH_CD in", values, "authCd");
			return (Criteria) this;
		}

		public Criteria andAuthCdNotIn(List<String> values) {
			addCriterion("AUTH_CD not in", values, "authCd");
			return (Criteria) this;
		}

		public Criteria andAuthCdBetween(String value1, String value2) {
			addCriterion("AUTH_CD between", value1, value2, "authCd");
			return (Criteria) this;
		}

		public Criteria andAuthCdNotBetween(String value1, String value2) {
			addCriterion("AUTH_CD not between", value1, value2, "authCd");
			return (Criteria) this;
		}

		public Criteria andUpdFuncIdIsNull() {
			addCriterion("UPD_FUNC_ID is null");
			return (Criteria) this;
		}

		public Criteria andUpdFuncIdIsNotNull() {
			addCriterion("UPD_FUNC_ID is not null");
			return (Criteria) this;
		}

		public Criteria andUpdFuncIdEqualTo(String value) {
			addCriterion("UPD_FUNC_ID =", value, "updFuncId");
			return (Criteria) this;
		}

		public Criteria andUpdFuncIdNotEqualTo(String value) {
			addCriterion("UPD_FUNC_ID <>", value, "updFuncId");
			return (Criteria) this;
		}

		public Criteria andUpdFuncIdGreaterThan(String value) {
			addCriterion("UPD_FUNC_ID >", value, "updFuncId");
			return (Criteria) this;
		}

		public Criteria andUpdFuncIdGreaterThanOrEqualTo(String value) {
			addCriterion("UPD_FUNC_ID >=", value, "updFuncId");
			return (Criteria) this;
		}

		public Criteria andUpdFuncIdLessThan(String value) {
			addCriterion("UPD_FUNC_ID <", value, "updFuncId");
			return (Criteria) this;
		}

		public Criteria andUpdFuncIdLessThanOrEqualTo(String value) {
			addCriterion("UPD_FUNC_ID <=", value, "updFuncId");
			return (Criteria) this;
		}

		public Criteria andUpdFuncIdLike(String value) {
			addCriterion("UPD_FUNC_ID like", value, "updFuncId");
			return (Criteria) this;
		}

		public Criteria andUpdFuncIdNotLike(String value) {
			addCriterion("UPD_FUNC_ID not like", value, "updFuncId");
			return (Criteria) this;
		}

		public Criteria andUpdFuncIdIn(List<String> values) {
			addCriterion("UPD_FUNC_ID in", values, "updFuncId");
			return (Criteria) this;
		}

		public Criteria andUpdFuncIdNotIn(List<String> values) {
			addCriterion("UPD_FUNC_ID not in", values, "updFuncId");
			return (Criteria) this;
		}

		public Criteria andUpdFuncIdBetween(String value1, String value2) {
			addCriterion("UPD_FUNC_ID between", value1, value2, "updFuncId");
			return (Criteria) this;
		}

		public Criteria andUpdFuncIdNotBetween(String value1, String value2) {
			addCriterion("UPD_FUNC_ID not between", value1, value2, "updFuncId");
			return (Criteria) this;
		}

		public Criteria andInsUserIdIsNull() {
			addCriterion("INS_USER_ID is null");
			return (Criteria) this;
		}

		public Criteria andInsUserIdIsNotNull() {
			addCriterion("INS_USER_ID is not null");
			return (Criteria) this;
		}

		public Criteria andInsUserIdEqualTo(String value) {
			addCriterion("INS_USER_ID =", value, "insUserId");
			return (Criteria) this;
		}

		public Criteria andInsUserIdNotEqualTo(String value) {
			addCriterion("INS_USER_ID <>", value, "insUserId");
			return (Criteria) this;
		}

		public Criteria andInsUserIdGreaterThan(String value) {
			addCriterion("INS_USER_ID >", value, "insUserId");
			return (Criteria) this;
		}

		public Criteria andInsUserIdGreaterThanOrEqualTo(String value) {
			addCriterion("INS_USER_ID >=", value, "insUserId");
			return (Criteria) this;
		}

		public Criteria andInsUserIdLessThan(String value) {
			addCriterion("INS_USER_ID <", value, "insUserId");
			return (Criteria) this;
		}

		public Criteria andInsUserIdLessThanOrEqualTo(String value) {
			addCriterion("INS_USER_ID <=", value, "insUserId");
			return (Criteria) this;
		}

		public Criteria andInsUserIdLike(String value) {
			addCriterion("INS_USER_ID like", value, "insUserId");
			return (Criteria) this;
		}

		public Criteria andInsUserIdNotLike(String value) {
			addCriterion("INS_USER_ID not like", value, "insUserId");
			return (Criteria) this;
		}

		public Criteria andInsUserIdIn(List<String> values) {
			addCriterion("INS_USER_ID in", values, "insUserId");
			return (Criteria) this;
		}

		public Criteria andInsUserIdNotIn(List<String> values) {
			addCriterion("INS_USER_ID not in", values, "insUserId");
			return (Criteria) this;
		}

		public Criteria andInsUserIdBetween(String value1, String value2) {
			addCriterion("INS_USER_ID between", value1, value2, "insUserId");
			return (Criteria) this;
		}

		public Criteria andInsUserIdNotBetween(String value1, String value2) {
			addCriterion("INS_USER_ID not between", value1, value2, "insUserId");
			return (Criteria) this;
		}

		public Criteria andInsDateIsNull() {
			addCriterion("INS_DATE is null");
			return (Criteria) this;
		}

		public Criteria andInsDateIsNotNull() {
			addCriterion("INS_DATE is not null");
			return (Criteria) this;
		}

		public Criteria andInsDateEqualTo(Date value) {
			addCriterion("INS_DATE =", value, "insDate");
			return (Criteria) this;
		}

		public Criteria andInsDateNotEqualTo(Date value) {
			addCriterion("INS_DATE <>", value, "insDate");
			return (Criteria) this;
		}

		public Criteria andInsDateGreaterThan(Date value) {
			addCriterion("INS_DATE >", value, "insDate");
			return (Criteria) this;
		}

		public Criteria andInsDateGreaterThanOrEqualTo(Date value) {
			addCriterion("INS_DATE >=", value, "insDate");
			return (Criteria) this;
		}

		public Criteria andInsDateLessThan(Date value) {
			addCriterion("INS_DATE <", value, "insDate");
			return (Criteria) this;
		}

		public Criteria andInsDateLessThanOrEqualTo(Date value) {
			addCriterion("INS_DATE <=", value, "insDate");
			return (Criteria) this;
		}

		public Criteria andInsDateIn(List<Date> values) {
			addCriterion("INS_DATE in", values, "insDate");
			return (Criteria) this;
		}

		public Criteria andInsDateNotIn(List<Date> values) {
			addCriterion("INS_DATE not in", values, "insDate");
			return (Criteria) this;
		}

		public Criteria andInsDateBetween(Date value1, Date value2) {
			addCriterion("INS_DATE between", value1, value2, "insDate");
			return (Criteria) this;
		}

		public Criteria andInsDateNotBetween(Date value1, Date value2) {
			addCriterion("INS_DATE not between", value1, value2, "insDate");
			return (Criteria) this;
		}

		public Criteria andUpdUserIdIsNull() {
			addCriterion("UPD_USER_ID is null");
			return (Criteria) this;
		}

		public Criteria andUpdUserIdIsNotNull() {
			addCriterion("UPD_USER_ID is not null");
			return (Criteria) this;
		}

		public Criteria andUpdUserIdEqualTo(String value) {
			addCriterion("UPD_USER_ID =", value, "updUserId");
			return (Criteria) this;
		}

		public Criteria andUpdUserIdNotEqualTo(String value) {
			addCriterion("UPD_USER_ID <>", value, "updUserId");
			return (Criteria) this;
		}

		public Criteria andUpdUserIdGreaterThan(String value) {
			addCriterion("UPD_USER_ID >", value, "updUserId");
			return (Criteria) this;
		}

		public Criteria andUpdUserIdGreaterThanOrEqualTo(String value) {
			addCriterion("UPD_USER_ID >=", value, "updUserId");
			return (Criteria) this;
		}

		public Criteria andUpdUserIdLessThan(String value) {
			addCriterion("UPD_USER_ID <", value, "updUserId");
			return (Criteria) this;
		}

		public Criteria andUpdUserIdLessThanOrEqualTo(String value) {
			addCriterion("UPD_USER_ID <=", value, "updUserId");
			return (Criteria) this;
		}

		public Criteria andUpdUserIdLike(String value) {
			addCriterion("UPD_USER_ID like", value, "updUserId");
			return (Criteria) this;
		}

		public Criteria andUpdUserIdNotLike(String value) {
			addCriterion("UPD_USER_ID not like", value, "updUserId");
			return (Criteria) this;
		}

		public Criteria andUpdUserIdIn(List<String> values) {
			addCriterion("UPD_USER_ID in", values, "updUserId");
			return (Criteria) this;
		}

		public Criteria andUpdUserIdNotIn(List<String> values) {
			addCriterion("UPD_USER_ID not in", values, "updUserId");
			return (Criteria) this;
		}

		public Criteria andUpdUserIdBetween(String value1, String value2) {
			addCriterion("UPD_USER_ID between", value1, value2, "updUserId");
			return (Criteria) this;
		}

		public Criteria andUpdUserIdNotBetween(String value1, String value2) {
			addCriterion("UPD_USER_ID not between", value1, value2, "updUserId");
			return (Criteria) this;
		}

		public Criteria andUpdDateIsNull() {
			addCriterion("UPD_DATE is null");
			return (Criteria) this;
		}

		public Criteria andUpdDateIsNotNull() {
			addCriterion("UPD_DATE is not null");
			return (Criteria) this;
		}

		public Criteria andUpdDateEqualTo(Date value) {
			addCriterion("UPD_DATE =", value, "updDate");
			return (Criteria) this;
		}

		public Criteria andUpdDateNotEqualTo(Date value) {
			addCriterion("UPD_DATE <>", value, "updDate");
			return (Criteria) this;
		}

		public Criteria andUpdDateGreaterThan(Date value) {
			addCriterion("UPD_DATE >", value, "updDate");
			return (Criteria) this;
		}

		public Criteria andUpdDateGreaterThanOrEqualTo(Date value) {
			addCriterion("UPD_DATE >=", value, "updDate");
			return (Criteria) this;
		}

		public Criteria andUpdDateLessThan(Date value) {
			addCriterion("UPD_DATE <", value, "updDate");
			return (Criteria) this;
		}

		public Criteria andUpdDateLessThanOrEqualTo(Date value) {
			addCriterion("UPD_DATE <=", value, "updDate");
			return (Criteria) this;
		}

		public Criteria andUpdDateIn(List<Date> values) {
			addCriterion("UPD_DATE in", values, "updDate");
			return (Criteria) this;
		}

		public Criteria andUpdDateNotIn(List<Date> values) {
			addCriterion("UPD_DATE not in", values, "updDate");
			return (Criteria) this;
		}

		public Criteria andUpdDateBetween(Date value1, Date value2) {
			addCriterion("UPD_DATE between", value1, value2, "updDate");
			return (Criteria) this;
		}

		public Criteria andUpdDateNotBetween(Date value1, Date value2) {
			addCriterion("UPD_DATE not between", value1, value2, "updDate");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table KENSYU.M_AUTH_USER
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	public static class Criterion {
		private String condition;
		private Object value;
		private Object secondValue;
		private boolean noValue;
		private boolean singleValue;
		private boolean betweenValue;
		private boolean listValue;
		private String typeHandler;

		public String getCondition() {
			return condition;
		}

		public Object getValue() {
			return value;
		}

		public Object getSecondValue() {
			return secondValue;
		}

		public boolean isNoValue() {
			return noValue;
		}

		public boolean isSingleValue() {
			return singleValue;
		}

		public boolean isBetweenValue() {
			return betweenValue;
		}

		public boolean isListValue() {
			return listValue;
		}

		public String getTypeHandler() {
			return typeHandler;
		}

		protected Criterion(String condition) {
			super();
			this.condition = condition;
			this.typeHandler = null;
			this.noValue = true;
		}

		protected Criterion(String condition, Object value, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.typeHandler = typeHandler;
			if (value instanceof List<?>) {
				this.listValue = true;
			} else {
				this.singleValue = true;
			}
		}

		protected Criterion(String condition, Object value) {
			this(condition, value, null);
		}

		protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.secondValue = secondValue;
			this.typeHandler = typeHandler;
			this.betweenValue = true;
		}

		protected Criterion(String condition, Object value, Object secondValue) {
			this(condition, value, secondValue, null);
		}
	}

	/**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table m_auth_user
     *
     * @mbggenerated do_not_delete_during_merge Tue May 17 10:41:15 JST 2016
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}