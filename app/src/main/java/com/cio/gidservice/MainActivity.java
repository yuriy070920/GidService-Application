package com.cio.gidservice;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_registration_page);

        final EditText phone = findViewById(R.id.phoneTextField);
        final EditText pass = findViewById(R.id.passwordTextField);

    }

    public void handleClickMain(View view) {
        final EditText phone = findViewById(R.id.phoneTextField);
        final EditText pass = findViewById(R.id.passwordTextField);

        if(phone.getText().toString().equals("+380931262912") && pass.getText().toString().equals("123456")){
            Intent intent = new Intent(this, VerificationActivity.class);
            startActivity(intent);
        }
    }
}
