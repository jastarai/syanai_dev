package jp.jast.spframework.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import jp.jast.spframework.config.SpFrameworkProperties;
import jp.jast.spframework.entity.CommonEntityInterface;
import jp.jast.spframework.entity.EntityBase;

@Component
public class DaoUtil {

	private static final String DAO_PKG_SEPARATOR = ".";

	@Autowired
	private SpFrameworkProperties properties;
	@Autowired
	private DaoUtilRepository repository;

	/**
	 * update (select for update)
	 * 
	 * @param entity
	 * @return
	 */
	public boolean update(CommonEntityInterface entity) {
		return updateExecute(entity, getUpdateUserId(), getUpdateFunctionId());
	}

	/**
	 * update (select for update)
	 * 
	 * @param entity
	 * @param functionId
	 * @return
	 */
	public boolean update(CommonEntityInterface entity, String functionId) {
		return updateExecute(entity, getUpdateUserId(), functionId);
	}


	/**
	 * update (select for update)
	 * 
	 * @param entity
	 * @param registUser
	 * @param functionId
	 * @return
	 */
	public boolean update(CommonEntityInterface entity, String registUser, String functionId) {
		return updateExecute(entity, registUser, functionId);
	}
	/**
	 * update (select for update)
	 * 
	 * @param entity
	 * @param registUser
	 * @param functionId
	 * @return
	 */
	private boolean updateExecute(CommonEntityInterface entity, String registUser, String functionId) {
		boolean retVal = false;

		// select for update
		CommonEntityInterface current = repository.selectOne(getDaoName(entity) + properties.getDao().getQuerySelectForUpdate(), entity);

		if (current != null
				&& entity.getUpdateDate().getTime() == current.getUpdateDate().getTime()) {

			// 更新日付、更新者、更新画面IDを設定
			setUpdInf(entity, new Date(), registUser, functionId);

			// update
			if (repository.update(getDaoName(entity) + properties.getDao().getQueryUdate(), entity) == 1) {
				retVal = true;
			}
		}
		return retVal;
	}

	
	/**
	 * updateWithSqlId (select for update)
	 * ※SQLIDを指定可能<br />
	 * ※排他有無を指定可能
	 * 
	 * @param entity
	 * @param selectSqlId
	 * @param updateSqlId
	 * @param isLockNeed
	 * @return
	 * @throws Exception 
	 */
	public boolean updateWithSqlId(CommonEntityInterface entity, String selectSqlId, String updateSqlId, boolean isLockNeed) throws Exception {
		return updateExecuteWithSqlId(entity, getUpdateUserId(), getUpdateFunctionId(), selectSqlId, updateSqlId, isLockNeed);
	}
	
	/**
	 * updateWithSqlId (select for update)
	 * ※SQLIDを指定可能<br />
	 * ※排他有無を指定可能
	 * 
	 * @param entity
	 * @param registUser
	 * @param functionId
	 * @param selectSqlId
	 * @param updateSqlId
	 * @param isLockNeed
	 * @return
	 * @throws Exception 
	 */
	public boolean updateWithSqlId(CommonEntityInterface entity, String registUser, String functionId, 
									String selectSqlId, String updateSqlId, boolean isLockNeed) throws Exception {
		return updateExecuteWithSqlId(entity, registUser, functionId, selectSqlId, updateSqlId, isLockNeed);
	}
	
	/**
	 * updateWithSqlId (select for update)<br />
	 * ※SQLIDを指定可能<br />
	 * ※排他有無を指定可能
	 * 
	 * @param entity
	 * @param registUser
	 * @param functionId
	 * @return
	 * @throws Exception 
	 */
	private boolean updateExecuteWithSqlId(CommonEntityInterface entity, String registUser, String functionId,
											String selectSqlId, String updateSqlId, boolean isLockNeed) throws Exception {
		boolean retVal = false;
		
		CommonEntityInterface current = null;
		
		if (isLockNeed == true) {
			// ロックの判定が必要な場合
			// select for update
			current = repository.selectOne(new StringBuilder(getUpcastedDaoName(entity)).append(selectSqlId).toString(), entity);
		}

		if (isLockNeed == false || (isLockNeed == true && current != null
				&& entity.getUpdateDate().getTime() == current.getUpdateDate().getTime())) {

			// 更新日付、更新者、更新画面IDを設定
			setUpdInf(entity, new Date(), registUser, functionId);

			// update
			if (repository.update(new StringBuilder(getUpcastedDaoName(entity)).append(updateSqlId).toString(), entity) == 1) {
				retVal = true;
			}
		}
		return retVal;
	}
	
