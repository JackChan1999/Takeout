package com.itheima.takeout.utils;

/**
 * 增量更新工具
 */

public class PatchUtils {
    static{
        System.loadLibrary("Patch");// 加载生成的.so文件
    }


    /**
     * native 方法 合并老版本APK和升级包的方法
     * @param oldApkPath   老版本的apk文件路径     *
     * @param newApkPath   新版本apk文件存放的路径
     * @param patchPath    升级包路径
     * @return
     */
    public static native int patch(String oldApkPath,String newApkPath,String patchPath);
}
