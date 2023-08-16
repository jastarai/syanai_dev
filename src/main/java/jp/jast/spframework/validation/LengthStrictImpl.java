package jp.jast.spframework.validation;

import java.lang.annotation.Annotation;
import java.util.Objects;

import javax.validation.Payload;

@SuppressWarnings(value = { "all" })
public class LengthStrictImpl implements LengthStrict {

	long MIN_DEFAULT = 0;
	long MAX_DEFAULT = Long.MAX_VALUE;
	
	long overrideMin;
	long overrideMax;
	
	/**
	 * サロゲートペアを考慮した文字長チェックの引数指定<br />
	 * <br />
	 * min（最小文字数。null指定時はデフォルト値の0文字とする）<br />
	 * max（最大文字数。null指定時はデフォルト値のLong.MAX_VALUEとする）<br />
	 * ※min、maxのいずれかについて、デフォルト値を用いるためにnullを渡せるよう、<br />
	 *   プリミティブ型ではなくラッパークラス（Long）の引数としています。
	 */
	public LengthStrictImpl(Long min, Long max) {
		
		if (Objects.isNull(min)) {
			overrideMin = MIN_DEFAULT;
		} else {
			overrideMin = min.longValue();
		}
		
		if (Objects.isNull(max)) {
			overrideMax = MIN_DEFAULT;
		} else {
			overrideMax = max.MAX_VALUE;
		}

	}
	
	@Override
	public Class<? extends Annotation> annotationType() {
		return null;
	}

	@Override
	public long min() {
		return overrideMin;
	}

	@Override
	public long max() {
		return overrideMax;
	}

	@Override
	public String message() {
		return null;
	}

	@Override
	public Class<?>[] groups() {
		return null;
	}

	@Override
	public Class<? extends Payload>[] payload() {
		return null;
	}

}
