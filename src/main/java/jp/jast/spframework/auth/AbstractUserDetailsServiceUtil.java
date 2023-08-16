package jp.jast.spframework.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * UserDetailsユーティリティ。
 */
public abstract class AbstractUserDetailsServiceUtil {

	/**
	 * ユーザ情報SelectID取得
	 * 
	 * @return ユーザ情報SelectID
	 */
	abstract protected String getUserStatement();

	/**
	 * ロール情報SelectId取得
	 * 
	 * @return ロール情報SelectId
	 */
	abstract protected String getRoleStatement();

	/**
	 * ユーザ情報オブジェクトインスタンス生成
	 * 
	 * @param user ユーザ情報
	 * @param roleList ロール情報
	 * @return ユーザ情報インスタンス
	 */
	abstract protected UserDetails createUserDetailsInstance(Object user, List<String> roleList);

	/**
	 * Authorityリスト作成（複数用）
	 * 
	 * @param roleList ロール情報リスト
	 * @return Authorityリスト
	 */
	protected List<GrantedAuthority> createAuthoritiyList(List<String> roleList) {
		List<GrantedAuthority>authorityList = new ArrayList<>();
		for (String role : roleList) {
			authorityList.add(new SimpleGrantedAuthority(role));
		}
		return authorityList;
	}

	/**
	 * Authorityリスト作成（1件用）
	 * 
	 * @param role ロール情報
	 * @return Authorityリスト
	 */
	protected List<GrantedAuthority> createAuthoritiyList(String role) {
		List<GrantedAuthority>authorityList = new ArrayList<>();
		authorityList.add(new SimpleGrantedAuthority(role));
		return authorityList;
	}
}
