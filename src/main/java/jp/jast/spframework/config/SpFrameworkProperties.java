package jp.jast.spframework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import jp.jast.spframework.config.SpFrameworkProperties.Svf;
import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix="spframework", ignoreUnknownFields=true)
public class SpFrameworkProperties {

	@Getter
	private final Dao dao = new Dao();

	public static class Dao {
		@Getter @Setter
		private String pkgNamePrefix;
		@Getter @Setter
		private String pkgNameSufix;
		@Getter @Setter
		private String queryUdate;
		@Getter @Setter
		private String querySelect;
		@Getter @Setter
		private String querySelectForUpdate;
		@Getter @Setter
		private String queryDelete;
		@Getter @Setter
		private String queryInsert;
		@Getter @Setter
		private String columnDeleteFlag;
		@Getter @Setter
		private String defaultFunctionId;
	}

	@Getter
	private final Security security = new Security();

	public static class Security {
		/** ログインフォームのURL */
		@Getter @Setter
		private String loginPage;

		/** ログイン処理のURL */
		@Getter @Setter
		private String loginProcessingUrl;

		/** ログイン成功時の遷移先URL */
		@Getter @Setter
		private String defaultSuccessUrl;

		/** ログイン成功時URLを常に使用する設定 */
		@Getter @Setter
		private String alwaysUseDefaultSuccessUrl;

		/** ログイン失敗時の遷移先URL */
		@Getter @Setter
		private String failureUrl;

		/** ログインフォームで使用するユーザー名のinput name */
		@Getter @Setter
		private String usernameParameter;

		/** ログインフォームで使用するパスワードのinput name */
		@Getter @Setter
		private String passwordParameter;
		
		// ログアウト関係
		/** ログアウト処理のURL */
		@Getter @Setter
		private String logoutUrl;

		/** ログアウト後の遷移先URL */
		@Getter @Setter
		private String logoutSuccessUrl;

		// 認可フィルター
		/** 認可フィルター無視パラメータ */
		@Getter @Setter
		private String ignoreAuthFilter;

		@Getter @Setter
		/** アクセス制限 全権限URL */
		private String permitAllUrl;

		@Getter @Setter
		/** CSRF 無効URL */
		private String disableCsrfUrl;
	}
	
	@Getter
	private final Svf svf = new Svf();
	
	public static class Svf {
		@Getter @Setter
		private String hostName;
		@Getter @Setter
		private String vrEncode;
		@Getter @Setter
		private String vrLocale;
		@Getter @Setter
		private String urlEncode;
	}
	
	@Getter
	private final File file = new File();

	public static class File {
		@Getter @Setter
		private String physicalPath;
		@Getter @Setter
		private String urlEncode;
	}
	
}
