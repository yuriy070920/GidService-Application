package com.cio.gidservice.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cio.gidservice.R;
import com.cio.gidservice.activities.OrganizationActivity;
import com.cio.gidservice.adapters.ServiceRecyclerAdapter;
import com.cio.gidservice.models.Organization;
import com.cio.gidservice.models.Service;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;


public class OrganizationServicesFragment extends Fragment {

    private static final String TAG = "OrganizServicesFragment";

    private RecyclerView recyclerView;
    private ServiceRecyclerAdapter adapter;
    private Organization organization;
    private Context context;

    View view;


    public OrganizationServicesFragment(Context context, Organization organization) {
        this.context = context;
        this.organization = organization;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.organization_services_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewServices);
        ServiceRecyclerAdapter recyclerAdapter = new ServiceRecyclerAdapter(getContext(), organization.getServices());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //createDataForRecycler(organization.getServices());
    }

    private void createDataForRecycler(List<Service> services) {
        adapter = new ServiceRecyclerAdapter(this.context, services);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
