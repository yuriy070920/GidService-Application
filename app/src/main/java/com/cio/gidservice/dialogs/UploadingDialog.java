package com.cio.gidservice.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.cio.gidservice.R;

public class UploadingDialog extends Dialog {

    Activity activity;
    AlertDialog dialog;

    public UploadingDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    public void startLoading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomAlertDialog);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_progress, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }
    public void startLoading(String text) {
        TextView view = dialog.findViewById(R.id.progress_message);
        view.setText(text);
        startLoading();
    }

    public void dismiss() {
        dialog.dismiss();
    }
}
