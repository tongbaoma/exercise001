package jp.microad.blade.batch.kizasi.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;


/**
 * ナンバー操作のユーティリティクラス。
 */
public class NumberUtil {

	/**
	 * Long型の対象値をBigDecimal型に変換する。
	 *
	 * @param value Long型の対象値
	 * @return 変換後の値
	 */
	public static BigDecimal getBigDecimal(Long value) {

		if (value == null) {
			return BigDecimal.ZERO;
		}
		return new BigDecimal(value.longValue());

	}

	/**
	 * BigDecimal型の対象値を除算する。
	 *
	 * @param dividend 除算される対象値
	 * @param divisor 除算する対象値
	 * @return 除算の結果
	 */
	public static BigDecimal getdivideResult(BigDecimal dividend, BigDecimal divisor) {
		if (dividend == null || divisor == null || (divisor.compareTo(BigDecimal.ZERO) == 0)) {
			return BigDecimal.ZERO;
		}
		return dividend.divide(divisor, 8, BigDecimal.ROUND_FLOOR);
	}

	/**
	 * Long型の対象値を除算する。
	 *
	 * @param dividend 除算される対象値
	 * @param divisor 除算する対象値
	 * @return 除算の結果
	 */
	public static BigDecimal getdivideResult(Long dividend, Long divisor) {
		BigDecimal bDividend = getBigDecimal(dividend);
		BigDecimal bDivisor = getBigDecimal(divisor);
		return getdivideResult(bDividend, bDivisor);
	}

	/**
	 * BigDecimal型とLong型の対象値の除算。
	 *
	 * @param dividend 除算される対象値
	 * @param divisor 除算する対象値
	 * @return 除算の結果
	 */
	public static BigDecimal getdivideResult(BigDecimal dividend, Long divisor) {
		BigDecimal bDivisor = getBigDecimal(divisor);
		return getdivideResult(dividend, bDivisor);
	}

	/**
	 * BigDecimal型の対象値を加算する。
	 *
	 * @param addend 加算する値
	 * @param augend 加算する値
	 * @return 加算の結果
	 */
	public static BigDecimal getAddResult(BigDecimal addend, BigDecimal augend) {
		if(addend == null){
			addend = BigDecimal.ZERO;
		}
		if (augend == null) {
			augend = BigDecimal.ZERO;
		}
		return addend.add(augend).divide(new BigDecimal(1), 8, BigDecimal.ROUND_FLOOR);
	}

	/**
	 * BigDecimal型とLong型の対象値の加算。
	 *
	 * @param addend 加算する値
	 * @param augend 加算する値
	 * @return 加算の結果
	 */
	public static BigDecimal getAddResult(BigDecimal addend, Long augend) {
		if(addend == null){
			addend = BigDecimal.ZERO;
		}

		BigDecimal bAugend = getBigDecimal(augend);
		return addend.add(bAugend).divide(new BigDecimal(1), 8, BigDecimal.ROUND_FLOOR);
	}

	/**
	 * String型の対象値をBigDecimal型に変換する。
	 *
	 * @param value String型の対象値
	 * @return 変換後の値
	 */
	public static BigDecimal decimalValueOf(String value) {
		if (StringUtils.isEmpty(value)) {
			return BigDecimal.ZERO;
		}
		return new BigDecimal(value);
	}

	/**
	 * String型の対象値をInteger型に変換する。
	 *
	 * @param value String型の対象値
	 * @return 変換後の値
	 */
	public static Integer integerValueOf(String value) {
		if (StringUtils.isEmpty(value)) {
			return 0;
		}
		return Integer.valueOf(value);
	}

	/**
	 * String型の対象値をLong型に変換する。
	 *
	 * @param value String型の対象値
	 * @return 変換後の値
	 */
	public static Long longValueOf(String value) {
		if (StringUtils.isEmpty(value)) {
			return 0L;
		}
		return Long.valueOf(value);
	}

	/**
	 * 金額のフォーマット。
	 *
	 * ("###.##")の形式でフォーマットする。
	 * 小数点以下3桁目を切捨て。
	 *
	 * @param value 対象値
	 * @return フォーマット後の値
	 */
	public static String currencyFormat(BigDecimal value) {
		if (value == null) {
			return "0";
		}

		DecimalFormat format = new DecimalFormat("##0.00");
		format.setRoundingMode(RoundingMode.DOWN);
		BigDecimal tempValue = new BigDecimal(format.format(value));

		if (tempValue.compareTo(new BigDecimal(tempValue.intValue())) > 0) {
			format = new DecimalFormat("##0.00");
		} else {
			format = new DecimalFormat("##0.##");
		}

		return format.format(tempValue);
	}

	/**
	 * 金額のフォーマット。
	 *
	 * ("###")の形式でフォーマットする。
	 * 小数点以下を切捨て。
	 *
	 * @param value 対象値
	 * @return フォーマット後の値
	 */
	public static String roundCurrencyFormat(BigDecimal value) {
		if (value == null) {
			return "0";
		}

		DecimalFormat format = new DecimalFormat("##0");
		format.setRoundingMode(RoundingMode.DOWN);

		return format.format(value);
	}

	/**
	 * パーセントのフォーマット。
	 *
	 * ("###.##%")の形式でフォーマットする。
	 * 小数点以下3桁目を切捨て。
	 *
	 * @param value 対象値
	 * @return フォーマット後の値
	 */
	public static String percentFormat(BigDecimal value) {
		if (value == null) {
			return "0%";
		}

		DecimalFormat format = new DecimalFormat("##0.00");
		format.setRoundingMode(RoundingMode.DOWN);
		BigDecimal tempValue = new BigDecimal(format.format(value));

		if (tempValue.compareTo(new BigDecimal(tempValue.intValue())) > 0) {
			format = new DecimalFormat("##0.00");
		} else {
			format = new DecimalFormat("##0.##");
		}

		return format.format(tempValue).concat("%");
	}

	/**
	 * BigDecimal型の対象値をInteger型にフォーマットする。
	 *
	 * ("###")の形式でフォーマットする。
	 * 小数の部分を捨てる。
	 *
	 * @param value 対象値
	 * @return フォーマット後の値
	 */
	public static String integerFormat(BigDecimal value) {

		if (value == null) {
			return "0";
		}
		DecimalFormat format = new DecimalFormat("###");
		return format.format(value);
	}

	/**
	 * BigDecimal型の対象値がNULLの場合0に設定する。
	 *
	 * @param value 対象値
	 * @return NULLの場合0を返す
	 */
	public static BigDecimal estimateNullBigDecimal(BigDecimal value) {
		BigDecimal returnValue = null;
		if (value == null) {
			returnValue = BigDecimal.ZERO;
		} else {
			returnValue = value;
		}
		return returnValue;
	}

	/**
	 * バイト配列型の対象値を文字列に変換する。
	 *
	 * @param value
	 *            対象値
	 * @return 変換後の値 NULLの場合0を返す
	 */
	public static Long valueOf(byte[] value) {
		if(null == value) {
			return 0L;
		}
		try {
			return new BigInteger(value).longValue();
		} catch(NumberFormatException e) {
			return 0L;
		}
	}
}
