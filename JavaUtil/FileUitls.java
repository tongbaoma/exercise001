package jp.microad.blade.util;

import java.awt.image.renderable.ParameterBlock;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

import jp.microad.blade.dispentity.structure.IndustryInfo;
import jp.microad.blade.dispentity.structure.ScoreInfo;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.sun.media.jai.codec.ByteArraySeekableStream;


public class FileUitls {

	/**
	 * 画像ファイルの取得
	 *
	 * @param path ファイルパス
	 * @param isHistoryPath 履歴ファイルパスのフラグ
	 * @return byte[] 画像ファイルのバイト配列
	 */
	public static byte[] getFileToByte(String path, boolean isHistoryPath) {
		byte [] imageBytes = null;

		String imagePath = "";
		if (!isHistoryPath) {
			imagePath = DspProps.getInstance().getImageOutputDir() + path;
		} else  {
			imagePath = DspProps.getInstance().getImageOutputHistoryDir() + path;
		}

		File f = new File(imagePath);
		if (f.exists()) {
			try {
			    FileInputStream fis = new FileInputStream(f);
			    byte [] bytes = new byte[fis.available()];
			    fis.read(bytes);
			    imageBytes = bytes;
			    fis.close();
			} catch (Exception e) {
				return imageBytes;
			}
		}

		return imageBytes;
	}

    /**
     * ファイルはバイト数列へ変換する
     *
     * @param path ファイルパス
     */
    public static byte[] getFileToByte(String path) {
        byte [] fileBytes = null;
        File f = new File(path);
        if (f.exists()) {
            try {
                FileInputStream fis = new FileInputStream(f);
                byte [] bytes = new byte[fis.available()];
                fis.read(bytes);
                fileBytes = bytes;
                fis.close();
            } catch (Exception e) {
                return fileBytes;
            }
        }

        return fileBytes;
    }

    /**
     * ファイルはバイト数列へ変換する
     *
     * @param path ファイルパス
     */
    public static byte[] getFileToByte(File f) {
        byte [] fileBytes = null;
        if (f.exists()) {
            try {
                FileInputStream fis = new FileInputStream(f);
                byte [] bytes = new byte[fis.available()];
                fis.read(bytes);
                fileBytes = bytes;
                fis.close();
            } catch (Exception e) {
                return fileBytes;
            }
        }

        return fileBytes;
    }

	/**
	 * 配置のパスについて、ファイルを移動する。
	 *
	 * @param coAccountId アカウント
	 * @param fileName ファイル名
	 * @return 成功の場合、 trueを返す。
	 */
	public static boolean moveAdbannerImageFile(String coAccountId, String fileName) {
		boolean isOk = false;

		if (checkClientDir(coAccountId, true)) {
			String srcFileName = DspProps.getInstance().getImageOutputDir() + coAccountId + File.separator + fileName;
			String destFileName = DspProps.getInstance().getImageOutputHistoryDir()
                    + coAccountId + File.separator + fileName;

			File srcFile = new File(srcFileName);
			File destFile = new File(destFileName);

            if (srcFile.exists()) {
				try {
					FileUtils.moveFile(srcFile, destFile);
				} catch(IOException e) {

				}
				isOk = true;
			}
		}

		return isOk;
	}

	/**
	 * パスチェック
	 * @param coAccountId アカウントID
	 * @param isHistory 元のファイルパスのフラグ
	 * @return チェック成功の場合 true
	 */
	public static boolean checkClientDir(String coAccountId, boolean isHistory) {
		boolean isOk = false;

		String clientDir = StringUtils.EMPTY;

        if (isHistory == false) {
			clientDir = DspProps.getInstance().getImageOutputDir() + coAccountId;
		} else {
			clientDir = DspProps.getInstance().getImageOutputHistoryDir() + coAccountId;
		}

		File dir = new File(clientDir);

        if (dir.exists()) {
			isOk = true;
		} else {

			if (dir.mkdirs()) {
				isOk = true;
			} else {
			}
		}
		return isOk;
	}

