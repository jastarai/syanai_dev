package jp.co.fukuya_k.db.generator;

import java.util.List;
import jp.co.fukuya_k.db.generator.Dept;
import jp.co.fukuya_k.db.generator.DeptHelper;
import org.apache.ibatis.annotations.Param;

public interface DeptMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table KENSYU.DEPT
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	int countByHelper(DeptHelper example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table KENSYU.DEPT
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	int deleteByHelper(DeptHelper example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table KENSYU.DEPT
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	int deleteByPrimaryKey(Short deptno);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table KENSYU.DEPT
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	int insert(Dept record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table KENSYU.DEPT
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	int insertSelective(Dept record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table KENSYU.DEPT
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	List<Dept> selectByHelper(DeptHelper example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table KENSYU.DEPT
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	Dept selectByPrimaryKey(Short deptno);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table KENSYU.DEPT
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	int updateByHelperSelective(@Param("record") Dept record, @Param("example") DeptHelper example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table KENSYU.DEPT
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	int updateByHelper(@Param("record") Dept record, @Param("example") DeptHelper example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table KENSYU.DEPT
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	int updateByPrimaryKeySelective(Dept record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table KENSYU.DEPT
	 * @mbggenerated  Wed Jun 08 18:01:06 JST 2016
	 */
	int updateByPrimaryKey(Dept record);
}