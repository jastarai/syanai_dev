package jp.jast.spbtfw.test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import jp.Application;
import jp.co.fukuya_k.db.generator.TBlobFile;
import jp.co.fukuya_k.db.generator.TUserRecord;
import jp.co.fukuya_k.web.common.auth.AuthUserDetails;
import jp.co.fukuya_k.web.common.constants.MapperConstants;
import jp.jast.spbtfw.dao.DaoUtil;
import jp.jast.spbtfw.dao.ParamBinder;
import jp.jast.spbtfw.entity.CommonEntityInterface;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class DaoUtilSelectTests {
	@Autowired
	WebApplicationContext appContext;
	@Autowired
	DaoUtil daoUtil;
	
	MockMvc mockMvc;


	@Before
	public void setup(){
		mockMvc = MockMvcBuilders.webAppContextSetup(appContext)
				.build();
		// DaoUtilでUserIdを読む際に使用
		List<GrantedAuthority>authorityList = new ArrayList<>();

		authorityList.add(new SimpleGrantedAuthority("010001"));
		final AuthUserDetails userDetails = new AuthUserDetails("testUser01", "pass", authorityList);
		final Authentication authData = new UsernamePasswordAuthenticationToken(userDetails, "pass", authorityList);
		SecurityContextHolder.getContext().setAuthentication(authData);
	}
	
	@Test
	@Transactional
	public void daoUtilInsertSelect() throws Exception {

		/*
		 * 準備
		 * 最終的にSELECT結果と突き合わせるデータを最初に登録する
		 */
		int maxSeq = daoUtil.getOneItemResult(MapperConstants.SQLID_MAX001, new TUserRecord());
		TUserRecord testData = new TUserRecord();
		testData.setUserId("user0001");
		testData.setSeq(maxSeq + 1);
		testData.setStrText("Test文字列");
		testData.setNumberText(123456789000L);
		testData.setAmount(999999L);
		testData.setReguserid("SUSER1");
		testData.setUpduserid("SUSER2");

		/*
		 * テスト用データをDBへ登録
		 */
		boolean ret = daoUtil.insert(testData);
		assertThat(ret, is(true));

		/*
		 * 抽出結果をテスト
		 */
		TUserRecord result = (TUserRecord)daoUtil.selectOne(MapperConstants.SQLID_SELECT003, testData);
		// DB抽出結果(3項目)の同値確認
		assertThat(result.getUserId(), is(testData.getUserId()));
		assertThat(result.getSeq(), is(testData.getSeq()));
		assertThat(result.getStrText(), is(testData.getStrText()));
		assertThat(result.getNumberText(), is(testData.getNumberText()));
		assertThat(result.getAmount(), is(testData.getAmount()));
		assertThat(result.getStrText(), is(testData.getStrText()));
		assertThat(result.getReguserid(), is(testData.getReguserid()));
		assertThat(result.getUpduserid(), is(testData.getUpduserid()));

		List<CommonEntityInterface> resList = daoUtil.select(MapperConstants.SQLID_SELECT002, new TUserRecord());
		assertThat(resList.get(0), instanceOf(TUserRecord.class));

		/*
		 * 複数件の同時INSERTをテスト
		 */
		// T_BLOB_FILEテーブル
		maxSeq = daoUtil.getOneItemResult(MapperConstants.SQLID_MAX001, new TBlobFile());
		List<CommonEntityInterface> entityList = new ArrayList<CommonEntityInterface>();
		entityList.add(createData(maxSeq, "TestPath9999", "TestFileName9999", "SUSER99", "SUSER999"));
		maxSeq = maxSeq+1;
		entityList.add(createData(maxSeq, "TestPath9998", "TestFileName9998", "SUSER98", "SUSER998"));
		maxSeq = maxSeq+1;
		entityList.add(createData(maxSeq, "TestPath9997", "TestFileName9997", "SUSER97", "SUSER997"));
		maxSeq = maxSeq+1;
		entityList.add(createData(maxSeq, "TestPath9996", "TestFileName9996", "SUSER96", "SUSER996"));
		maxSeq = maxSeq+1;
		entityList.add(createData(maxSeq, "TestPath9995", "TestFileName9995", "SUSER95", "SUSER995"));
		ret = daoUtil.insertListForMySQL(MapperConstants.SUFFIX_INSERT_LIST, entityList, true);

		// T_USER_RECORDテーブル
		maxSeq = daoUtil.getOneItemResult(MapperConstants.SQLID_MAX001, new TUserRecord());
		entityList = new ArrayList<CommonEntityInterface>();
		entityList.add(createData2(maxSeq));
		maxSeq = maxSeq+1;
		entityList.add(createData2(maxSeq));
		maxSeq = maxSeq+1;
		entityList.add(createData2(maxSeq));
		ret = daoUtil.insertListForMySQL(MapperConstants.SUFFIX_INSERT_LIST, entityList, true);

		assertThat(ret, is(true));
	}
	
	
	private TBlobFile createData(int maxSeq, String filePath, String fileName, String regUserId, String updUserId){

		TBlobFile testData = new TBlobFile();
		testData.setSeq(maxSeq + 1);
		testData.setFilePath(filePath);
		testData.setFileName(fileName);
		testData.setFileData("testdata".getBytes());
		testData.setFileType(0);
		testData.setRegistUserId(regUserId);
		testData.setUpdateUserId(updUserId);
		
		Calendar cal = Calendar.getInstance();
		cal.set(2016, 2, 23, 13, 59, 59);
		cal.set(Calendar.MILLISECOND, 0);
		testData.setRegdate(cal.getTime());
		testData.setUpddate(cal.getTime());
		return testData;
	}
	private TUserRecord createData2(int maxSeq){
		TUserRecord testData = new TUserRecord();
		testData.setUserId("user0001");
		testData.setSeq(maxSeq + 1);
		testData.setStrText("Test文字列");
		testData.setNumberText(123456789000L);
		testData.setAmount(999999L);
		testData.setReguserid("SUSER1");
		testData.setUpduserid("SUSER2");
		
		Calendar cal = Calendar.getInstance();
		cal.set(2016, 2, 23, 13, 59, 59);
		cal.set(Calendar.MILLISECOND, 0);
		testData.setRegdate(cal.getTime());
		testData.setUpddate(cal.getTime());
		return testData;
	}
}
