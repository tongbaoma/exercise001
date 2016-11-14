package jp.microad.blade.batch.kizasi.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.routines.UrlValidator;


/**
 * validation用メソッド
 */
public class ValidateUtil {

    private static final long MAX_INT = 4294967295L;
    private static final long MAX_TINY_INT = 255L;
    private static final int MAX_TINY_BLOB = 255;
    private static final int MAX_BLOB = 65535;

    /**
     * isInt
     *
     * @param intStr
     * @return
     */
    public static boolean isInt(String intStr) {
        try {
            Integer.parseInt(intStr);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * isFloat
     *
     * @param floatStr
     * @return
     */
    public static boolean isFloat(String floatStr) {
        try {
            Float.parseFloat(floatStr);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * isEmpty
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }
        return StringUtils.isEmpty(str);
    }

    /**
     * isInRange
     *
     * @param str
     * @return
     */
    public static boolean isInRange(float floatValue, float min, float max) {
        return floatValue >= min && floatValue <= max;
    }

    /**
     * validate Url format
     *
     * @param url
     * @return
     */
    public static boolean isUrlFormat(String url) {
        UrlValidator urlValidator = new UrlValidator();
        return urlValidator.isValid(url);
    }

    /**
     * Validate Int Length
     *
     * @param str
     * @return
     */
    public static boolean isValidIntLength(String str) {
        return Long.parseLong(str) <= MAX_INT && Long.parseLong(str) >= 0;
    }

    /**
     * Validate TinyInt Length
     *
     * @param str
     * @return
     */
    public static boolean isValidTinyIntLength(String str) {
        return Long.parseLong(str) <= MAX_TINY_INT && Long.parseLong(str) >= 0;
    }

    /**
     * Validate TinyBlob Length
     *
     * @param str
     * @return
     */
    public static boolean isValidTinyBlobLength(String str) {
        if (StringUtils.isEmpty(str)) {
            return true;
        }
        return str.getBytes().length <= MAX_TINY_BLOB;
    }

    /**
     * Validate Blob Length
     *
     * @param str
     * @return
     */
    public static boolean isValidBlobLength(String str) {
        if (StringUtils.isEmpty(str)) {
            return true;
        }
        return str.getBytes().length <= MAX_BLOB;
    }
    
    /**
     * 
     * 入力文字の最大サイズをチェックする。
     * 
     * @param value チェック値
     * @param max 最大サイズ値
     * @return 入力文字のサイズは引数 max より大きい場合は false
     */
    public static boolean checkMaxLength(String value, int max) {

        if (value != null) {

            return GenericValidator.maxLength(value, max);
        }

        return true;
    }

    /**
     * 
     * 入力文字が全角カタカナと「ー」であるかチェックする。
     * 
     * @param value チェック値
     * @return 全角カタカナと「ー」の場合は true
     */
    public static boolean fullsizekanaCharacterandhyphen(String value) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }
        boolean checkFlag = GenericValidator.matchRegexp(value, "^[ァ-ヶー]*$");
        return checkFlag;
    }

