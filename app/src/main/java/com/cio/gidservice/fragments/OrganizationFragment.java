package com.cio.gidservice.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cio.gidservice.R;
import com.cio.gidservice.adapters.ServiceRecyclerAdapter;
import com.cio.gidservice.models.Organization;

public class OrganizationFragment extends Fragment {

    private static final String TAG = "OrganizationFragment";

    View view;
    private Organization organization;
    private ServiceRecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    private Context context;


    public OrganizationFragment(Context context, Organization organization) {
        this.context = context;
        this.organization = organization;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.organization_description_fragment, container, false);

        //Setting name of organization
        TextView name = view.findViewById(R.id.name_organization_fragment);
        name.setText(organization.getName());

        //Setting description of organization
        TextView description = view.findViewById(R.id.description_organization_fragment);
        description.setText(organization.getDescription());

        //Setting rating of organization
        TextView rating = view.findViewById(R.id.rating_organization_fragment);
        rating.setText(String.valueOf(organization.getRating()));

        return view;
    }


}
