package com.cio.gidservice.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cio.gidservice.R;
import com.cio.gidservice.models.Service;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ServiceRecyclerAdapter extends RecyclerView.Adapter<ServiceRecyclerAdapter.ServiceViewHolder> {

    private static final String TAG = "ServiceRecyclerAdapter";

    //список услуг какой-либо организации
    private List<Service> serviceList;
    //Контекст, из которого создан адаптер
    private Context mContext;

    public ServiceRecyclerAdapter(Context mContext, List<Service> serviceList) {
        this.serviceList = serviceList;
        this.mContext = mContext;
    }

    /**
     * Создание холдера, в котором отбраются элементы RecyclerView
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.service_recycler_item, parent, false);
        //Log.d(TAG, "onCreateViewHolder: " + view.);
        return new ServiceRecyclerAdapter.ServiceViewHolder(view);
    }

    /**
     * Создание элемента RecyclerView, в котором и будут отображаться данные класса Service
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + holder.duration);
        holder.name.setText(serviceList.get(position).getName());
        holder.description.setText(serviceList.get(position).getDescription());
        holder.duration.setText(String.valueOf(serviceList.get(position).getLeadTime()));
        holder.price.setText(String.valueOf(serviceList.get(position).getPrice()));
        Glide.with(mContext)
                .asBitmap()
                .load(serviceList.get(position).getImageUrl())
                .placeholder(R.drawable.placeholder_image)
                .into(holder.image);
    }

    /**
     * Получение количества элементов списка
     * @return Size of list of services
     */
    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    /**
     * Класс, в котором храняться UI элементы, в которые при создании адаптеры вписываются данные
     */
    class ServiceViewHolder extends RecyclerView.ViewHolder {

        View view;

        private TextView name;
        private TextView description;
        private TextView duration;
        private TextView price;
        private CircleImageView image;
        private CardView cardView;

        private ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            name = itemView.findViewById(R.id.service_recycler_name);
            description = itemView.findViewById(R.id.service_recycler_description);
            duration = itemView.findViewById(R.id.service_recycler_timeDuration);
            price = itemView.findViewById(R.id.service_recycler_cost);
            image = itemView.findViewById(R.id.service_recycler_image);
            cardView = itemView.findViewById(R.id.service_cardView);
        }
    }
}
