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

public class QuestionDialog extends Dialog {

    private Activity activity;
    private AlertDialog dialog;
    private AfterProcess process;

    public QuestionDialog(Activity activity, AfterProcess process) {
        super(activity);
        this.activity = activity;
        this.process = process;
    }

    public void start() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomAlertDialog);
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.question_custom_dialog, viewGroup, false);
        builder.setView(dialogView);
        dialog = builder.create();
        dialog.show();
        Button yesButton = dialogView.findViewById(R.id.buttonYes);
        Button noButton = dialogView.findViewById(R.id.buttonNo);
        noButton.setOnClickListener(v -> dismiss());
        yesButton.setOnClickListener(v -> {
            process.run();
            dismiss();
        });
    }

    public void start(String message) {
        start();
        TextView messageView = dialog.findViewById(R.id.success_dialog_message);
        messageView.setText(message);
    }

    public void dismiss() {
        dialog.dismiss();
    }

}
