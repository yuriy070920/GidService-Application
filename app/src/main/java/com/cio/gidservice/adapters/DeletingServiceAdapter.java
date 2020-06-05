package com.cio.gidservice.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cio.gidservice.R;
import com.cio.gidservice.dialogs.AfterProcess;
import com.cio.gidservice.dialogs.ErrorDialog;
import com.cio.gidservice.dialogs.QuestionDialog;
import com.cio.gidservice.dialogs.SuccessDialog;
import com.cio.gidservice.models.Service;
import com.cio.gidservice.network.DeleteAPIManager;
import com.cio.gidservice.network.RetrofitClientInstance;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeletingServiceAdapter extends RecyclerView.Adapter<DeletingServiceAdapter.ServiceViewHolder> {

    private View view;
    private List<Service> services;
    private Context context;
    private Activity activity;
    private AfterProcess process;

    public DeletingServiceAdapter(Context context, List<Service> services, Activity activity, AfterProcess process) {
        this.context = context;
        this.services = services;
        this.activity = activity;
        this.process = process;
    }

    @NonNull
    @Override
    public DeletingServiceAdapter.ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delete_service_item, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeletingServiceAdapter.ServiceViewHolder holder, int position) {
        holder.name.setText(services.get(position).getName());
        holder.price.setText(String.valueOf(services.get(position).getPrice()));
        Glide.with(context)
                .asBitmap()
                .fitCenter()
                .placeholder(R.drawable.placeholder_image)
                .load(services.get(position).getImageUrl())
                .into(holder.imageView);
        holder.cardView.setOnClickListener(v -> {
            QuestionDialog dialog = new QuestionDialog(activity, () -> {
                deleteService(services.get(position).getId());
            });
            dialog.start("Are you sure you want to delete service "+ services.get(position).getName() + "?\nYou will not able to recover data!");
        });
    }

    private void deleteService(Long id) {
        DeleteAPIManager apiManager = RetrofitClientInstance.getRetrofitInstance().create(DeleteAPIManager.class);
        apiManager.deleteService(id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    SuccessDialog dialog = new SuccessDialog(activity);
                    dialog.start("Organization was successfully deleted.\nPress OK to continue");
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                sleep(3000);
                                dialog.dismiss();
                                process.run();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    thread.start();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                ErrorDialog dialog = new ErrorDialog(activity);
                dialog.start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView name;
        private TextView price;
        private ImageView imageView;
        private CardView cardView;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            name = view.findViewById(R.id.deletingServiceName);
            price = view.findViewById(R.id.deletingServicePrice);
            imageView = view.findViewById(R.id.deletingServiceImageView);
            cardView = view.findViewById(R.id.deletingServiceCardView);
        }

        public View getView() {
            return view;
        }

        public void setView(View view) {
            this.view = view;
        }

        public TextView getName() {
            return name;
        }

        public void setName(TextView name) {
            this.name = name;
        }

        public TextView getPrice() {
            return price;
        }

        public void setPrice(TextView price) {
            this.price = price;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        public CardView getCardView() {
            return cardView;
        }

        public void setCardView(CardView cardView) {
            this.cardView = cardView;
        }
    }
}