	/**
	 * 画像ファイルの幅と高のチェック
	 *
	 * @param binary 画像のバイト配列
	 * @param width 画像の幅
	 * @param height 画像の高
	 * @return 引数 width が画像の幅と等しくて、然も引数 height が画像の高と等しい場合だけ true
	 */
	public static boolean validateImageSize(byte[] binary, int width, int height) {
		JAI jai = new JAI();
		ByteArraySeekableStream bass = null;

		try {
			bass = new ByteArraySeekableStream(binary);
		} catch (IOException ioe) {
			return false;
		}

		ParameterBlock pb = new ParameterBlock();
		pb.add(bass);
		RenderedOp image = jai.createNS("stream", pb, null);

		try {
			image.getWidth();
		} catch (RuntimeException re) {
			return false;
		}

		// ぴったり同じじゃないとだめ
		if (image.getWidth() == width && image.getHeight() == height) {
			return true;
		}

		return false;
	}

	/**
	 * 画像ファイルの幅と高のチェック
	 *
	 * @param binary 画像のバイト配列
	 * @param width 画像の幅
	 * @param height 画像の高
	 * @return 引数 width が画像の幅と等しくて、然も引数 height が画像の高と等しい場合だけ true
	 */
	public static boolean validateLessThanImageSize(byte[] binary, int width, int height) {
		JAI jai = new JAI();
		ByteArraySeekableStream bass = null;

		try {
			bass = new ByteArraySeekableStream(binary);
		} catch (IOException ioe) {
			return false;
		}

		ParameterBlock pb = new ParameterBlock();
		pb.add(bass);
		RenderedOp image = jai.createNS("stream", pb, null);

		try {
			image.getWidth();
		} catch (RuntimeException re) {
			return false;
		}

		// ぴったり同じじゃないとだめ
		if (image.getWidth() <= width && image.getHeight() <= height) {
			return true;
		}

		return false;
	}

