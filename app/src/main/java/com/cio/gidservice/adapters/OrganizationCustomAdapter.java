package com.cio.gidservice.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cio.gidservice.R;
import com.cio.gidservice.activities.MainActivity;
import com.cio.gidservice.activities.OrganizationActivity;
import com.cio.gidservice.models.Organization;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrganizationCustomAdapter extends RecyclerView.Adapter<OrganizationCustomAdapter.OrganizationViewHolder> {

    private static final String TAG = "OrganizationCustomAdapter";

    private List<Organization> organizationList;
    private Context context;

    public OrganizationCustomAdapter(Context context, List<Organization> organizationList) {
        this.context = context;
        this.organizationList = organizationList;
    }

    class OrganizationViewHolder extends RecyclerView.ViewHolder {

        final View mView;

        private TextView name;
        private TextView description;
        private TextView rating;
        private CircleImageView image;

        private OrganizationViewHolder(@NonNull View mView) {
            super(mView);
            this.mView = mView;
            name = mView.findViewById(R.id.name);
            description = mView.findViewById(R.id.description);
            rating = mView.findViewById(R.id.rating);
            image = mView.findViewById(R.id.organization_image);
        }
    }

    @NonNull
    @Override
    public OrganizationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false);
        return new OrganizationCustomAdapter.OrganizationViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrganizationViewHolder holder, int position) {
        holder.name.setText(organizationList.get(position).getName());
        holder.description.setText(organizationList.get(position).getDescription());
        try{
            holder.rating.setText(organizationList.get(position).getRating().toString());
        }catch (NullPointerException e) {
            holder.rating.setText("0");
        }
        holder.description.setOnClickListener(v -> {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            String forSave = gson.toJson(organizationList.get(position));
            Intent intent = new Intent(context, OrganizationActivity.class);
            intent.putExtra("organization", forSave);
            context.startActivity(intent);
            Toast.makeText(context, organizationList.get(position).getName(), Toast.LENGTH_SHORT).show();
        });
        Glide.with(context)
                .asBitmap()
                .load(organizationList.get(position).getImageUrl())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return organizationList.size();
    }
}
