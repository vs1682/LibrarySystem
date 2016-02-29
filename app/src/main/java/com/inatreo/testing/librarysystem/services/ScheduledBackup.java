package com.inatreo.testing.librarysystem.services;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.inatreo.testing.librarysystem.utils.ExportImportDB;

import java.io.IOException;

/**
 * Created by vishal on 2/24/2016.
 */
public class ScheduledBackup extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("-SB-", "it's backing up in background");
        try{
            ExportImportDB.exportDB();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
