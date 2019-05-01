package com.team7.hadcontrolpanel;

import android.content.DialogInterface;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

public class DialogBox extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle SavedInstanceSate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Information")
                .setMessage("Item has been saved")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}