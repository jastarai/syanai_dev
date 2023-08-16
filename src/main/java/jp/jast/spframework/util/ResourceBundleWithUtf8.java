package jp.jast.spframework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class ResourceBundleWithUtf8 {

	private ResourceBundleWithUtf8() {
		// コンストラクタを隠蔽します
	}
	
	public static ResourceBundle.Control UTF8_ENCODING_CONTROL = new ResourceBundle.Control() { 
	       /** 
	        * UTF-8 エンコーディングのプロパティファイルから ResourceBundle オブジェクトを生成します。 
	        * @throws IllegalAccessException 
	        * @throws InstantiationException 
	        * @throws IOException 
	        */ 
	       @Override 
	       public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) 
	               throws IllegalAccessException, InstantiationException, IOException { 
	           String bundleName = toBundleName(baseName, locale); 
	           String resourceName = toResourceName(bundleName, "properties"); 


	           try (InputStream is = loader.getResourceAsStream(resourceName); 
	                InputStreamReader isr = new InputStreamReader(is, "UTF-8"); 
	                BufferedReader reader = new BufferedReader(isr)) { 
	               return new PropertyResourceBundle(reader); 
	           } 
	       } 
	   }; 
	
}
