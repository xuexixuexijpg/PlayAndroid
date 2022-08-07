package com.dragon.common_database.helper;

import android.content.Context;
import android.util.Log;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import kotlin.Suppress;

/**
 * @Description : 文件相关工具类
 */

@Suppress(names = "UNUSED")
public class FileTool {
    /**
     * @return java.io.File
     */
    public static File makeFilePath(String filePath, String fileName) {
        File file = null;
        // 生成文件夹
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            // 文件不存在则生成文件
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * @Description: 生成文件夹
     * @Param: filePath
     */
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }
    }


    /**
     * @Description: 获取log具体内容的前缀
     *
     * @Param:
     * @return java.lang.String
     */
    public static String getLogContentPrefix() {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy_HH:mm:ss_SSS");
        String dateString = df.format(new Date());
        String result = dateString + " | Molecision MP | info    | ";
        return result;
    }

    /**
     * 从assets目录下拷贝整个文件夹，不管是文件夹还是文件都能拷贝
     *
     * @param context           上下文
     * @param rootDirFullPath   文件目录，要拷贝的目录如assets目录下有一个tessdata文件夹：
     * @param targetDirFullPath 目标文件夹位置如：/Download/tessdata
     */
    public static boolean copyFolderFromAssets(Context context, String rootDirFullPath, String targetDirFullPath) {
        try {
            makeRootDirectory(targetDirFullPath);
            String[] listFiles = context.getAssets().list(rootDirFullPath);
            //遍历该目录下的文件和文件夹
            for (String string : listFiles) {
                //判断目录是文件还是文件夹，这里只好用.做区分了
                if (isFileByName(string)) {
                    //文件
                    if (copyFileFromAssets(context, rootDirFullPath + "/" + string, targetDirFullPath + "/" + string)) {
//
//                        if (targetDirFullPath.equals(Constants.SETTING_DIR_PATH) && ("db_version.nii".equals(string))) {
////                            //将数据库版本信息写入数据库
////                            DBVersion dbVersion = new DBVersion();
////                            dbVersion.setVersion(DBVersionXmlRepo.getInstance().getDbVersionStr(Constants.SETTING_DIR_PATH + "/" + string));
////                            //写入数据库
////                            ConstantsCache.greenDaoManager.getDaoSession().getDBVersionDao().insert(dbVersion);
//                        }
                    }

                } else {
                    //文件夹
                    String childRootDirFullPath = rootDirFullPath + "/" + string;
                    String childTargetDirFullPath = targetDirFullPath + "/" + string;
                    //new File(childTargetDirFullPath).mkdirs();
                    copyFolderFromAssets(context, childRootDirFullPath, childTargetDirFullPath);
                }
            }
            return true;
        } catch (IOException e) {
            Log.d("Tag", "copyFolderFromAssets " + "IOException-" + e.getMessage());
            Log.d("Tag", "copyFolderFromAssets " + "IOException-" + e.getLocalizedMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @Description: 删除文件夹里面的文件
     */
    public static void deleteFileFromDir(final String dirPath) {
        File dir = new File(dirPath);
        deleteDirWihtFile(dir);
    }

    /**
     *
     * 功能描述: 删除文件夹里面的所有文件，同时把文件夹删除
     *
     */
    public static void deleteDirAndFile(File dir) {
        deleteDirWihtFile(dir);
        dir.delete();
    }

    public static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            return;
        }
        if (dir.listFiles() != null) {
            for (File file : dir.listFiles()) {
                if (file.isFile()) {
                    file.delete(); // 删除所有文件
                } else if (file.isDirectory()) {
                    deleteDirWihtFile(file); // 递规的方式删除文件夹
                }
            }
        }
    }

    /**
     判断目录是否是文件（包含.的是文件，否则是文件夹）
     */
    private static boolean isFileByName(String string) {
        if (string.contains(".")) {
            return true;
        }
        return false;
    }

    /**
     * 从assets目录下拷贝文件
     *
     * @param context            上下文
     * @param assetsFilePath     文件的路径名如：SBClock/0001cuteowl/cuteowl_dot.png
     * @param targetFileFullPath 目标文件路径如：/sdcard/SBClock/0001cuteowl/cuteowl_dot.png
     */
    public static boolean copyFileFromAssets(Context context, String assetsFilePath, String targetFileFullPath) {
        Log.d("Tag", "copyFileFromAssets ");
        InputStream assestsFileImputStream;
        try {
            assestsFileImputStream = context.getAssets().open(assetsFilePath);
            return copyFile(assestsFileImputStream, targetFileFullPath);
        } catch (IOException e) {
            Log.d("Tag", "copyFileFromAssets " + "IOException-" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @Description: 将sourceFilePath复制到targetFilePath
     * @Param: sourceFilePath 源文件绝对路径
     * @Param: targetFilePath 目标文件绝对路径
     */
    public static void copyCommonFile(File sourceFile, String targetFilePath) {
        // 新建文件输入流并对它进行缓冲
        try {
            FileInputStream input = new FileInputStream(sourceFile);
            copyFile(input, targetFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static boolean copyFile(InputStream in, String targetPath) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(targetPath));
            byte[] buffer = new byte[1024];
            int byteCount = 0;
            while ((byteCount = in.read(buffer)) != -1) {// 循环从输入流读取
                // buffer字节
                fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
            }
            fos.flush();// 刷新缓冲区
            in.close();
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     *
     * 功能描述: 文件夹复制（使用缓冲字节流）
     *
     */
    public static void copyFolder(String sourcePath,String targetPath) throws Exception{

        //源文件夹路径
        File sourceFile = new File(sourcePath);
        //目标文件夹路径
        File targetFile = new File(targetPath);

        if(!sourceFile.exists()){
            throw new Exception("文件夹不存在");
        }
        if(!sourceFile.isDirectory()){
            throw new Exception("源文件夹不是目录");
        }
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        if(!targetFile.isDirectory()){
            throw new Exception("目标文件夹不是目录");
        }

        File[] files = sourceFile.listFiles();
        if(files == null || files.length == 0){
            return;
        }

        for(File file : files){
            //文件要移动的路径
            String movePath = targetFile+File.separator+file.getName();
            if(file.isDirectory()){
                //如果是目录则递归调用
                copyFolder(file.getAbsolutePath(),movePath);
            }else {
                //如果是文件则复制文件
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(movePath));

                byte[] b = new byte[1024];
                int temp = 0;
                while((temp = in.read(b)) != -1){
                    out.write(b,0,temp);
                }
                out.close();
                in.close();
            }
        }
    }
}