	/**
	 * 指定フォルダの中のファイルのサイズ
	 *
	 * @param file 指定ファイル
	 * @return ファイルのサイズ
	 */
	public static long getAllFileSizeInDir(File file) {
		long size = 0;
		File flist[] = file.listFiles();
		if (flist == null) {
			return size;
		}

		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getAllFileSizeInDir(flist[i]);
			} else {
				size = size + flist[i].length();
			}
		}
		return size;
	}

	/**
	 * swfファイルであるかのチェック
	 *
	 * @param signature ファイルのバイト配列
	 * @return swfファイルである場合trueを返す
	 */
	public static boolean isSWF(byte[] signature) {
		if (signature == null) {
			return false;
		}
		String sig = "" + (char) signature[0] + (char) signature[1] + (char) signature[2];

		if (sig.equals("FWS") || sig.equals("CWS")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * swfファイルの幅と高のチェック
	 *
	 * @param temp swfのバイト配列
	 * @param width swfの幅
	 * @param height swfの高
	 * @return 引数 width が swf の幅と等しくて、然も引数 height が swf の高と等しい場合だけ true
	 */
	public static boolean validateSwfSize(byte[] temp, int width, int height) {

		if (temp == null) {
			return false;
		}

		byte[] swf = null;

		if (isCompressed(temp[0])) {
			try {
				swf = uncompress(temp);
			} catch (DataFormatException e) {
				return false;
			}
		} else {
			swf = temp;
		}

		int nbits = ((swf[8] & 0xff) >> 3);

		PackedBitObj pbo = readPackedBits(swf, 8, 5, nbits);

		PackedBitObj pbo2 = readPackedBits(swf, pbo.nextByteIndex, pbo.nextBitIndex, nbits);

		PackedBitObj pbo3 = readPackedBits(swf, pbo2.nextByteIndex, pbo2.nextBitIndex, nbits);

		PackedBitObj pbo4 = readPackedBits(swf, pbo3.nextByteIndex, pbo3.nextBitIndex, nbits);

		int xmax = pbo2.value;
		int ymax = pbo4.value;

		int widthReal = convertTwipsToPixels(xmax);
		int heightReal = convertTwipsToPixels(ymax);

		if (width == widthReal && height == heightReal) {
			return true;
		}
		return false;

	}

	private static boolean isCompressed(int firstByte) {
		if (firstByte == 67) {
			return true;
		} else {
			return false;
		}
	}

	private static byte[] uncompress(byte[] bytes) throws DataFormatException {
		Inflater decompressor = new Inflater();
		decompressor.setInput(strip(bytes));// feed the Inflater the bytes

		ByteArrayOutputStream stream = new ByteArrayOutputStream(bytes.length - 8);// an
		// expandable
		// byte
		// array
		// to
		// store
		// the
		// uncompressed
		// data

		byte[] buffer = new byte[1024];
		while (!decompressor.finished())// read until the end of the stream is
		// found
		{
			try {
				int count = decompressor.inflate(buffer);// decompress the data
				// into the buffer
				stream.write(buffer, 0, count);
			} catch (DataFormatException dfe) {
				dfe.printStackTrace();
			}
		}
		try {
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// create an array to hold the header and body bytes
		byte[] swf = new byte[8 + stream.size()];
		// copy the first 8 bytes which are uncompressed into the swf array
		System.arraycopy(bytes, 0, swf, 0, 8);
		// copy the uncompressed data into the swf array
		System.arraycopy(stream.toByteArray(), 0, swf, 8, stream.size());
		// the first byte of the swf indicates whether the swf is compressed or
		// not
		swf[0] = 70;

		return swf;
	}

	private static byte[] strip(byte[] bytes) {
		byte[] compressable = new byte[bytes.length - 8];
		System.arraycopy(bytes, 8, compressable, 0, bytes.length - 8);// fills a
		// byte
		// array
		// with
		// data
		// needing
		// decompression
		return compressable;
	}

	private static PackedBitObj readPackedBits(byte[] bytes, int byteMarker, int bitMarker, int length) {
		int total = 0;
		int shift = 7 - bitMarker;
		int counter = 0;
		int bitIndex = bitMarker;
		int byteIndex = byteMarker;

		while (counter < length) {
			for (int i = bitMarker; i < 8; i++) {
				int bit = ((bytes[byteMarker] & 0xff) >> shift) & 1;
				total = (total << 1) + bit;
				bitIndex = i;
				shift--;
				counter++;

				if (counter == length) {
					break;
				}
			}
			byteIndex = byteMarker;
			byteMarker++;
			bitMarker = 0;
			shift = 7;
		}
		return new PackedBitObj(bitIndex, byteIndex, total);
	}

	private static int convertTwipsToPixels(int twips) {
		return twips / 20;
	}

	/**
	 * 指定したXMLファイルの解析。
	 *
	 * @param coAccountId  アカウント
	 * @param profileId  プロフィールID
	 * @return 解析したスコアリスト
	 * @exception Exception 解析エラーの場合
	 */
	@SuppressWarnings({ "rawtypes" })
	public static Map<String, List> getScoreInfoList(String coAccountId, String profileId) throws Exception {
	    Map<String, List> map = new HashMap<String, List>();

		String profileFilePath = DspProps.getInstance().getProfileFilePath() + coAccountId + File.separator + "profile_" +  profileId + ".xml";

		File dir = new File(profileFilePath);

		if(!dir.exists()){
			return map;
		}

		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(profileFilePath));

		Element root = document.getRootElement();

		// seriesのエレメントを取得
		Element seriesElement = root.element("series");

		List<ScoreInfo> scoreInfoList = new ArrayList<ScoreInfo>();

		List seriesElementList = seriesElement.elements();

		String profileFileType = getProfileFileType(coAccountId,profileId);

		Map<Element, List> grahMap = new HashMap<Element, List>();
		// seriesエレメントによって、解析Objectを作成する
		for (int i = 0; i < seriesElementList.size(); i++) {

			Element element = (Element) seriesElementList.get(i);

			ScoreInfo scoreInfo = new ScoreInfo();
			// Scoreを設定
			scoreInfo.setScore(element.getText());

			List<IndustryInfo> industryInfoList = getIndustryInfoList(root, i, profileFileType, grahMap);

			// graphsエレメントのObjectを設定
			scoreInfo.setIndustryInfoList(industryInfoList);

			scoreInfoList.add(scoreInfo);
		}

		List<IndustryInfo> industryInfoList = getIndustryInfoList(root, profileFileType);

		map.put("scoreInfoList", scoreInfoList);
		map.put("industryInfoList", industryInfoList);

		return map;
	}

	/**
	 * 指定したXMLファイルのgraphsエレメントの解析。
	 *
	 * @param root  XMLファイルのrootエレメント
	 * @return ubCountIndex seriesと対応のindex
	 * @return industryInfoList 解析したgraphsのObject
	 */
	@SuppressWarnings("rawtypes")
    private static List<IndustryInfo> getIndustryInfoList(Element root,
            int ubCountIndex, String profileFileType, Map<Element, List> grahMap) {
		List<IndustryInfo> industryInfoList = new ArrayList<IndustryInfo>();

		// graphsのエレメントを取得
		Element graphsElement = root.element("graphs");

		// 新しいXMLの場合はindustry_idのvalue値を取得する
        if (profileFileType.equals("industry_id") || profileFileType.equals("title")) {
            int i = 0;
			for (Iterator iterator = graphsElement.elementIterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();

                IndustryInfo industryInfo = new IndustryInfo();
				if (element.attributeValue("industry_id") != null) {
					// industry_idのvalue値を設定。
					industryInfo.setIndustryId(element.attributeValue("industry_id"));
					industryInfo.setIndustryName(null);
                } else if (element.attributeValue("title") != null) {
                    industryInfo.setIndustryName(element.attributeValue("title"));
                    industryInfo.setIndustryId(String.valueOf(i));
                }

                List els = (List) grahMap.get(element);
                if (els == null) {
                    els = element.elements();
                    grahMap.put(element, els);
                }
					// graphエレメントのvalue値を設定
                if (els.size() > 0 && els.size() > ubCountIndex) {
                    Element element2 = (Element) (els.get(ubCountIndex));
						industryInfo.setUbCount(element2.getText());
                    industryInfoList.add(industryInfo);
                }
                i++;
					}
				}

        return industryInfoList;
			}

    /**
     * 指定したXMLファイルのgraphsエレメントの解析。
     *
     * @param root  XMLファイルのrootエレメント
     * @return SegmentInfoList 解析したgraphsのObject
     */
    @SuppressWarnings("rawtypes")
    private static List<IndustryInfo> getIndustryInfoList(Element root,
            String profileFileType) {
        List<IndustryInfo> industryInfoList = new ArrayList<IndustryInfo>();

        // graphsのエレメントを取得
        Element graphsElement = root.element("graphs");

        // 新しいXMLの場合はindustry_idのvalue値を取得する
        if (profileFileType.equals("industry_id") || profileFileType.equals("title")) {
            int i = 0;
			for (Iterator iterator = graphsElement.elementIterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();

					IndustryInfo industryInfo = new IndustryInfo();
                if (element.attributeValue("industry_id") != null) {
                    // industry_idのvalue値を設定。
                    industryInfo.setIndustryId(element.attributeValue("industry_id"));
                    industryInfo.setIndustryName(null);
                } else if (element.attributeValue("title") != null) {
					industryInfo.setIndustryName(element.attributeValue("title"));
                    industryInfo.setIndustryId(String.valueOf(i));
					}
					industryInfoList.add(industryInfo);
                i++;
				}
			}

		return industryInfoList;
	}

    /**
     * スコアを取得する
     */
    @SuppressWarnings({ "unchecked" })
    public static List<String> getScoreList(String coAccountId, String profileId) throws Exception {
        List<String> scoreList = new ArrayList<String>();

        String profileFilePath = DspProps.getInstance().getProfileFilePath() + coAccountId + File.separator + "profile_" +  profileId + ".xml";

        File dir = new File(profileFilePath);

        if(!dir.exists()){
            return scoreList;
        }

        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(profileFilePath));

        Element root = document.getRootElement();

        // seriesのエレメントを取得
        Element seriesElement = root.element("series");
        List<Element> seriesElementList = (List<Element>)seriesElement.elements();

        // seriesエレメントによって、解析Objectを作成する
        for (Element element : seriesElementList) {
            scoreList.add(element.getText());
        }

        return scoreList;
    }

	/**
	 * 判断xmlタイプ
     *
     * @param root  XMLファイルのrootエレメント
     * @return ubCountIndex seriesと対応のindex
     * @return xmlタイプ(industry_id,title)
	 */
	@SuppressWarnings("rawtypes")
	public static String getProfileFileType(String coAccountId, String profileId) throws Exception {

        String profileFilePath = DspProps.getInstance().getProfileFilePath() + coAccountId + File.separator + "profile_" +  profileId + ".xml";

		File dir = new File(profileFilePath);

		if (!dir.exists()) {
			return null;
		}

		SAXReader reader = new SAXReader();

		Document document = reader.read(new File(profileFilePath));

		Element root = document.getRootElement();

		String type = "title";

		// graphsのエレメントを取得
		Element graphsElement = root.element("graphs");

		for (Iterator iterator = graphsElement.elementIterator(); iterator.hasNext();) {
			Element element = (Element) iterator.next();

			// 先にindustry_idを取得する。
			if (element.attributeValue("industry_id") != null) {
				type = "industry_id";
                break;
			}
		}
		return type;
	}

	/**
	 * 指定したファイルの最後の改正日付。
	 *
	 * @param path フアイル場所
	 * @return Date 最後の改正日付 yyyy/MM/dd HH:mm:ss
	 */
	public static Date getLastModifiedDateOfFile(String path) {
		File dir = new File(path);
		long lastModified = dir.lastModified();
		if (lastModified == 0L) {
			return null;
		}
		Date date = new Date(lastModified);
		return date;
	}

	/**
	 * エクセルファイルを解析
	 *
	 * @param inputStream エクセルファイル
	 * @return HSSFWorkbook
	 */
	public static Workbook xlsParse(FileInputStream inputStream) {
		Workbook workbook = null;

		try {
			workbook = WorkbookFactory.create(inputStream);

		} catch (Exception e) {
			return null;
		}

		return workbook;
	}

	public static String getSwfSize(byte[] temp) {
		String result = "";
		byte[] swf = null;

		if (isCompressed(temp[0])) {
			try {
				swf = uncompress(temp);
			} catch (DataFormatException e) {
				return result;
			}
		} else {
			swf = temp;
		}

		int nbits = ((swf[8] & 0xff) >> 3);

		PackedBitObj pbo = readPackedBits(swf, 8, 5, nbits);

		PackedBitObj pbo2 = readPackedBits(swf, pbo.nextByteIndex, pbo.nextBitIndex, nbits);

		PackedBitObj pbo3 = readPackedBits(swf, pbo2.nextByteIndex, pbo2.nextBitIndex, nbits);

		PackedBitObj pbo4 = readPackedBits(swf, pbo3.nextByteIndex, pbo3.nextBitIndex, nbits);

		int xmax = pbo2.value;
		int ymax = pbo4.value;

		int widthReal = convertTwipsToPixels(xmax);
		int heightReal = convertTwipsToPixels(ymax);

		result = StringControl.valueOf(widthReal) + "×" + StringControl.valueOf(heightReal);

		return result;
	}
}
