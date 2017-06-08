package com.kuaimeizhuang.fashionmix.utils;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.bumptech.glide.Glide;

import java.io.File;
import java.math.BigDecimal;

/**
 * <p>清除缓存</p>
 * Created on 17/5/22.
 *
 * @author Shi GuangXiang.
 */

public class DataCleanUtils {
    public static final String FRESCO_CACHE = "LazyCat";

    public DataCleanUtils() {
    }

    /**
     * * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * *
     *
     * @param context
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * *
     *
     * @param context
     */
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/databases"));
    }

    /**
     * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) *
     *
     * @param context
     */
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * * 按名字清除本应用数据库 * *
     *
     * @param context
     * @param dbName
     */
    public static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * * 清除/data/data/com.xxx.xxx/files下的内容 * *
     *
     * @param context
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     *
     * @param context
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }

    }

    /**
     * * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * *
     *
     * @param filePath
     */
    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    /**
     * * 清除本应用所有的数据 * *
     *
     * @param context
     * @param filepath
     */
    public static void cleanApplicationData(Context context, String... filepath) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        if (filepath != null) {
            String[] var2 = filepath;
            int var3 = filepath.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                String filePath = var2[var4];
                cleanCustomCache(filePath);
            }

        }
    }

    /**
     * * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * *
     *
     * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            File[] var1 = directory.listFiles();
            int var2 = var1.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                File item = var1[var3];
                boolean success = item.getAbsoluteFile().delete();
                Log.v("cleandata", "success  " + success + "  path " + item.getAbsolutePath());
            }
        }

    }

    // 获取文件
    //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
    //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    public static long getFolderSize(File file) throws Exception {
        long size = 0L;

        try {
            File[] e = file.listFiles();

            for (int i = 0; i < e.length; ++i) {
                if (e[i].isDirectory()) {
                    size += getFolderSize(e[i]);
                } else {
                    size += e[i].length();
                }
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return size;
    }

    /**
     * 删除指定目录下文件及目录
     *
     * @param deleteThisPath
     * @param filePath
     * @return
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File e = new File(filePath);
                if (e.isDirectory()) {
                    File[] files = e.listFiles();

                    for (int i = 0; i < files.length; ++i) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }

                if (deleteThisPath) {
                    if (!e.isDirectory()) {
                        e.delete();
                    } else if (e.listFiles().length == 0) {
                        e.delete();
                    }
                }
            } catch (Exception var5) {
                var5.printStackTrace();
            }
        }

    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024.0D;
        if (kiloByte < 1.0D) {
            return size + "Byte";
        } else {
            double megaByte = kiloByte / 1024.0D;
            if (megaByte < 1.0D) {
                BigDecimal gigaByte1 = new BigDecimal(Double.toString(kiloByte));
                return gigaByte1.setScale(2, 4).toPlainString() + "KB";
            } else {
                double gigaByte = megaByte / 1024.0D;
                if (gigaByte < 1.0D) {
                    BigDecimal teraBytes1 = new BigDecimal(Double.toString(megaByte));
                    return teraBytes1.setScale(2, 4).toPlainString() + "MB";
                } else {
                    double teraBytes = gigaByte / 1024.0D;
                    BigDecimal result4;
                    if (teraBytes < 1.0D) {
                        result4 = new BigDecimal(Double.toString(gigaByte));
                        return result4.setScale(2, 4).toPlainString() + "GB";
                    } else {
                        result4 = new BigDecimal(teraBytes);
                        return result4.setScale(2, 4).toPlainString() + "TB";
                    }
                }
            }
        }
    }

    public static String getCacheSize(File file) throws Exception {
        return getFormatSize((double) getFolderSize(file));
    }

    public static String getCacheSize(Application context) {
        long size = 0L;

        try {
            size = getFolderSize(getExternalFile(context));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return getFormatSize((double) size);
    }

    public static File getExternalCacheDir(Application context) {
        return context.getExternalCacheDir();
    }

    public static void cleanCache(Application context) {
        deleteFolderFile(getExternalFilePath(context), false);
    }

    public static File getExternalFile(Application context) {
        return new File(getExternalFilePath(context));
    }

    public static String getExternalFilePath(Application context) {
        //return Environment.getExternalStorageDirectory() + "/Android/data/" + AppSystemUtil.getPackageName();
        return Glide.getPhotoCacheDir(context).getAbsolutePath();
    }
}

