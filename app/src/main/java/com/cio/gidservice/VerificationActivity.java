package com.cio.gidservice;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.cio.gidservice.UIServices.CodeInput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VerificationActivity extends AppCompatActivity {

    List<EditText> codeFields = new ArrayList<>();
    CodeInput codeInput;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification_page);

        codeFields.add(findViewById(R.id.firstNumberCode));
        codeFields.add(findViewById(R.id.secondNumberCode));
        codeFields.add(findViewById(R.id.thirdNumberCode));
        codeFields.add(findViewById(R.id.fourthNumberCode));

        codeInput = new CodeInput(codeFields, () -> {
            Intent intent = new Intent(this, EnterNameActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.imageButton).setOnClickListener(v -> codeInput.enterDigit("1"));
        findViewById(R.id.imageButton2).setOnClickListener(v -> codeInput.enterDigit("2"));
        findViewById(R.id.imageButton3).setOnClickListener(v -> codeInput.enterDigit("3"));
        findViewById(R.id.imageButton4).setOnClickListener(v -> codeInput.enterDigit("4"));
        findViewById(R.id.imageButton5).setOnClickListener(v -> codeInput.enterDigit("6"));
        findViewById(R.id.imageButton6).setOnClickListener(v -> codeInput.enterDigit("5"));
        findViewById(R.id.imageButton7).setOnClickListener(v -> codeInput.enterDigit("7"));
        findViewById(R.id.imageButton8).setOnClickListener(v -> codeInput.enterDigit("8"));
        findViewById(R.id.imageButton9).setOnClickListener(v -> codeInput.enterDigit("9"));
        findViewById(R.id.imageButton11).setOnClickListener(v -> codeInput.enterDigit("0"));

        findViewById(R.id.erase_button).setOnClickListener(v -> codeInput.eraseDigit());
        findViewById(R.id.clear_button).setOnClickListener(v -> codeInput.clearAll());
    }

}
