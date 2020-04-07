package com.cio.gidservice.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cio.gidservice.R;
import com.cio.gidservice.models.Service;
import com.cio.gidservice.viewModels.ServiceViewModel;


public class AddServiceActivity extends AppCompatActivity {

    EditText name_et;
    EditText description_et;
    EditText duration_et;
    EditText cost_et;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button save = findViewById(R.id.save_service_but);
        save.setOnClickListener(v -> {
            ServiceViewModel viewModel = new ServiceViewModel(new Service(name_et.getText().toString(),
                    description_et.getText().toString(),
                    Integer.valueOf(duration_et.getText().toString()),
                    Float.valueOf(cost_et.getText().toString()),
                    null));
            if(viewModel.save()) {
                Toast.makeText(this, "Saved successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
