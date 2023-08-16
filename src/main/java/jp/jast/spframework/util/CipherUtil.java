package jp.jast.spframework.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

@Component
public class CipherUtil {

	private final static String ALGORITHM_AES = "AES";

	/**
	 * AES暗号化してBase64した文字列で返す
	 * 
	 * @param	originalSource 	暗号化対象の平文の文字列
	 * @param	secretKey 		暗号化キー文字列（※長さ128ビットである制限あり）
	 * @return	String			暗号化後の文字列
	 * @throws Exception
	 */
	public static String encrypt(final String originalSource, final String secretKey) throws Exception {

		return new String(Base64.getEncoder().encode(cipher(Cipher.ENCRYPT_MODE, originalSource.getBytes(), secretKey)),
				StandardCharsets.UTF_8);
	}

	/**
	 * Base64されたAES暗号化文字列を元の文字列に復元する
	 * 
	 * @param	originalSource 	復号化対象の文字列
	 * @param	secretKey 		暗号化キー文字列（※長さ128ビットである制限あり）
	 * @return	String			復号化後の文字列 
	 * @throws Exception
	 */
	public static String decrypt(final String encryptBytesBase64String, final String secretKey) throws Exception {

		return new String(cipher(Cipher.DECRYPT_MODE, Base64.getDecoder().decode(encryptBytesBase64String), secretKey),
				StandardCharsets.UTF_8);
	}

	/**
	 * 暗号化/複合化の共通処理
	 * 
	 * @param	mode			モード（Cipher.ENCRYPT_MODE:暗号化、Cipher.DECRYPT_MODE:復号化）
	 * @param	byte[]			処理対象文字列のバイト配列
	 * @param	secretKey 		暗号化キー文字列（※長さ128ビットである制限あり）
	 * @return	String			復号化後の文字列  
	 * @throws Exception
	 */
	private static byte[] cipher(final int mode, final byte[] source, final String secretKey) throws Exception {

		Cipher cipher = Cipher.getInstance(ALGORITHM_AES);
		cipher.init(mode, generateKey(secretKey));
		return cipher.doFinal(source);
	}

	/**
	 * 秘密鍵生成
	 * 
	 * @param	secretKey		暗号化キー文字列（※長さ128ビットである制限あり）
	 * @return	Key				秘密鍵
	 * @throws Exception
	 */
	private static Key generateKey(final String secretKey) throws Exception {
		Key key = new SecretKeySpec(secretKey.getBytes(), ALGORITHM_AES);
		return key;
	}

}
