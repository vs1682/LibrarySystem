package com.inatreo.testing.librarysystem.activities.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.inatreo.testing.librarysystem.interfaces.CreateNewMasterInterface;

/**
 * Created by vishal on 3/11/2016.
 */
public class CreatingMasterWarningDialog extends DialogFragment {
    CreateNewMasterInterface newMasterInterface;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        return (dialog.setTitle("Another Master Warning!!")
            .setMessage("You are about to create another master. This will delete the earlier master. Do you want to continue ?")
            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dismiss();
                }
            })
            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    newMasterInterface.createNewMaster();
                }
            })
            .create()
        );
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            newMasterInterface = (CreateNewMasterInterface) activity;
        }catch (ClassCastException e){
            throw (new ClassCastException(activity.toString() + " must implement CreateNewMasterInterface"));
        }
    }
}
