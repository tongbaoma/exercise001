package jp.microad.blade.batch.kizasi.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;



/**
 * ストリング操作のユーティリティクラス。
 */
public class StringUtil {

	/**
	 * 文字列の半角スペース、全角空白をトリムする。
	 *
	 * @param param 対象値
	 * @return トリム後の値
	 */
	public static String trim(String param) {

		if (param == null) {
			return null;
		}

		param = param.replaceAll("^[\\s　]+|[\\s　]+$", "");

		return param;
	}

	/**
	 * 文字列の前後タブ、半角スペース、全角空白、制御文字をトリムする。
	 *
	 * @param param 対象値
	 * @return トリム後の値
	 */
	public static String trimSpecialString(String param) {

		if (StringUtils.isEmpty(param)) {
			return "";
		}
		// 文字列の制御文字をトリムする
		Pattern patternOne = Pattern.compile("([\\x00-\\x1F\\x7F])");
		Matcher matcherOne = patternOne.matcher(param);
		param = matcherOne.replaceAll("");
		// 文字列の前後タブ、半角スペース、全角空白をトリムする
		Pattern patternTwo = Pattern.compile("(^[\\s|\u3000]*)|([\\s|\u3000]*$)|(\r|\n)");
		Matcher matcherTwo = patternTwo.matcher(param);
		param = matcherTwo.replaceAll("");

		return param;
	}

	/**
	 * 文字列をバイト配列に変換する。
	 *
	 * @param value 対象値
	 * @return 文字列のバイト配列
	 */
	public static byte[] getByteArrayFromString(String value) {

		if (null == value) {
			return new byte[0];
		}
		try {
			return value.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			return new byte[0];
		}

	}

	/**
	 * BigDecimal型の対象値を文字列に変換する。
	 *
	 * @param value 対象値
	 * @return 変換後の値
	 */
	public static String valueOf(BigDecimal value) {
		if (null == value) {
			return "";
		}
		return value.toPlainString();
	}

	/**
	 * Long型の対象値を文字列に変換する。
	 *
	 * @param value 対象値
	 * @return 変換後の値
	 */
	public static String valueOf(Long value) {
		if (null == value) {
			return "";
		}
		return value.toString();
	}

	/**
	 * Integer型の対象値を文字列に変換する。
	 *
	 * @param value 対象値
	 * @return 変換後の値
	 */
	public static String valueOf(Integer value) {
		if (null == value) {
			return "";
		}
		return value.toString();
	}

	/**
	 * バイト配列型の対象値を文字列に変換する。
	 *
	 * @param value 対象値
	 * @return 変換後の値
	 */
	public static String valueOf(byte[] value) {
		if (null == value) {
			return "";
		}
		try {
			return new String(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * Date型の日付をyyyy/MM/dd形式の文字列に変換する。
	 *
	 * @param value Date型の日付
	 * @return 変換後の値
	 */
	public static String valueOf(Date value) {
		if (null == value) {
			return "";
		}
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		return format.format(value);
	}

	/**
	 * Date型の日付をyyyy/MM/dd HH:mm:ss形式の文字列に変換する。
	 *
	 * @param　value Date型の日付
	 * @return 変換後の値
	 */
	public static String valueOfDateTime(Date value) {
		if (null == value) {
			return "";
		}
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return format.format(value);
	}

	/**
	 * Date型の日付をカスタマ文字列の形式に変換する。
	 *
	 * @param value Date型の日付
	 * @param formatType カスタマの日付形式
	 * @return 変換後の値
	 */
	public static String valueOf(Date value, String formatType) {
		if (null == value) {
			return "";
		}
		DateFormat formatter = new SimpleDateFormat(formatType);
		return formatter.format(value);
	}

	/**
	 * タブとエンターを削除。
	 *
	 * @param　value 対象値
	 * @return 削除後の値
	 */
	public static String removeTabAndEnter(String value) {
		if (value != null) {
			value = value.replaceAll("[\\t\\r\\n]", "");
		}
		return value;
	}

	/**
	 * タブを削除。
	 *
	 * @param　value 対象値
	 * @return 削除後の値
	 */
	public static String removeTab(String value) {
		if (value != null) {
			value = value.replaceAll("\\t", "");
		}
		return value;
	}

	/**
	 * CRをLFに変更する。
	 *
	 * @param　value 対象値
	 * @return 変更後の値
	 */
	public static String replaceCRToLF(String value) {
		if (value != null) {
			value = value.replaceAll("\r", "\n");
		}
		return value;
	}


	/**
	 * バイト数で文字列を取得。
	 *
	 * @param str 文字
	 * @param maxbytes 最大のバイトの数
	 * @return 文字列
	 */
	public static String getStringByMaxBytes(String str, int maxbytes) {
		if (str == null) {
			return "";
		}
		String reStr = "";
		int length = str.length();
		for (int i = 0; i < length; i++) {
			reStr = str.substring(0, i+1);
			byte[] bytes = reStr.getBytes();

			if (bytes.length == maxbytes) {
				return reStr;
			}

			if (bytes.length > maxbytes) {
				return str.substring(0, i);
			}
		}
		return reStr;
	}

	/**
	 * エスケープキャラクタ（パーセント記号）
	 *
	 * @param str 文字
	 * @return 文字列
	 */
	public static String getEscapePercentSign(String str) {
		if (StringUtils.isEmpty(str)) {
			return str;
		}
		if (str.contains("%")) {
			str = str.replaceAll("%", "\\\\%");
		}
		return str;
	}
}
