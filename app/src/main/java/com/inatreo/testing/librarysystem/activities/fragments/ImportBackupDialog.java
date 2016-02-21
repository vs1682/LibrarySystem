package com.inatreo.testing.librarysystem.activities.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Created by vishal on 2/16/2016.
 */
public class ImportBackupDialog extends DialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        return (dialog.setMessage("Do you want to import one" +
                "?")
                    .setTitle("Backups available")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getActivity(), "yes is working", Toast.LENGTH_SHORT).show();
                            new SelectBackupDialog().show(getFragmentManager(), "selectBackup");
                        }
                    })
                    .setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getActivity(), "no is working", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .create());
    }
}
