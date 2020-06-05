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

public class ErrorDialog extends Dialog {

    private Activity activity;
    private AlertDialog dialog;
    private boolean autoClose;

    public ErrorDialog(Activity activity){
        super(activity);
        this.activity = activity;
    }

    public void start() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomAlertDialog);
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.error_custom_dialog, viewGroup, false);
        builder.setView(dialogView);
        dialog = builder.create();
        dialog.show();
        Button closeButton = dialogView.findViewById(R.id.buttonOkError);
        closeButton.setOnClickListener(v -> dismiss());
    }

    public void start(String message) {
        start();
        TextView messageView = dialog.findViewById(R.id.error_dialog_message);
        messageView.setText(message);
    }

    public void start(String message, long millis) {
        start(message);
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(millis);
                    ErrorDialog.this.dismiss();
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
