package com.inatreo.testing.librarysystem.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vishal on 1/28/2016.
 */
public class ExportImportDB {
    private static final String dbFolderPath = "/data/data/com.inatreo.testing.librarysystem/databases";
    private static final String backupDbFolderPath = Environment.getExternalStorageDirectory() + "/LibrarySystemBackup";

    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    static Date date = new Date();

    private static boolean isDbPresent(){
        File dbFile = new File(dbFolderPath + "/library_system.db");
        if (dbFile.exists()){
            return true;
        }
        return false;
    }

/*    public static boolean isBackupPresent(){
        File backupFolder = new File(backupDbFolderPath);
        if (backupFolder.isDirectory()){
            if (backupFolder.list().length == 0){
                return true;
            }
        }
        return false;
    }*/


    public static int exportDB() throws IOException{
        int exportSuccessfull = 0;
        if (Environment.getExternalStorageDirectory().canWrite()){
            File dbFolder = new File(dbFolderPath);
            if (dbFolder.exists()){
                File backupDbFolder = new File(backupDbFolderPath);
                if (!backupDbFolder.exists()){
                    if (!backupDbFolder.mkdir())
                        return exportSuccessfull;
                }
                File dbFile = new File(dbFolderPath + "/library_system.db");
                File backupDbFile = new File(backupDbFolderPath + "/library_system_" + dateFormat.format(date) + ".db");
                if (isDbPresent()){
                    FileChannel src = new FileInputStream(dbFile).getChannel();
                    FileChannel dst = new FileOutputStream(backupDbFile).getChannel();
                    try{
                        dst.transferFrom(src, 0, src.size());
                        exportSuccessfull = 1;
                    }finally {
                        if (src != null)
                            src.close();
                        if (dst != null)
                            dst.close();
                    }
                }
            }

        }
        return exportSuccessfull;
    }

    public static void importDB(String file) throws IOException{
        File dbFolder = new File(dbFolderPath);
        if (!dbFolder.exists()){
            if (!dbFolder.mkdir())
                return;
        }
        File dbFile = new File(dbFolderPath + "/library_system.db");
        File backupFile = new File(backupDbFolderPath + "/" + file);
        FileChannel src = new FileInputStream(backupFile).getChannel();
        FileChannel dst = new FileOutputStream(dbFile).getChannel();
        try{
            dst.transferFrom(src, 0, src.size());
        }finally {
            if (src != null)
                src.close();
            if (dst != null)
                dst.close();
        }
    }

    public static String[] listAllBackupFiles() throws IOException{
        File backupDbFolder = new File(backupDbFolderPath);
        for (String file : backupDbFolder.list()){
            Log.v("-EIDB-",file);
        }
        return backupDbFolder.list();
    }

    public static void deleteOldestFile(){
        File backupDbFolder = new File(backupDbFolderPath);
        String[] dbFiles = backupDbFolder.list();
        Log.v("-EIDB-", dbFiles[0]);
        if (dbFiles.length > 4){
            File oldestFile = new File(backupDbFolderPath + "/" + dbFiles[0]);
            Log.v("-EIDB-", backupDbFolderPath + "/" + dbFiles[0]);
            if(oldestFile.delete())
                Log.v("-EIDB-", "deleted");
        }
    }
    /*public static int exportDB() throws IOException{
        if (Environment.getExternalStorageDirectory().canWrite()){
            File backupDBFolder = new File(backupDbDirectoryPath);
            if (!backupDBFolder.exists()){
                backupDBFolder.mkdir();
            }
            File dbFile = new File(dbFolderPath+"/library_system.db");
            if (!isBackupPresent()){
                (new File(backupDbDirectoryPath+"/library_system.db")).createNewFile();
            }
            if (isDbPresent()){
                FileChannel src = new FileInputStream(dbFile).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                return 1;
            }else return 0;
        }else return 0;
    }

    public static int importDB(Context context) throws IOException{
        if (!isDbPresent()){
            Log.v("-EIDB-","present");
            File dbFolder = new File(dbFolderPath);
            if (!dbFolder.exists()){
                dbFolder.mkdir();
            }
            File backupDB = new File(backupDbPath);
            if (backupDB.exists()){
                File newDbFile = new File(dbFolder.getAbsolutePath(), "library_system.db");
                FileChannel src = new FileInputStream(backupDB).getChannel();
                FileChannel dst = new FileOutputStream(newDbFile).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                return 1;
            }else return 0;
        }else return 0;
    }

    public static boolean isBackupPresent(){
        File backupFolder = new File(backupDbDirectoryPath);
        if (backupFolder.isDirectory()){
            if (backupFolder.list().length == 0){
                return true;
            }
        }
        return false;
    }

    public static boolean isDbPresent(){
        File dbFile = new File(dbFolderPath+"/library_system.db");
        if (dbFile.exists()){
            return true;
        }
        return false;
    }*/
}
