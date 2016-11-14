package jp.microad.blade.batch.kizasi.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jp.microad.blade.batch.kizasi.constants.Constants;

/**
 * ファイル処理のクラス
 */
public class FileUtils {

    /**
     * フォルダ配下の子フォルダのmax no.を取得する
     *
     * @param f
     * @return
     */
    public static int getMaxSubDirName(File f) {
        if (!f.exists()) {
            return 1;
        }

        OptionalInt maxOptional = Arrays.asList(f.listFiles()).stream()
                .filter(File::isDirectory)
                .map(file -> file.getName())
                .mapToInt(Integer::parseInt)
                .max();

        return maxOptional.orElse(1);
    }

    /**
     * フォルダ配下の子フォルダのmax no.を取得する
     *
     * @param path
     * @return
     */
    public static int getMaxSubDirName(String path) {
        File f = new File(path);
        return getMaxSubDirName(f);
    }

    /**
     * フォルダ配下のファイルの数量を取得する
     *
     * @param f
     * @return
     */
    public static int getFileCountInFolder(File f) {
        if (!f.exists()) {
            return 0;
        }

        int fileCount = (int)Arrays.asList(f.listFiles()).stream()
                .filter(File::isFile)
                .count();
        return fileCount;
    }

    /**
     * フォルダ配下のファイルの数量を取得する
     *
     * @param path
     * @return
     */
    public static int getFileCountInFolder(String path) {
        File f = new File(path);
        return getFileCountInFolder(f);
    }

    /**
     * フォルダ配下のファイルリストを取得する
     *
     * @param f
     * @param filters ファイルフィールド条件のリスト
     * @return
     */
    public static List<File> getFileList(File f, List<Predicate<File>> filters) {
        return getFileList(f, filters, null);
    }

    /**
     * フォルダ配下のファイルリストを取得する
     * @param path
     * @param filters ファイルフィールド条件のリスト
     * @return
     */
    public static List<File> getFileList(String path, List<Predicate<File>> filters) {
        File f = new File(path);
        return getFileList(f, filters);
    }

    /**
     * ソータ順によって、フォルダ配下のファイルリストを取得する
     *
     * @param f
     * @param filters ファイルフィールド条件のリスト
     * @param sort
     * @return
     */
    public static List<File> getFileList(File f, List<Predicate<File>> filters, String sort) {
        if (!f.exists() || f.listFiles().length == 0) {
            return new ArrayList<File>();
        }

        Stream<File> fileStream = Arrays.asList(f.listFiles()).stream()
                .filter(File::isFile);
        for (Predicate<File> p : filters) {
            fileStream = fileStream.filter(p);
        }

        Comparator<File> c = (f1, f2) -> f1.getName().compareTo(f2.getName());
        if (Constants.SORT.ASCENDING.name().equals(sort)) {
            fileStream = fileStream.sorted(c);
        }
        if (Constants.SORT.REVERSE.name().equals(sort)) {
            fileStream = fileStream.sorted(c.reversed());
        }

        return fileStream.collect(Collectors.toList());
    }

    /**
     * ソータ順によって、フォルダ配下のファイルリストを取得する
     *
     * @param path
     * @param filters ファイルフィールド条件のリスト
     * @param sort
     * @return
     */
    public static List<File> getFileList(String path, List<Predicate<File>> filters, String sort) {
        File f = new File(path);
        return getFileList(f, filters, sort);
    }
}
