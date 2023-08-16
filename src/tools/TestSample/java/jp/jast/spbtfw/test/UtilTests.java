package jp.jast.spbtfw.test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.security.Provider;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import jp.Application;
import jp.jast.spbtfw.util.TypeCastingUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class UtilTests {
	@Autowired
	WebApplicationContext appContext;
	@Test
	public void typeCastTest() throws Exception {
		// テストケースその１
		ArrayList<Format> superList1 = new ArrayList<Format>();
		NumberFormat f = NumberFormat.getInstance();
		superList1.add(f);
		List<DecimalFormat> resultList1 = TypeCastingUtil.getDownCastedTypeList(superList1, DecimalFormat.class);
		assertThat(resultList1.get(0), is(DecimalFormat.getInstance()));
//		List<DecimalFormat> resultList1 = superList1;
//		ArrayList<Number> downCasted = (ArrayList<Number>)TypeCastingUtil.getDownCastedList(intList, Number.class);


		// テストケースその２
		try{
			ArrayList<Properties> superList2 = new ArrayList<Properties>();
			superList2.add(new Properties());
			List<Provider> resultList2 = TypeCastingUtil.getDownCastedTypeList(superList2, Provider.class);
		} catch(Exception e){
			assertThat(e, instanceOf(IllegalArgumentException.class));
			e.printStackTrace();
		}
		// テストケースその３
		List<Number> superList3 = new ArrayList<Number>();
		superList3.add(new Integer(1));
		superList3.add(new Integer(2));
		
		List<Integer> intList = TypeCastingUtil.getDownCastedTypeList(superList3, Integer.class);
//		List<Integer> intList = (List<Integer>)superList3;
//		List<Integer> intList = new ArrayList<Integer>();
//		for(Number n : superList3){
//			intList.add((Integer)n);
//		}

		// テストケースその４
		List<Object> superList4 = new ArrayList<Object>();
		superList4.add(new String("aaa"));
		superList4.add(new String("bbb"));
		
		List<String> strList = TypeCastingUtil.getDownCastedTypeList(superList4, String.class);
//		List<String> strList = (List<String>)superList4;
//		List<String> strList2 = new ArrayList<String>();
//		strList2.addAll(superList4);
		
		Object o = (Object)(new String("aaa"));
		String s = (String)o;

		Number n = (Number)(new Integer(1));
		Integer i = (Integer)n;
		
		// テストケースその５
		List<Object> superList5 = new ArrayList<Object>();
		superList5.add(new AtomicLong(1));
		superList5.add(new AtomicLong(2));
		
		List<AtomicLong> byteListx = TypeCastingUtil.getDownCastedTypeList(superList5, AtomicLong.class);
		AtomicLong byteValue = byteListx.get(0);
	}
}
