package com.cio.gidservice.adapters;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.cio.gidservice.R;
import com.cio.gidservice.activities.AddServiceActivity;
import com.cio.gidservice.models.Organization;
import com.cio.gidservice.viewController.ClickListener;

import java.util.List;

public class SelectOrganizationRecyclerAdapter extends OrganizationCustomAdapter {

    public SelectOrganizationRecyclerAdapter(Context context, List<Organization> organizationList, ClickListener clickListener) {
        super(context, organizationList, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull OrganizationViewHolder holder, int position) {
        holder.getName().setText(organizationList.get(position).getName());
        holder.getDescription().setText(organizationList.get(position).getDescription());
        try{
            holder.getRating().setText(organizationList.get(position).getRating().toString());
        }catch (NullPointerException e) {
            holder.getRating().setText("0");
        }
        holder.getCardView().setOnClickListener(v -> {
            Intent intent = new Intent(super.context, AddServiceActivity.class)
                    .putExtra("organization_id", organizationList.get(position).getId());
            System.out.println(organizationList.get(position).getId());
            holder.mView.getContext().startActivity(intent);
        });
        Glide.with(context)
                .asBitmap()
                .fitCenter()
                .load(organizationList.get(position).getImageUrl())
                .placeholder(R.drawable.placeholder_image)
                .into(holder.getImage());
    }
}
