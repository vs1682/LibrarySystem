package com.inatreo.testing.librarysystem.activities.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.inatreo.testing.librarysystem.utils.ExportImportDB;

import java.io.IOException;

/**
 * Created by vishal on 2/21/2016.
 */
public class SelectBackupDialog extends DialogFragment {
    String[] backupFiles;
    String selectedFile;

    public interface SelectBackupInterface{
        public void selectedFile(String file);
    }

    SelectBackupInterface selectBackupInterface;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        try {
            backupFiles = ExportImportDB.listAllBackupFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("choose backup file")
                .setSingleChoiceItems(backupFiles, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        selectedFile = backupFiles[position];
                    }
                })
                .setPositiveButton("select", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        selectBackupInterface.selectedFile(selectedFile);
                        dismiss();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
        return dialog.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            selectBackupInterface = (SelectBackupInterface) activity;
        }catch (ClassCastException e){
            throw (new ClassCastException(activity.toString() + " must implement SelectBackupInterface"));
        }
    }
}
