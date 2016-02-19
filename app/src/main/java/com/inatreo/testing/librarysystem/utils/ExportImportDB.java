package com.inatreo.testing.librarysystem.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by vishal on 1/28/2016.
 */
public class ExportImportDB {
    private static final String dbFolderPath = "/data/data/com.inatreo.testing.librarysystem/databases";
    private static final String backupDbDirectoryPath = Environment.getExternalStorageDirectory() + "/LibrarySystemBackup";

    public static int exportDB() throws IOException{
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
    }
}