	/**
	 * insert
	 * 
	 * @param entity
	 * @return
	 */
	public boolean insert(CommonEntityInterface entity) {
		return insertExecute(entity, getUpdateUserId(), getUpdateFunctionId());
	}

	/**
	 * insert
	 * 
	 * @param entity
	 * @param functionId
	 * @return
	 */
	public boolean insert(CommonEntityInterface entity, String functionId) {
		return insertExecute(entity, getUpdateUserId(), functionId);
	}

	/**
	 * insert
	 * 
	 * @param entity
	 * @param registUser
	 * @param functionId
	 * @return
	 */
	public boolean insert(CommonEntityInterface entity, String registUser, String functionId) {
		return insertExecute(entity, registUser, functionId);
	}

	/**
	 * insert
	 * 
	 * @param entity
	 * @param registUser
	 * @param functionId
	 * @return
	 */
	private boolean insertExecute(CommonEntityInterface entity, String registUser, String functionId) {
		boolean retVal = false;

		setRgstInf(entity, new Date(), registUser, functionId);
		
		try {
			if (repository.insert(getDaoName(entity) + properties.getDao().getQueryInsert(), entity) == 1) {
				retVal = true;
			}
		} catch (DuplicateKeyException de) {
			// 一意キー制約違反の場合は、戻り値をfalseとします
			retVal = false;
		} catch (Throwable ex) {
			// 一意キー制約違反以外の場合は上位にスローします
			throw ex;
		}

		return retVal;
	}

	/**
	 * insertWithSqlId
	 * ※SQLIDを指定可能<br />
	 * 
	 * @param entity
	 * @insertSqlId
	 * @return
	 * @throws Exception 
	 */
	public boolean insertWithSqlId(CommonEntityInterface entity, String insertSqlId) throws Exception {
		return insertExecuteWithSqlId(entity, getUpdateUserId(), getUpdateFunctionId(), insertSqlId);
	}
	
	/**
	 * insertExecuteWithSqlId<br />
	 * ※SQLIDを指定可能<br />
	 * 
	 * @param entity
	 * @param registUser
	 * @param functionId
	 * @param insertSqlId
	 * @return
	 * @throws Exception 
	 */
	private boolean insertExecuteWithSqlId(CommonEntityInterface entity, String registUser, String functionId,
											String insertSqlId) throws Exception {
		boolean retVal = false;

		setRgstInf(entity, new Date(), registUser, functionId);

		try {
			if (repository.insert(new StringBuilder(getUpcastedDaoName(entity)).append(insertSqlId).toString(), entity) == 1) {
				retVal = true;
			}
		} catch (DuplicateKeyException de) {
			// 一意キー制約違反の場合は、戻り値をfalseとします
			retVal = false;
		} catch (Throwable ex) {
			// 一意キー制約違反以外の場合は上位にスローします
			throw ex;
		}
		
		return retVal;
	}
	
	/**
	 * delete & insert
	 * 
	 * @param entity
	 * @return
	 */
	public boolean deleteAndInsert(CommonEntityInterface entity) {
		return deleteAndInsertExecute(entity, getUpdateUserId(), getUpdateFunctionId());
	}

	/**
	 * delete & insert
	 * 
	 * @param entity
	 * @param functionId
	 * @return
	 */
	public boolean deleteAndInsert(CommonEntityInterface entity,  String functionId) {
		return deleteAndInsertExecute(entity, getUpdateUserId(), functionId);
	}

	/**
	 * delete & insert
	 * 
	 * @param entity
	 * @param registUser
	 * @param functionId
	 * @return
	 */
	public boolean deleteAndInsert(CommonEntityInterface entity, String registUser, String functionId) {
		return deleteAndInsertExecute(entity, registUser, functionId);
	}

	/**
	 * delete & insert
	 * 
	 * @param entity
	 * @param registUser
	 * @param functionId
	 * @return
	 */
	private boolean deleteAndInsertExecute(CommonEntityInterface entity, String registUser, String functionId) {
		boolean retVal = false;

		// select for update
		CommonEntityInterface current = repository.selectOne(getDaoName(entity) + properties.getDao().getQuerySelectForUpdate(), entity);

		if (current != null
				&& entity.getUpdateDate().getTime() == current.getUpdateDate().getTime()) {

			// delete
			if (repository.delete(getDaoName(entity) + properties.getDao().getQueryDelete(), entity) == 1 ) {
				// insert
				setRgstInf(entity, new Date(), registUser, functionId);
				if (repository.insert(getDaoName(entity) + properties.getDao().getQueryInsert(), entity) == 1) {
					retVal = true;
				}
			}
		}
		return retVal;
	}

