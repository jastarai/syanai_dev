package jp.co.fukuya_k.traning;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jp.co.fukuya_k.db.generator.Emp;
import jp.jast.spframework.dao.DaoUtil;

/**
 * 社員検索サービスクラス.
 * 
 * @author 日本システム技術株式会社
 *
 */
@Service
public class TraningPg01Service {
	@Autowired
	private DaoUtil daoUtil;

	/**
	 * 社員一覧検索処理
	 * 
	 * @param form 社員検索画面フォーム
	 * @return
	 * @throws Exception
	 */
	public TraningPg01Form search(TraningPg01Form form) throws Exception {

		// 検索条件の設定.
		Emp emp = new Emp();
		emp.setEmpno(form.getEmpno());

		// 社員検索の実行
		List<Emp> resultEmpData = daoUtil.selectList("select001", emp);

		// 社員一覧情報フォームの設定
		List<TraningPg01FormEmp> empList = new ArrayList<TraningPg01FormEmp>();
		for (Emp tmpEmpData : resultEmpData) {

			TraningPg01FormEmp traningPg01FormEmp = new TraningPg01FormEmp();

			traningPg01FormEmp.setEmpno(tmpEmpData.getEmpno());
			traningPg01FormEmp.setEname(tmpEmpData.getEname());
			empList.add(traningPg01FormEmp);
		}

		// 社員一覧情報フォームを社員検索画面フォームに設定
		form.setListRow(empList);

		return form;
	}

}
