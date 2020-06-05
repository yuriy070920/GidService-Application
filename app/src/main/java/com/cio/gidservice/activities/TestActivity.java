package com.cio.gidservice.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cio.gidservice.R;
import com.cio.gidservice.dialogs.QuestionDialog;
import com.cio.gidservice.dialogs.SuccessDialog;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        findViewById(R.id.testButton).setOnClickListener(v -> {
            QuestionDialog successDialog = new QuestionDialog(this, () -> {
                SuccessDialog dialog = new SuccessDialog(this);
                dialog.start();
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(2000);
                            dialog.dismiss();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            });
            successDialog.start("Это просто для тестирования, чтобы понять, все ли работает правильно, чтобы потом не было проблем!");
        });
    }
}