	/**
	 * 論理削除<br>
	 * 論理削除フラグを持たないテーブルに対して実行した場合はエラーが発生する。
	 * 
	 * @param entity
	 * @return
	 * @throws Exception 
	 */
	public boolean logicalDelete(CommonEntityInterface entity) throws Exception {
		return logicalDeleteExecute(entity, getUpdateUserId(), getUpdateFunctionId());
	}

	/**
	 * 論理削除<br>
	 * 論理削除フラグを持たないテーブルに対して実行した場合はエラーが発生する。
	 * 
	 * @param entity
	 * @param functionId
	 * @return
	 * @throws Exception 
	 */
	public boolean logicalDelete(CommonEntityInterface entity, String functionId) throws Exception {
		return logicalDeleteExecute(entity, getUpdateUserId(), functionId);
	}

	/**
	 * 論理削除<br>
	 * 論理削除フラグを持たないテーブルに対して実行した場合はエラーが発生する。
	 * 
	 * @param entity
	 * @param registUser
	 * @param functionId
	 * @return
	 * @throws Exception 
	 */
	public boolean logicalDelete(CommonEntityInterface entity, String registUser, String functionId) throws Exception {
		return logicalDeleteExecute(entity, registUser, functionId);
	}

	/**
	 * 論理削除<br>
	 * 論理削除フラグを持たないテーブルに対して実行した場合はエラーが発生する。
	 * 
	 * @param entity
	 * @param registUser
	 * @param functionId
	 * @return
	 * @throws Exception 
	 */
	private boolean logicalDeleteExecute(CommonEntityInterface entity, String registUser, String functionId) throws Exception {
		boolean retVal = false;

		// select for update
		CommonEntityInterface current = repository.selectOne(getDaoName(entity) + properties.getDao().getQuerySelectForUpdate(), entity);

		if (current != null
				&& entity.getUpdateDate().getTime() == current.getUpdateDate().getTime()) {

			setDeleteFlag(entity);
			// 更新日付、更新者、更新画面IDを設定
			setUpdInf(entity, new Date(), registUser, functionId);

			// update
			if (repository.update(getDaoName(entity) + properties.getDao().getQueryUdate(), entity) == 1) {
				retVal = true;
			}
		}
		return retVal;
	}

	/**
	 * 物理削除
	 * 
	 * @param entity
	 * @return
	 */
	public boolean physicalDelete(CommonEntityInterface entity) {
		boolean retVal = false;

		// select for update
		CommonEntityInterface current = repository.selectOne(getDaoName(entity) + properties.getDao().getQuerySelectForUpdate(), entity);

		if (current != null
				&& entity.getUpdateDate().getTime() == current.getUpdateDate().getTime()) {

			// delete
			if (repository.delete(getDaoName(entity) + properties.getDao().getQueryDelete(), entity) == 1) {
				retVal = true;
			}
		}
		return retVal;
	}
	
	/**
	 * 物理削除
	 * ※SQLIDを指定可能<br />
	 * ※排他有無を指定可能
	 * 
	 * @param entity
	 * @param selectSqlId
	 * @param deleteSqlId
	 * @return
	 * @throws Exception 
	 */
	public boolean physicalDeleteWithSqlId(CommonEntityInterface entity, String selectSqlId, String deleteSqlId, boolean isLockNeed) throws Exception {

		boolean retVal = false;

		CommonEntityInterface current = null;
		
		if (isLockNeed == true) {
			// ロックの判定が必要な場合
			// select for update
			current = repository.selectOne(new StringBuilder(getUpcastedDaoName(entity)).append(selectSqlId).toString(), entity);
		}

		if (isLockNeed == false || (isLockNeed == true && current != null
				&& entity.getUpdateDate().getTime() == current.getUpdateDate().getTime())) {

			// delete
			if (repository.delete(new StringBuilder(getUpcastedDaoName(entity)).append(deleteSqlId).toString(), entity) == 1) {
				retVal = true;
			}
		}
		
		return retVal;
	}
	
