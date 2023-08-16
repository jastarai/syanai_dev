package jp.co.fukuya_k.system.interfaces;

import java.util.Date;

import jp.jast.spframework.entity.CommonEntityInterface;

public interface FukuyaEntityInterface extends CommonEntityInterface {

	// プロジェクト固有・共通プロパティ値変換用
	Date getUpdDate();

	void setInsDate(Date date);

	void setInsUserId(String userId);

	void setUpdDate(Date date);

	void setUpdUserId(String userId);

	void setUpdFuncId(String funcId);

	// プロジェクト共通インターフェイス
	@Override
	default void setRegistDate(Date date) {
		setInsDate(date);
	}

	@Override
	default void setRegistUserId(String userId) {
		setInsUserId(userId);
	}

	@Override
	default void setUpdateDate(Date date) {
		setUpdDate(date);
	}
	@Override
	default Date getUpdateDate() {
		return getUpdDate();
	}

	@Override
	default void setUpdateUserId(String userId) {
		setUpdUserId(userId);
	}

	@Override
	default void setUpdateFunctionId(String funcId) {
		setUpdFuncId(funcId);
	}
}
