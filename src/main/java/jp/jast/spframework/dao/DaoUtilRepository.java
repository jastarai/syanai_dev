package jp.jast.spframework.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DaoUtilRepository {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public <T> T selectOne(String statement, Object parameter) {
		return sqlSessionTemplate.selectOne(statement, parameter);
	}
	public int update(String statement, Object parameter) {
		return sqlSessionTemplate.update(statement, parameter);
	}
	public int insert(String statement, Object parameter) {
		return sqlSessionTemplate.insert(statement, parameter);
	}
	public int delete(String statement, Object parameter) {
		return sqlSessionTemplate.delete(statement, parameter);
	}
// TODO 【FUKUYA】サンプルより追加
	public <T> List<T> select(String statement, Object parameter) {
		return sqlSessionTemplate.selectList(statement, parameter);
	}
}
