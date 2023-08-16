package jp.co.fukuya_k.traning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 社員検索画面コントローラクラス
 * 
 * @author 日本システム技術株式会社
 */
@Controller
public class TraningPg01Controller {
	
	@Autowired
	TraningPg01Service traningPg01Sevice;

	/**
	 * 社員検索画面初期表示メソッド
	 * 
	 * @param form 社員検索画面フォーム
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/traning/traning_pg1/init001", method = RequestMethod.GET)
	public String init001_get(@ModelAttribute("mainForm") TraningPg01Form form) {
		return "traning/traning_pg1";
	}
	
	/**
	 * 社員検索画面検索メソッド
	 * 
	 * @param form 社員検索画面フォーム
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/traning/traning_pg1/search001", method = RequestMethod.POST)
	public String search001_post(@ModelAttribute("mainForm") TraningPg01Form form) throws Exception {
		
		// 社員検索実行
		form = traningPg01Sevice.search(form);
		return "traning/traning_pg1";
	}
	
}
