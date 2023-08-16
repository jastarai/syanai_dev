package jp.jast.spframework.dao;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import lombok.Data;

/**
 * DMLのWHERE句以降へ使用する、条件/範囲/その他動的パラメータの格納に使用するクラスです.
 * @author
 *
 */
@Data
public class ParamBinder<P> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1445997091605424075L;
	@Autowired
	private P bindParam;
	private Pageable pageable;


	/**
	 * デフォルトコンストラクタ<BR>
	 * ※ParamBinderがインスタンス生成する際に必要
	 */
	public ParamBinder(){}
	/**
	 * 
	 * @param bindParam
	 */
	public ParamBinder(P bindParam){
		this.bindParam  = bindParam;
	}
	/**
	 * @param bindParam
	 * @param pageable
	 */
	public ParamBinder(P bindParam, Pageable pageable){
		this.bindParam  = bindParam;
		this.pageable   = pageable;
	}
}
