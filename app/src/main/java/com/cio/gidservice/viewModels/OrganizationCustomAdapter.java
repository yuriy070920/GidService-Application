package com.cio.gidservice.viewModels;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cio.gidservice.R;
import com.cio.gidservice.RecyclerViewAdapter;
import com.cio.gidservice.models.Organization;

import java.util.List;

public class OrganizationCustomAdapter extends RecyclerView.Adapter<OrganizationCustomAdapter.OrganizationViewHolder> {

    private static final String TAG = "OrganizationCustomAdapter";

    private List<Organization> organizationList;
    private Context context;

    public OrganizationCustomAdapter(Context context, List<Organization> organizationList) {
        this.context = context;
        this.organizationList = organizationList;
    }

    class OrganizationViewHolder extends RecyclerView.ViewHolder{
        final View mView;

        private TextView name;
        private TextView description;
        private TextView rating;

        private OrganizationViewHolder(@NonNull View mView) {
            super(mView);
            this.mView = mView;
            name = mView.findViewById(R.id.name);
            description = mView.findViewById(R.id.description);
            rating = mView.findViewById(R.id.rating);
        }
    }

    @NonNull
    @Override
    public OrganizationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false);
        return new OrganizationCustomAdapter.OrganizationViewHolder(view);
    }


    @SuppressLint({"SetTextI18n", "ShowToast"})
    @Override
    public void onBindViewHolder(@NonNull OrganizationViewHolder holder, int position) {
        holder.name.setText(organizationList.get(position).getName());
        holder.description.setText(organizationList.get(position).getDescription());
        //holder.rating.setText(organizationList.get(position).getRating().toString());
        holder.description.setOnClickListener(v -> {
            Toast.makeText(context, organizationList.get(position).getName(), Toast.LENGTH_SHORT);
        });
    }

    @Override
    public int getItemCount() {
        return organizationList.size();
    }
}