    /**
     * 
     * 入力文字が半角カナであるかチェックする。
     * 
     * @param value チェック値
     * @return 半角カナの場合は true
     */
    public static boolean halfkanaCharacter(String value) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }
        boolean checkFlag = GenericValidator.matchRegexp(value, "[｡-ﾟ]+");
        return checkFlag;
    }
    
    /**
     * 
     * 入力文字が半角数字であるかチェックする。
     * 
     * @param value チェック値
     * @return 半角数字の場合は true
     */
    public static boolean numericalCharacter(String value) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }
        boolean checkFlag = GenericValidator.matchRegexp(value, "^[0-9]*$");
        return checkFlag;
    }
    
    /**
     * 
     * 入力文字が(ｱ-ﾝ─-╂Ⅰ-Ⅹ①-⑳㊤-㊨㍉-㎡㌔-㌻㈱-㈹㌃㌍㏄〟〝№㏍℡≒≡∫∮∑√⊥∠∟⊿∵∩∪)特殊文字列であるかチェックする。
     * 
     * @param value チェック値
     * @return 特殊文字列の場合は true
     */
    public static boolean specialCharacter(String value) {
        if (GenericValidator.matchRegexp(value, "^[^ｱ-ﾝ─-╂Ⅰ-Ⅹ①-⑳㊤-㊨㍉-㎡㌔-㌻㈱-㈹㌃㌍㏄〟〝№㏍℡≒≡∫∮∑√⊥∠∟⊿∵∩∪]+$")){
            return true;
        }
        
        return false;
    }
    
    /**
     * 
     * ４バイト文字を含むかどうかを判定します。
     * 
     * @param value チェック値
     * @return 含む場合は true
     */
    public static boolean isContain4BytesCharacter(String value) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        
        String regex = "[𠀋𡈽𡌛𡑮𡢽𠮟𡚴𡸴𣇄𣗄𣜿𣝣𣳾𤟱𥒎𥔎𥝱𥧄𥶡𦫿𦹀𧃴𧚄𨉷𨏍𪆐𠂉𠂢𠂤𠆢𠈓𠌫𠎁𠍱𠏹𠑊𠔉𠗖𠘨𠝏𠠇𠠺"+
                           "𠢹𠥼𠦝𠫓𠬝𠵅𠷡𠺕𠹭𠹤𠽟𡈁𡉕𡉻𡉴𡋤𡋗𡋽𡌶𡍄𡏄𡑭𡗗𦰩𡙇𡜆𡝂𡧃𡱖𡴭𡵅𡵸𡵢𡶡𡶜𡶒𡶷𡷠𡸳𡼞𡽶𡿺𢅻"+
                           "𢌞𢎭𢛳𢡛𢢫𢦏𢪸𢭏𢭐𢭆𢰝𢮦𢰤𢷡𣇃𣇵𣆶𣍲𣏓𣏒𣏐𣏤𣏕𣏚𣏟𣑊𣑑𣑋𣑥𣓤𣕚𣖔𣘹𣙇𣘸𣘺𣜜𣜌𣝤𣟿𣟧𣠤𣠽𣪘"+
                           "𣱿𣴀𣵀𣷺𣷹𣷓𣽾𤂖𤄃𤇆𤇾𤎼𤘩𤚥𤢖𤩍𤭖𤭯𤰖𤴔𤸎𤸷𤹪𤺋𥁊𥁕𥄢𥆩𥇥𥇍𥈞𥉌𥐮𥓙𥖧𥞩𥞴𥧔𥫤𥫣𥫱𥮲𥱋𥱤"+
                           "𥸮𥹖𥹥𥹢𥻘𥻂𥻨𥼣𥽜𥿠𥿔𦀌𥿻𦀗𦁠𦃭𦉰𦊆𦍌𣴎𦐂𦙾𦚰𦜝𦣝𦣪𦥑𦥯𦧝𦨞𦩘𦪌𦪷𦱳𦳝𦹥𦾔𦿸𦿶𦿷𧄍𧄹𧏛𧏚"+
                           "𧏾𧐐𧑉𧘕𧘔𧘱𧚓𧜎𧜣𧝒𧦅𧪄𧮳𧮾𧯇𧲸𧶠𧸐𧾷𨂊𨂻𨊂𨋳𨐌𨑕𨕫𨗈𨗉𨛗𨛺𨥉𨥆𨥫𨦇𨦈𨦺𨦻𨨞𨨩𨩱𨩃𨪙𨫍𨫤"+
                           "𨫝𨯁𨯯𨴐𨵱𨷻𨸟𨸶𨺉𨻫𨼲𨿸𩊠𩊱𩒐𩗏𩙿𩛰𩜙𩝐𩣆𩩲𩷛𩸽𩸕𩺊𩹉𩻄𩻩𩻛𩿎𪀯𪀚𪃹𪂂𢈘𪎌𪐷𪗱𪘂𪘚𪚲]";
        
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(value);  

        if (m.find()) {
            return true;
        }
        
        return false;
    }
    
    /**
     * 
     * 入力文字が([ ])特殊文字列であるかチェックする。
     * 
     * @param value チェック値
     * @param var 特殊文字
     * @return 含まない場合は true
     */
    public static boolean specialUse(String value,String var) {
        // 罫線,ローマ数字,丸付き数字,特殊文字
        if(GenericValidator.matchRegexp(value, "^[^"+ var +"]+$")){
            return true;
        }
        
        return false;
    }
    
    /**
     * 
     * 入力文字が半角英数字であるかチェックする。
     * 
     * @param value チェック値
     * @return 半角英数字の場合は true
     */
    public static boolean numericalalphabeticCharacter(String value) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }
        boolean checkFlag = GenericValidator.matchRegexp(value,
                "^[0-9a-zA-Z]*$");
        return checkFlag;
    }
    
    /**
     * 
     * 入力文字が全角であるかチェックする。
     * 
     * @param value チェック値
     * @return 全角の場合は true
     */
    public static boolean fullsizeCharacter(String value) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }
        boolean checkFlag = GenericValidator.matchRegexp(value,
                "^[ 　]*[^ -~｡-ﾟ]*[ 　]*$");
        return checkFlag;
    }
    
    /**
     * 
     * 入力文字が値範囲であるかチェックする。
     * 
     * @param value チェック値
     * @param min 最小入力値
     * @param max 最大入力値
     * @return 範囲内の場合は true
     */
    public static boolean valueRange(int value,int min,int max) {

        if(min > Integer.valueOf(value) || Integer.valueOf(value) > max) {
            return true;
        }
        return false;
    }
    
    /**
     * 
     * 入力文字の先頭が0の場合をチェックする。
     * 
     * @param value チェック値
     * @return 先頭が0の場合は true
     */
    public static boolean leadingZero(String value) {

        if(value.length() > 1 && value.charAt(0) == '0') {
            return true;
        }
        return false;
    }
    
    /**
     * 
     * 小数点が含まれていた場合をチェックする。
     * 
     * @param value チェック値
     * @return 小数点を含む場合は true
     */
    public static boolean isPoint(String value) {

        if(GenericValidator.matchRegexp(value, "\\.")){
            return true;
        }
        return false;
    }

    /**
     * 
     * 小数桁数をチェックする。
     * 
     * @param value チェック値
     * @param size 桁数値
     * @return 引数 value の小数桁数は引数 size より小さい場合はtrue
     */
    public static boolean pointLength(String value,int size) {

        //小数点の数を取得する
        int count = 0;
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) == '.') {
                count++;
            }
        }
        if (2 <= count) {
            //"."が２つ以上入力されている場合はＮＧ
            return false;
        }
        if (value.charAt(0) == '.') {
            //先頭に小数点が入力された場合はＮＧ
            return false;
        }
        //小数点以下のチェック
        if (count == 1) {
            //小数点が入力された場合のみチェック
            //小数点以下の桁数チェック
            int idx = value.lastIndexOf(".");
            String decimalPart = value.substring(idx);
            //小数点以下の桁数を取得する
            int length = decimalPart.length() - 1;
            if (length == 0) {
                //小数点以下の入力がない場合はＮＧ
                return false;
            }
            if (size < length) {
                //小数点以下の桁数がオーバーしている場合はＮＧ
                return false;
            }
        }
        return true;
    }
    
    /**
     * 
     * 入力文字が、指定された Float 型の書式と一致するかどうかを判定します。
     * 
     * @param value チェック値
     * @param intsize 整数部分の桁数
     * @param decsize 小数部分の桁数
     * @return 引数 value は Float 型で、整数部分の桁数が引数 intsize と等しくて、小数部分の桁数が引数 decsize と等しい場合だけ true
     */
    public static boolean FloatCheck(String value,String intsize,String decsize) {
        // 罫線,ローマ数字,丸付き数字,特殊文字
        if(GenericValidator.matchRegexp(value, "^([0-9]{1,"+ intsize +"})(\\.[0-9]{1,"+ decsize +"})?$")){
            return true;
        }
        
        return false;
    }
    
    /**
     * 
     * 入力文字の整数部分の桁数は９より小さいかどうかを判定します。
     * 
     * @param value チェック値
     * @return 整数部分の桁数は９より小さい場合は true
     */
    public static boolean PriceCheck(String value) {

        
        //自然数のチェック（桁数チェック、９桁まで）
        int idx = value.lastIndexOf(".");
        if (idx < 0) {
            //小数点がない場合はそのまま
            idx = value.length();
        }
        String decimalPart = value.substring(0, idx);
        int length = decimalPart.length();
        if (length == 0) {
            return false;
        }
        if (length > 9) {
            return false;
        }
        return true;
    }
    
    /**
     *
     * 入力された文字列と「http://」後の文字列のフォーマット一致するかどうかを判定する。
     * 
     * @param value チェック値
     * @return フォーマット一致する場合は true
     */
    public static boolean adurlCharacter(String value) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }
        String adurl = "http://" + value; 
        
        if(!urlFormatCheck(adurl)){
            return false;               
        }

        return true;
        
    }
    
    /**
     * URLの形式を確認
     * 
     * @param value チェック値
     * @return フォーマット一致する場合は true
     */
    public static boolean urlFormatCheck(String value) {

        if (StringUtils.isBlank(value)) {
            return true;
        }

        Pattern urlPpattern = Pattern.compile("^(http|https|market)://[\\x00-\\x7F]*$");
        Matcher urlMatcher = urlPpattern.matcher(value);
        if (!urlMatcher.find()) {
            return false;
        }

        return true;
    }
    
    /**
     * URLの開始文字列形式を確認
     * 
     * @param value チェック値
     * @return 開始文字列がhttp://あるいはhttps://の場合は true
     */
    public static boolean adurlCharacterNotContainsProtocol(String value){
        if (StringUtils.isEmpty(value)) {
            return true;
        }
        if(value.startsWith("http://")||value.startsWith("https://")){
            return false;   
        }
        return true;
    }

    /**
     * URLが'/'を含むかの確認
     * 
     * @param value チェック値
     * @return  '/'を含まない場合は true
     */
    public static boolean adurlCharacterNotContainsPath(String value){
        if (StringUtils.isEmpty(value)) {
            return true;
        }
        return (value.indexOf('/')==-1);
    }
    
    /**
     * 有効日付のフォーマットタイプ検証
     * 
     * @param date 日付
     * @param formatString フォーマットタイプ
     * @return チェック結果
     */
    public static boolean checkDateFormat(String date, String formatString) {

        if (StringUtils.isEmpty(date) || date.length() != formatString.length()) {
            return false;
        }

        Pattern pattern = Pattern.compile("^[0-9]*$");

        int yearIndexOf = formatString.indexOf("y");
        int yearLastIndexOf = formatString.lastIndexOf("y");
        String year = date.substring(yearIndexOf, yearLastIndexOf + 1);
        if (!pattern.matcher(year).matches()) {
            return false;
        }
        if (yearLastIndexOf + 1 != formatString.length()) {
            if (formatString.charAt(yearLastIndexOf + 1) != date.charAt(yearLastIndexOf + 1)) {
                return false;
            }
        }

        int monthIndexOf = formatString.indexOf("M");
        int monthLastIndexOf = formatString.lastIndexOf("M");
        String month = date.substring(monthIndexOf, monthLastIndexOf + 1);
        if (!pattern.matcher(month).matches()) {
            return false;
        }
        if (monthLastIndexOf + 1 != formatString.length()) {
            if (formatString.charAt(monthLastIndexOf + 1) != date.charAt(monthLastIndexOf + 1)) {
                return false;
            }
        }

        int dayIndexOf = formatString.indexOf("d");
        int dayLastIndexOf = formatString.lastIndexOf("d");
        String day = date.substring(dayIndexOf, dayLastIndexOf + 1);
        if (!pattern.matcher(day).matches()) {
            return false;
        }
        if (dayLastIndexOf + 1 != formatString.length()) {
            if (formatString.charAt(dayLastIndexOf + 1) != date.charAt(dayLastIndexOf + 1)) {
                return false;
            }
        }

        if (formatString.indexOf("H") <= 0) {
            return true;
        }

        int hourIndexOf = formatString.indexOf("H");
        int hourLastIndexOf = formatString.lastIndexOf("H");
        String hour = date.substring(hourIndexOf, hourLastIndexOf + 1);
        if (!pattern.matcher(hour).matches()) {
            return false;
        }
        if (hourLastIndexOf + 1 != formatString.length()) {
            if (formatString.charAt(hourLastIndexOf + 1) != date.charAt(hourLastIndexOf + 1)) {
                return false;
            }
        }

        int minuteIndexOf = formatString.indexOf("m");
        int minuteLastIndexOf = formatString.lastIndexOf("m");
        String minute = date.substring(minuteIndexOf, minuteLastIndexOf + 1);
        if (!pattern.matcher(minute).matches()) {
            return false;
        }
        if (minuteLastIndexOf + 1 != formatString.length()) {
            if (formatString.charAt(minuteLastIndexOf + 1) != date.charAt(minuteLastIndexOf + 1)) {
                return false;
            }
        }

        int secondIndexOf = formatString.indexOf("s");
        int secondLastIndexOf = formatString.lastIndexOf("s");
        String second = date.substring(secondIndexOf, secondLastIndexOf + 1);
        if (!pattern.matcher(second).matches()) {
            return false;
        }
        if (secondLastIndexOf + 1 != formatString.length()) {
            if (formatString.charAt(secondLastIndexOf + 1) != date.charAt(secondLastIndexOf + 1)) {
                return false;
            }
        }

        return true;

    }

    /**
     * 有効日付の検証
     * 
     * @param date 日付
     * @param formatString フォーマットタイプ
     * @return チェック結果
     */
    public static boolean checkDateInvalid(String date, String formatString) {

        int yearIndexOf = formatString.indexOf("y");
        int yearLastIndexOf = formatString.lastIndexOf("y");
        String year = date.substring(yearIndexOf, yearLastIndexOf + 1);

        int monthIndexOf = formatString.indexOf("M");
        int monthLastIndexOf = formatString.lastIndexOf("M");
        String month = date.substring(monthIndexOf, monthLastIndexOf + 1);

        int dayIndexOf = formatString.indexOf("d");
        int dayLastIndexOf = formatString.lastIndexOf("d");
        String day = date.substring(dayIndexOf, dayLastIndexOf + 1);

        int yearNum = Integer.valueOf((year));
        int monthNum = Integer.valueOf((month));
        int dayNum = Integer.valueOf((day));

        if (yearNum > 9999 || yearNum < 0) {
            return false;
        }

        if (monthNum > 12 || monthNum < 1) {
            return false;
        }

        int maxDay = 31;
        if (monthNum == 4 || monthNum == 6 || monthNum == 9 || monthNum == 11) {
            maxDay = 30;
        } else if (monthNum == 2) {
            if (yearNum % 4 > 0) {
                maxDay = 28;
            } else if (yearNum % 100 == 0 && yearNum % 400 > 0) {
                maxDay = 28;
            } else {
                maxDay = 29;
            }
        }

        if (dayNum > maxDay || dayNum < 1) {
            return false;
        }

        if (formatString.indexOf("H") <= 0) {
            return true;
        }

        int hourIndexOf = formatString.indexOf("H");
        int hourLastIndexOf = formatString.lastIndexOf("H");
        String hour = date.substring(hourIndexOf, hourLastIndexOf + 1);
        int hourNum = Integer.valueOf((hour));
        if (hourNum > 23) {
            return false;
        }

        int minuteIndexOf = formatString.indexOf("m");
        int minuteLastIndexOf = formatString.lastIndexOf("m");
        String minute = date.substring(minuteIndexOf, minuteLastIndexOf + 1);
        int minuteNum = Integer.valueOf((minute));
        if (minuteNum > 59) {
            return false;
        }

        int secondIndexOf = formatString.indexOf("s");
        int secondLastIndexOf = formatString.lastIndexOf("s");
        String second = date.substring(secondIndexOf, secondLastIndexOf + 1);
        int secondNum = Integer.valueOf((second));
        if (secondNum > 59) {
            return false;
        }
        return true;
    }
    
    /**
     * URLのページ名に正規表現タグ検証
     * 
     * @param value チェック値
     * @return 検証エラーの場合 false
     */
    public static boolean checkTagUrl(String value) {

        String urlWithoutTag = value.replaceAll("<[^<>]*>", "");

        Pattern urlPpattern = Pattern.compile("^(http|https)://[\\x00-\\x7F]*$");
        Matcher urlMatcher = urlPpattern.matcher(urlWithoutTag);
        if (!urlMatcher.find()) {
            return false;
        }

        return true;
    }

    /**
     * タグURLに正規表現タグ検証
     * 
     * @param regUrl チェック値
     * @return 検証エラーの場合 false
     */
    public static boolean checkTagRegs(String regUrl) {
        
        String regex = "<[^<>]*>";

        //  フィルターされたURLをチェックする
        String regUrlTagFiltered = regUrl.replaceAll(regex, "");
        if (regUrlTagFiltered.equals(regUrl)) {
            return false;
        }

        // タグをチェックする
        Boolean isValid = true;
        
        Pattern tagPattern = Pattern.compile(regex);
        Matcher tagMatcher = tagPattern.matcher(regUrl);

        while (tagMatcher.find()) {
            if (!isValidTag(tagMatcher.group())) {
                isValid = false;
                break;
            }
        }

        return isValid;
    }

    private static boolean isValidTag(String tag) {
        Boolean isValid = false;

        if ("<any>".equals(tag) || "<english-any>".equals(tag) || "<number-any>".equals(tag)) {
            isValid = true;
        } else if (tag.matches("^<[0-9]{1,2}-any>$")) {
            Pattern rangPattern = Pattern.compile("[0-9]+");
            Matcher rangMatcher = rangPattern.matcher(tag);
            if (rangMatcher.find()) {
                Integer rangNum = Integer.valueOf(rangMatcher.group());
                if (rangNum >= 1 && rangNum <= 20) {
                    isValid = true;
                }
            }
        } else if (tag.matches("^<\\(.*\\)\\-\\?>$")) {
            isValid = true;
        }

        return isValid;
    }

    
    /**
     * 
     * 入力文字がメールアドレスであるかチェックする。
     * 
     * @param value チェック値
     * @return チェック結果
     */
    public static boolean validateEmail(String value) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }
        
        Pattern urlPpattern = Pattern.compile("^[a-z][\\w]*[a-z0-9]@[a-z0-9][.-a-z0-9]*[a-z0-9]\\.[a-z0-9][.-a-z0-9]*[a-z0-9]$", Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = urlPpattern.matcher(value);
        if (!urlMatcher.find()) {
            return false;
        }

        return true;
    }
    
    /**
     * 
     * 正規表現を含むURLの規則によってURLを検証。
     * 
     * @param value チェック値
     * @param regs 正規表現のURL
     * @return チェック結果
     */
    public static boolean isContainTagRegs(String value, String regs) {
        if (StringUtils.isEmpty(value) || StringUtils.isEmpty(regs)) {
            return false;
        }
        
        // 正規表現メタ文字をエスケープ
        String newRegs = escapeRegExpMetacharacters(regs);
        
        // 正規表現用文字列の置き換え
        newRegs = newRegs.replaceAll("<any>", ".*");
        newRegs = newRegs.replaceAll("<([1-9]|1[0-9]|20)\\-any>", ".{$1}");
        newRegs = newRegs.replaceAll("<\\\\\\((.*)\\\\\\)\\-\\\\\\?>", "($1)?");
        newRegs = newRegs.replaceAll("<english\\-any>", "[a-zA-Z]+");
        newRegs = newRegs.replaceAll("<number\\-any>", "[0-9]+");

        Pattern pattern = Pattern.compile(newRegs);
        Matcher matcher = pattern.matcher(value);
        while (matcher.find()) {
            return true;
        }
        
        return false;
    }
    
    /**
     * 
     * 半角カンマが含まれていた場合をチェックする。
     * 
     * @param value チェック値
     * @return 半角カンマを含む場合は true
     */
    public static boolean isContainComma(String value) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        
        if(GenericValidator.matchRegexp(value, "\\,")){
            return true;
        }
        
        return false;
    }
    
    /**
     * HTMLの形式チェック
     * 
     * @param html チェックされたｈｔｍｌ
     * @return ルールに合う場合、trueを返す
     */
    private static boolean isValidHtml(String html) {
        Boolean isValid = false;
        
        // TODO



        return isValid;
    }
    
    /**
     * 
     * 文字をエスケープ。
     * 
     * @param str
     * @return エスケープ結果
     */
    private static String escapeRegExpMetacharacters(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        
        return str.replaceAll("(\\/|\\?|\\.|\\^|\\$|\\\\|\\*|\\+|\\(|\\)|\\[|\\]|\\{|\\}|\\|)", "\\\\$1");
    }

    /**
     * カラーコードチェック
     */
    public static boolean isColorCodeFormat(String colorCode) {
        if (StringUtils.isEmpty(colorCode)) {
            return false;
        }

        return GenericValidator.matchRegexp(colorCode, "[a-fA-F0-9]{6}");
    }
}
