package com.cio.gidservice.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cio.gidservice.R;

public class SuccessDialog extends Dialog {

    private Activity activity;
    private AlertDialog dialog;

    public SuccessDialog(Activity activity){
        super(activity);
        this.activity = activity;
    }

    public void start() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomAlertDialog);
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.success_custom_dialog, viewGroup, false);
        builder.setView(dialogView);
        dialog = builder.create();
        dialog.show();
        Button closeButton = dialogView.findViewById(R.id.buttonOk);
        closeButton.setOnClickListener(v -> dismiss());
    }

    public void start(String message) {
        start();
        TextView messageView = dialog.findViewById(R.id.success_dialog_message);
        messageView.setText(message);
    }

    public void start(String message, long millis) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    SuccessDialog.this.start(message);
                    sleep(millis);
                    SuccessDialog.this.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    public void dismiss() {
        dialog.dismiss();
    }
}
