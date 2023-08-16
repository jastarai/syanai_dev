package jp.jast.spframework.auth;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import jp.jast.spframework.config.SpFrameworkProperties;

public abstract class AbstractAuthFilter implements Filter {

	/**
	 * 認可判定<br>
	 * 各プロジェクトで実装
	 * 
	 * @param request リクエスト
	 * @param userDetails 認証情報
	 * @return
	 */
	protected abstract boolean isApproval(ServletRequest request, UserDetails userDetails);

	/** 認可フィルター無視パラメータ */
	private static final String IGNORE_AUTH_FILTER = "1";
	/** リクエストマッピング 認証エラー画面 */
	private static final String REQUEST_MAPPING_AHTU_ERROR = "/authError";
	@Autowired
	SpFrameworkProperties properties;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	/**
	 * フィルターメイン
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// プロパティ値で認可処理の実施有無を切り替え（デバック用）
		if (!IGNORE_AUTH_FILTER.equals(properties.getSecurity().getIgnoreAuthFilter())) {

			String uri = ((HttpServletRequest) request).getRequestURI();

			// 静的ファイル、認証エラー画面などは対象外
			if (isTarget(uri, ((HttpServletRequest) request).getContextPath())) {
				// 認可判定（各プロジェクトで実装）
				if (!isApproval(request, (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
					// 認証エラー画面にフォワード
					RequestDispatcher rd = request.getRequestDispatcher(REQUEST_MAPPING_AHTU_ERROR);
					rd.forward(request, response);
					return;
				}
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

	private boolean isTarget(String uri, String contextPath) {
		boolean retVal = true;

		StringBuilder regex = new StringBuilder();	
		regex.append(contextPath + "/css/");
		regex.append("|").append(contextPath + "/js/");
		regex.append("|").append(contextPath + "/images/");
		regex.append("|").append(contextPath + "/img/");
		regex.append("|").append(contextPath + "/custom/");
		regex.append("|").append(contextPath + "/webjars/");
		regex.append("|").append(contextPath + "/favicon.ico");
		regex.append("|").append(contextPath + "/authError");		// TODO: システムエラー画面を一時的に除外対象にする。

		// プロパティ項目「spframework.security.loginPage」の反映
		if (!StringUtils.isEmpty(properties.getSecurity().getLoginPage())) {
			regex.append("|").append(contextPath + properties.getSecurity().getLoginPage());
		}
		// プロパティ項目「spframework.security.permitAllUrl」の反映
		if (!StringUtils.isEmpty(properties.getSecurity().getPermitAllUrl())) {
			String[] permitAllUrlArray = properties.getSecurity().getPermitAllUrl().split(",");
			for (String permitAllUrl : permitAllUrlArray) {
				regex.append("|").append(contextPath + permitAllUrl);
			}
		}
		// プロパティ項目「spframework.security.failureUrl」の反映
		if (!StringUtils.isEmpty(properties.getSecurity().getFailureUrl())) {
			regex.append("|").append(contextPath + properties.getSecurity().getFailureUrl());
		}

		Pattern pattern = Pattern.compile(regex.toString());
		Matcher matcher = pattern.matcher(uri);
		if (matcher.find()) retVal = false;
		return retVal;
	}
}
