package jp.microad.blade.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.Var;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.commons.lang.StringUtils;

/**
 * validator-rules.xml規則によってのvalidator検証用のクラス。
 * 
 * プログラムの中直接に以下のmethodを呼び出すのは許可しない。
 */
@SuppressWarnings("serial")
public class AdvFieldChecks implements Serializable {

	/**
	 * 
	 * 入力文字が必須入力をチェックする。
	 * 
	 * @return チェック結果
	 */
	public static boolean validateRequired(Object bean, Field field) {
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

		return !GenericValidator.isBlankOrNull(value);
	}

	/**
	 * 
	 * 関連性必須入力をチェックする。
	 * 
	 * @return チェック結果
	 */
	public static boolean relevantValidateRequired(Object bean, Field field) {
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

		// 関連性を取得する
		boolean res = validateCorrelation(bean, field);

		if (res) {
			return !GenericValidator.isBlankOrNull(value);
		} else {
			return true;
		}
	}

	/**
	 * 
	 * 開始日は終了日以降の日付をチェックする。
	 * 
	 * @return チェック結果
	 */
	public static boolean validateGreaterThanEndDate(Object bean, Field field) {

		// 終了日を取得する
		String endDate = getValue(bean, field);

		// 開始日を取得する
		String startDate = getAssignValue(bean, field, "startDate");

		if (!"".equals(startDate) && !"".endsWith(endDate)
				&& Integer.parseInt(startDate.replaceAll("/", "")) > Integer.parseInt(endDate.replaceAll("/", ""))) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * 入力文字が最大サイズをチェックする。
	 * 
	 * @return チェック結果
	 */
	public static boolean validateMaxLength(Object bean, Field field) {

		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

		Var var = field.getVar("maxlength");

		int max = Integer.parseInt(var.getValue());

		if (value != null) {

			return GenericValidator.maxLength(value, max);
		}

		return true;
	}

	/**
	 * 
	 * 入力文字が最小値をチェックする。
	 * 
	 * @return チェック結果
	 */
	public static boolean validateMinValue(Object bean, Field field) {

		// 入力文字を取得する
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

		Var var = field.getVar("minvalue");

		int min = Integer.parseInt(var.getValue());

		if (value != null) {

			return GenericValidator.minValue(Long.parseLong(value), min);
		}

		return true;
	}

	/**
	 * 
	 * 入力文字が最大値をチェックする。
	 * 
	 * @return チェック結果
	 */
	public static boolean validateMaxValue(Object bean, Field field) {

		// 入力文字を取得する
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

		Var var = field.getVar("maxvalue");

		Long max = Long.parseLong(var.getValue());

		if (value != null) {

			return GenericValidator.maxValue(Long.parseLong(value), max);
		}

		return true;
	}

	/**
	 * 
	 * 入力文字が規定サイズをチェックする。
	 * 
	 * @return チェック結果
	 */
	public static boolean validateLength(Object bean, Field field) {

		// 入力文字を取得する
		String value = getValue(bean, field);

		Var var = field.getVar("length");

		int length = Integer.parseInt(var.getValue());

		if (value.length() == length) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * 入力文字がサイズ範囲をチェックする。
	 * 
	 * @return チェック結果
	 */
	public static boolean validateRange(Object bean, Field field) {

		// 入力文字を取得する
		String value = getValue(bean, field);

		String var = field.getVar("range").getValue();

		int minLength = Integer.parseInt(var.substring(0, 1));
		int maxLength = Integer.parseInt(var.substring(var.indexOf('-') + 1, var.length()));

		if (value.length() <= maxLength && value.length() >= minLength) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * 入力文字が値範囲をチェックする。
	 * 
	 * @return チェック結果
	 */
	public static boolean validateValueRange(Object bean, Field field) {

		// 入力文字を取得する
		String value = getValue(bean, field);

		String var = field.getVar("valuerange").getValue();

		int min = Integer.parseInt(var.substring(0, var.indexOf('-')));
		int max = Integer.parseInt(var.substring(var.indexOf('-') + 1, var.length()));

		if (ValidateFunctions.valueRange(Integer.parseInt(value), min, max)) {

			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * Float型をチェックする。
	 * 
	 * @return チェック結果
	 */
	public static boolean validateFloat(Object bean, Field field) {

		// 入力文字を取得する
		String value = getValue(bean, field);

		if (null == value || "".equals(value)) {
			return true;
		}

		String intsize = field.getVar("intsize").getValue();
		String decsize = field.getVar("decsize").getValue();

		if (!ValidateFunctions.FloatCheck(value, intsize, decsize)) {

			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * 金額をチェックする。
	 * 
	 * @return チェック結果
	 */
	public static boolean validatePrice(Object bean, Field field) {

		// 入力文字を取得する
		String value = getValue(bean, field);

		if (!ValidateFunctions.PriceCheck(value)) {

			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * 入力文字が全角であるかチェックする。
	 * 
	 * @return チェック結果
	 */
	public static boolean validateFullsizeCharacter(Object bean, Field field) {

		// 入力文字を取得する
		String value = getValue(bean, field);

		if (!ValidateFunctions.fullsizeCharacter(value)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * 入力文字が全角カタカナと「ー」であるかチェックする。
	 * 
	 * @return チェック結果
	 */
	public static boolean fullsizekanaCharacterandhyphen(Object bean, Field field) {

		// 入力文字を取得する
		String value = getValue(bean, field);

		if (!ValidateFunctions.fullsizekanaCharacterandhyphen(value)) {

			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * 入力文字が半角カナであるかチェックする。
	 * 
	 * @return チェック結果
	 */
	public static boolean validateHalfkanaCharacter(Object bean, Field field) {

		// 入力文字を取得する
		String value = getValue(bean, field);

		if (ValidateFunctions.halfkanaCharacter(value)) {

			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * 入力文字が半角英数字であるかチェックする。
	 * 
	 * @return チェック結果
	 */
	public static boolean numericalalphabeticCharacter(Object bean, Field field) {

		// 入力文字を取得する
		String value = getValue(bean, field);

		if (!ValidateFunctions.numericalalphabeticCharacter(value)) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 
	 * 入力文字が半角数字であるかチェックする。
	 * 
	 * @return チェック結果
	 */
	public static boolean validateNumericalCharacter(Object bean, Field field) {

		// 入力文字を取得する
		String value = getValue(bean, field);

		if (!ValidateFunctions.numericalCharacter(value)) {

			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * 入力文字が(─-╂Ⅰ-ⅹ①-⑳㈱-㉃㍉-㎡)特殊文字列をチェックする。
	 * 
	 * @return チェック結果
	 */
	public static boolean validateSpecialCharacter(Object bean, Field field) {

		// 入力文字を取得する
		String value = getValue(bean, field);
		
		if(value ==null || "".equals(value)){
			return true;
		}

		if (!ValidateFunctions.specialCharacter(value)) {

			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * 4バイト文字をチェックする。
	 * 
	 * @return チェック結果
	 */
	public static boolean validateFourBytesCharacter(Object bean, Field field) {

		// 入力文字を取得する
		String value = getValue(bean, field);
		
		if (StringUtils.isEmpty(value)) {
			return true;
		}

		if (ValidateFunctions.isContain4BytesCharacter(value)) {
			return false;
		}
		
		return true;
	}

	/**
	 * 
	 * 入力文字が([ ])特殊文字列であるかチェックする。
	 * 
	 * @return チェック結果
	 */
	public static boolean validateSpecialUse(Object bean, Field field) {

		String var = field.getVar("specialuse").getValue();
		// 入力文字を取得する
		String value = getValue(bean, field);

		if (!ValidateFunctions.specialUse(value, var)) {

			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * 入力文字がメールであるかチェックする。
	 * 
	 * @return チェック結果
	 */
	public static boolean validateEmail(Object bean, Field field) {

		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		if (value != null) {
			return ValidateFunctions.validateEmail(value);
		}
		return true;
	}

	/**
	 * 
	 * 小数点が含まれていたの場合をチェックする。
	 * 
	 * @return チェック結果
	 */
	public static boolean validatePoint(Object bean, Field field) {

		// 入力文字を取得する
		String value = getValue(bean, field);

		if (!ValidateFunctions.isPoint(value)) {

			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * 入力文字の先頭が0の場合をチェックする。
	 * 
	 * @return チェック結果
	 */
	public static boolean validateLeadingZero(Object bean, Field field) {

		// 入力文字を取得する
		String value = getValue(bean, field);

		if (ValidateFunctions.leadingZero(value)) {

			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * 入力文字を取得する。
	 * 
	 * @return チェック結果
	 */
	private static String getValue(Object bean, Field field) {

		String value = null;
		if (bean == null || String.class.isInstance(bean)) {
			value = (String) bean;
		} else {
			value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		}

		return value;

	}

	/**
	 * 
	 * 指定項目内容を取得する。
	 * 
	 * @return チェック結果
	 */
	private static String getAssignValue(Object bean, Field field, String name) {

		String var = ValidatorUtils.getValueAsString(bean, field.getVarValue(name));

		return var;

	}

	/**
	 * 
	 * 関連性をチェックする。
	 * 
	 * @return チェック結果
	 */
	private static boolean validateCorrelation(Object bean, Field field) {

		String type_name = getAssignValue(bean, field, "relevanttype");

		String value = field.getVarValue("relevantvalue");

		if (type_name.equals(value)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * 入力文字が半角数字と小数点であるかチェックする。
	 * 
	 * @return チェック結果
	 */
	public static boolean validateNumericalWithPoint(Object bean, Field field) {

		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

		if (null == value) {
			return true;
		}

		boolean checkFlag = GenericValidator.matchRegexp(value, "^[0-9|.]*$");

		if (checkFlag) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 
	 * 指定された日付は本日より前にあるかどうかを判定。
	 * 
	 * @return チェック結果
	 */
	public static boolean validateBeforeToday(Object bean, Field field) throws Exception {

		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

		if (null == value || "".equals(value)) {
			return true;
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

		String today = format.format(new Date());

		if (format.parse(value).before(format.parse(today))) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * 指定された日付は本日より後にあるかどうかを判定。
	 * 
	 * @return チェック結果
	 */
	public static boolean validateAfterToday(Object bean, Field field) throws Exception {

		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

		if (null == value || "".equals(value)) {
			return true;
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

		String today = format.format(new Date());

		if (format.parse(value).after(format.parse(today))) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 開始日と終了日を比較する。
	 * 
	 * @return チェック結果
	 */
	public static boolean validateDateCompare(Object bean, Field field) {

		// 開始日を取得する
		String startDate = getAssignValue(bean, field, "startdate");

		String endDate = getAssignValue(bean, field, "enddate");

		if (null == startDate || null == endDate) {
			return true;
		}

		if (!"".equals(startDate) && !"".endsWith(endDate)
				&& Integer.parseInt(startDate.replaceAll("/", "")) > Integer.parseInt(endDate.replaceAll("/", ""))) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 
	 * 半角カンマが含まれていたの場合をチェックする。
	 * 
	 * @return チェック結果
	 */
	public static boolean validateComma(Object bean, Field field) {

		// 入力文字を取得する
		String value = getValue(bean, field);

		if (ValidateFunctions.isContainComma(value)) {
			return false;
		} else {
			return true;
		}
	}

}
