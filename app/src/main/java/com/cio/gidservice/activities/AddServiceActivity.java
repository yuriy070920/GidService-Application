package com.cio.gidservice.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cio.gidservice.R;
import com.cio.gidservice.dao.App;
import com.cio.gidservice.dao.AppDatabase;
import com.cio.gidservice.dao.ServiceDao;
import com.cio.gidservice.models.Service;


public class AddServiceActivity extends AppCompatActivity {

    EditText name_et;
    EditText description_et;
    EditText duration_et;
    EditText cost_et;
    EditText imageUrl_et;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_service_layout);

        //Setting header name
        setTitle("Add service");

        //Setting up button for returning to the previous layout
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Long orgId = getIntent().getLongExtra("organization_id", 1);

        name_et = findViewById(R.id.service_name_input);
        description_et = findViewById(R.id.service_duration_input);
        duration_et = findViewById(R.id.service_duration_input);
        cost_et = findViewById(R.id.service_cost_input);
        imageUrl_et = findViewById(R.id.service_url_input);


        Button save = findViewById(R.id.save_service_but);
        save.setOnClickListener(v -> {
            Service service = new Service(name_et.getText().toString(),
                    description_et.getText().toString(),
                    Integer.valueOf(duration_et.getText().toString()),
                    Float.valueOf(cost_et.getText().toString()),
                    imageUrl_et.getText().toString(),
                    orgId);
            if(save(service)) {
                Toast.makeText(this, "Saved successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean save(Service service) {
        try{
            AppDatabase db = App.getInstance().getDatabase();
            ServiceDao dao = db.serviceDao();
            dao.insert(service);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
