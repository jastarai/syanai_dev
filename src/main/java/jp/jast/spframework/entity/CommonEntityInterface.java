package jp.jast.spframework.entity;

import java.util.Date;

public interface CommonEntityInterface {

	Date getUpdateDate();

	void setRegistDate(Date date);

	void setRegistUserId(String userId);

	void setUpdateDate(Date date);

	void setUpdateUserId(String userId);

	void setUpdateFunctionId(String funcId);

}
