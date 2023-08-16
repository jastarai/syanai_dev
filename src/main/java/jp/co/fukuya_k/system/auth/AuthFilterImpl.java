package jp.co.fukuya_k.system.auth;

import java.util.Collection;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import jp.co.fukuya_k.db.generator.AuthUserDetail;
import jp.co.fukuya_k.system.constants.MapperConstants;
import jp.co.fukuya_k.system.utils.CheckUtil;
import jp.jast.spframework.auth.AbstractAuthFilter;

@Component
public class AuthFilterImpl extends AbstractAuthFilter {

	@Autowired
	DataSource dataSource;
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	/* (非 Javadoc)
	 * @see jp.jast.spframework.auth.AbstractAuthFilter#isApproval(javax.servlet.ServletRequest, org.springframework.security.core.userdetails.UserDetails)
	 */
	@Override
	protected boolean isApproval(ServletRequest request, UserDetails userDetails) {
		boolean retVal = false;
		AuthUserDetails user = (AuthUserDetails) userDetails;
		// TODO URIへ画面IDが仕込まれ且つ、その画面IDで権限を参照する場合
		user.setFuncId(((HttpServletRequest) request).getRequestURI());

		String authority = "";
		Collection<GrantedAuthority> authorities = user.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			authority = grantedAuthority.getAuthority();
		}

		AuthUserDetail authUserDetail = new AuthUserDetail();
		authUserDetail.setUserId(user.getUsername());// Userクラスのプロパティ名がUsernameの為
		authUserDetail.setApproval(user.getFuncId());
		authUserDetail.setAuthCd(authority);
		// TODO 【例】 2テーブルを結合して両テーブルの項目を取得する例(userIdとkinouIdを条件として抽出)
		AuthUserDetail result = sqlSessionTemplate.selectOne(MapperConstants.M_AUTH_DETAIL, authUserDetail);

		// ------------------
		// 実行結果に基づいて、認可判定
		if (CheckUtil.isNotNull(result)) {
			// getRequestURIで指定したコンテキストが、DB抽出結果のAPPROVALと一致した場合はtrue
			if(CheckUtil.isNotNull(result.getApproval())
					&& result.getApproval().equals(user.getFuncId())){
				retVal = true;
				// TODO キー名は暫定
				request.setAttribute("sessionBean", result);
			}
		}
		return retVal;
	}

}
