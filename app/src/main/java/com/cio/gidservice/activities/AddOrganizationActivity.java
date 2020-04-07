package com.cio.gidservice.activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.cio.gidservice.R;
import com.cio.gidservice.dao.App;
import com.cio.gidservice.dao.AppDatabase;
import com.cio.gidservice.dao.OrganizationDao;
import com.cio.gidservice.models.Organization;
import com.cio.gidservice.viewModels.OrganizationViewModel;

import java.lang.ref.WeakReference;
import java.util.List;

public class AddOrganizationActivity extends AppCompatActivity {

    EditText name_et;
    EditText description_et;
    EditText imageUrl_et;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_organization_layout);

        name_et = findViewById(R.id.name_organization_input);
        description_et = findViewById(R.id.description_organization_input);
        imageUrl_et = findViewById(R.id.imageUrl_organization_input);

        Button save = findViewById(R.id.save_organization_but);
        save.setOnClickListener(v -> {
            Organization organization = new Organization(name_et.getText().toString(), description_et.getText().toString(), 0F, null, imageUrl_et.getText().toString());
            if(save(organization)) {
                Toast.makeText(this, "Saved successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean save(Organization organization) {
        try{
            AppDatabase db = App.getInstance().getDatabase();
            OrganizationDao orgDB = db.organizationDao();
            orgDB.insert(organization);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