	/**
	 * 
	 * @param selectSqlId
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public CommonEntityInterface selectOne(final String selectSqlId, CommonEntityInterface entity) throws Exception {
		CommonEntityInterface result = repository.selectOne(
				new StringBuilder(getUpcastedDaoName(entity)).append(selectSqlId).toString(), entity);
		return result;
	}
	/**
	 * 1テーブル1カラムに対する条件指定の集計関数結果1件を取得
	 * @param selectSqlId
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public int getOneItemResult(final String selectSqlId, CommonEntityInterface entity) throws Exception {
		EntityBase result = repository.selectOne(
				new StringBuilder(getUpcastedDaoName(entity)).append(selectSqlId).toString(), entity);
		if(result == null){
			return 0;
		}
		return result.getOneItemResult();
	}
	
	/**
	 * 
	 * @param selectSqlId
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public List<CommonEntityInterface> select(final String selectSqlId, CommonEntityInterface entity) throws Exception {
		List<CommonEntityInterface> result = repository.select(
				new StringBuilder(getUpcastedDaoName(entity)).append(selectSqlId).toString(), entity);
		if(result == null){
			return new ArrayList<CommonEntityInterface>();
		}
		return result;
	}
	
	/**
	 * 
	 * @param selectSqlId
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> selectList(final String selectSqlId, final CommonEntityInterface entity) throws Exception {
		List<T> result = repository.select(
				new StringBuilder(getUpcastedDaoName(entity)).append(selectSqlId).toString(), entity);
		if(result == null){
			return (List<T>) new ArrayList<T>();
		}
		return (List<T>) result;
	}
	
	/**
	 * MySQLの複数レコードINSERT(複数values句(?,?,?,?)(?,?,?,?)(?,?,?,?))に対応
	 * @param insertListSuffix
	 * @param entityList
	 * @param useFixedDate 登録&更新日付を全件同一にする場合はtrue
	 * @return
	 * @throws Exception
	 */
	public boolean insertListForMySQL(final String insertListSuffix, List<CommonEntityInterface> entityList, boolean useFixedDate)
	throws Exception{
		return insertListForMySQLExecute(insertListSuffix, entityList, useFixedDate, getUpdateUserId(), getUpdateFunctionId());
	}
	/**
	 * 
	 * @param entityList
	 * @param useFixedDate 登録&更新日付を全件同一にする場合はtrue
	 * @param registUser
	 * @param functionId
	 * @return
	 */
	/**
	 * MySQLの複数レコードINSERT(複数values句(?,?,?,?)(?,?,?,?)(?,?,?,?))に対応
	 * 
	 * @param insertListSuffix
	 * @param entityList
	 * @param useFixedDate 登録&更新日付を全件同一にする場合はtrue
	 * @param functionId
	 * @return
	 * @throws Exception
	 */
	public boolean insertListForMySQL(final String insertListSuffix, List<CommonEntityInterface> entityList, boolean useFixedDate, String functionId)
	throws Exception{
		return insertListForMySQLExecute(insertListSuffix, entityList, useFixedDate, getUpdateUserId(), functionId);
	}
	/**
	 * MySQLの複数レコードINSERT(複数values句(?,?,?,?)(?,?,?,?)(?,?,?,?))に対応
	 * @param insertListSuffix
	 * @param entityList
	 * @param useFixedDate 登録&更新日付を全件同一にする場合はtrue
	 * @param registUser
	 * @param functionId
	 * @return
	 * @throws Exception
	 */
	public boolean insertListForMySQL(final String insertListSuffix, List<CommonEntityInterface> entityList, boolean useFixedDate, String registUser, String functionId)
	throws Exception{
		return insertListForMySQLExecute(insertListSuffix, entityList, useFixedDate, registUser, functionId);
	}
	/**
	 * MySQLの複数レコードINSERT(複数values句(?,?,?,?)(?,?,?,?)(?,?,?,?))に対応
	 * @param insertListSuffix
	 * @param entityList
	 * @param useFixedDate 登録&更新日付を全件同一にする場合はtrue
	 * @param registUser
	 * @param functionId
	 * @return
	 * @throws Exception
	 */
	private boolean insertListForMySQLExecute(
			  final String insertListSuffix
			, List<CommonEntityInterface> entityList
			, boolean useFixedDate
			, String registUser
			, String functionId) throws Exception {
		boolean retVal = false;
		if(entityList == null || entityList.size() == 0){
			return retVal;
		}
		final Date fixedDate = new Date();
		for(CommonEntityInterface entity : entityList){
			Date nowDate = new Date();
			setRgstInf(entity, (useFixedDate ? fixedDate : nowDate), registUser, functionId);
		}
		// 全件登録に成功したらtrueを返す
		retVal = (
				repository.insert(
						new StringBuilder(getUpcastedDaoName(entityList.get(0)))
						.append(insertListSuffix).toString(), entityList) == entityList.size()
				) ? true : false;
		return retVal;
	}
	/**
	 * 
	 * @param deleteListSuffix
	 * @param insertListSuffix
	 * @param exclusiveKey 排他対象テーブルのキー
	 * @param entityList
	 * @param useFixedDate 登録&更新日付を全件同一にする場合はtrue
	 * @return
	 * @throws Exception
	 */
	public boolean deleteAndInsertListForMySQL(
			  final String deleteListSuffix
			, final String insertListSuffix
			, List<CommonEntityInterface> exclusiveKeys
			, List<CommonEntityInterface> entityList
			, boolean useFixedDate) throws Exception {
		boolean retVal = false;
		String daoName = getUpcastedDaoName(exclusiveKeys.get(0));
		// for update statement
		String forUpdateSqlId =
				new StringBuilder(daoName).append(properties.getDao().getQuerySelectForUpdate()).toString();
		// delete statement
		String deleteSqlId =
				new StringBuilder(daoName).append(deleteListSuffix).toString();
		/**
		 * 排他キー毎に処理
		 */
		for(CommonEntityInterface exclusiveKey : exclusiveKeys){
			// select for update
			// TODO(insertのcommit時まで排他の想定)
			CommonEntityInterface current = repository.selectOne(forUpdateSqlId, exclusiveKey);
			if(current != null){
				// 既存の洗い替え
				if(exclusiveKey.getUpdateDate().getTime() == current.getUpdateDate().getTime()){
					// delete
					// TODO このdelete結果(retVal)必要か？
					retVal = (repository.delete(deleteSqlId, exclusiveKey) > 0);
				}
			}
		}
		// insert (List for MySQL)
		retVal = insertListForMySQL(insertListSuffix, entityList, useFixedDate);
		return retVal;
	}
	/*
	 * システム管理項目（登録用・更新用）に値を設定する
	 */
	private void setRgstInf(CommonEntityInterface entity, Date now, String userId, String funcId) {
		entity.setRegistDate(now);
		entity.setRegistUserId(userId);
		setUpdInf(entity, now, userId, funcId);
	}
	/*
	 * システム管理項目（更新用）に値を設定する
	 */
	private void setUpdInf(CommonEntityInterface entity, Date now, String userId, String funcId) {
		entity.setUpdateDate(now);
		entity.setUpdateUserId(userId);
		entity.setUpdateFunctionId(funcId);
	}
	/*
	 * プロパティファイルの定義値＋entity（インスタンス）のクラス名を元に、<br>
	 * entityに対応するDaoの名前を取得する
	 */
	private String getDaoName(CommonEntityInterface entity) {
		return properties.getDao().getPkgNamePrefix() + DAO_PKG_SEPARATOR
				+ entity.getClass().getSimpleName()
				+ properties.getDao().getPkgNameSufix() + DAO_PKG_SEPARATOR;
	}
	/*
	 * 削除フラグを設定する。
	 */
	private void setDeleteFlag(CommonEntityInterface entity) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = entity.getClass().getDeclaredMethod("set" + StringUtils.capitalize(properties.getDao().getColumnDeleteFlag()), String.class);
		method.invoke(entity, "1");
	}
	/*
	 * 更新ユーザーIDを取得する。
	 */
	private String getUpdateUserId() {
		String retVal = "";
		SecurityContext context = SecurityContextHolder.getContext();
		if (context != null) {
			retVal = ((UserDetails) context.getAuthentication().getPrincipal()).getUsername();
		}
		return retVal;
	}
	/*
	 * 更新機能IDを取得する。
	 */
	protected String getUpdateFunctionId() {
		return properties.getDao().getDefaultFunctionId();
	}
	/**
	 * 引数のエンティティクラスに対してCommonEntityInterfaceの実装クラスまでアップキャストしたクラスのDao名を返す。
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	protected String getUpcastedDaoName(CommonEntityInterface entity) throws Exception {
		
		if(entity == null) return "";

		// 引数のentityのクラスにおいて直接実装しているインターフェースを取得する
		Class<?> baseEntity = entity.getClass();
		Class<?>[] interfaces = baseEntity.getInterfaces();
		boolean isTarget = false;
		while(!isTarget){
			for(Class<?> cls : interfaces){
				// 処理中のインターフェースクラスがCommonEntityInterface(またはそのサブインターフェース)であるかをチェック
				isTarget = CommonEntityInterface.class.isAssignableFrom(cls);
				if(isTarget) break;
			}
			if(!isTarget){
				// このクラスがCommonEntityInterface(またはそのサブインターフェース)を直接実装していない場合
				// そのスーパークラスのインターフェースを取得
				baseEntity = baseEntity.getSuperclass();
				interfaces = baseEntity.getInterfaces();
			}
		}
		// CommonEntityInterface(またはそのサブインターフェース)の直接実装クラスでDao名を得る
		return getDaoName(CommonEntityInterface.class.cast(baseEntity.newInstance()));
	}
}
