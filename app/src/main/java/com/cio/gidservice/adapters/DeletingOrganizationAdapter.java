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
import com.cio.gidservice.models.Organization;
import com.cio.gidservice.network.DeleteAPIManager;
import com.cio.gidservice.network.RetrofitClientInstance;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeletingOrganizationAdapter extends RecyclerView.Adapter<DeletingOrganizationAdapter.OrganizationViewHolder> {

    private Context context;
    private Activity activity;
    private List<Organization> organizations;
    private View view;
    private AfterProcess process;

    public DeletingOrganizationAdapter(Context context, List<Organization> organizations, Activity activity, AfterProcess process) {
        this.context = context;
        this.organizations = organizations;
        this.activity = activity;
        this.process = process;
    }


    @NonNull
    @Override
    public OrganizationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delete_organization_item, parent, false);
        return new OrganizationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrganizationViewHolder holder, int position) {
        holder.name.setText(organizations.get(position).getName());
        holder.rating.setText(String.valueOf(organizations.get(position).getRating()));
        Glide.with(context)
                .asBitmap()
                .fitCenter()
                .placeholder(R.drawable.placeholder_image)
                .load(organizations.get(position).getImageUrl())
                .into(holder.imageView);
        holder.cardView.setOnClickListener(v -> {
            QuestionDialog dialog = new QuestionDialog(activity, () -> {
                deleteOrganization(organizations.get(position).getId());
            });
            dialog.start("Are you sure you want to delete organization "+ organizations.get(position).getName() + "?\nYou will not able to recover data!");
        });
    }

    private void deleteOrganization(Long id) {
        DeleteAPIManager apiManager = RetrofitClientInstance.getRetrofitInstance().create(DeleteAPIManager.class);
        apiManager.deleteOrganization(id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    SuccessDialog dialog = new SuccessDialog(activity);
                    dialog.start("Organization was successfully deleted.\nPress OK to continue");
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                sleep(2000);
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
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(2000);
                            dialog.dismiss();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return organizations.size();
    }

    public class OrganizationViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView name;
        private TextView rating;
        private ImageView imageView;
        private CardView cardView;

        public OrganizationViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            this.name = view.findViewById(R.id.deletingOrganizationName);
            this.rating = view.findViewById(R.id.deletingOrganizationRating);
            this.imageView = view.findViewById(R.id.deletingOrganizationImageView);
            this.cardView = view.findViewById(R.id.deletingOrganizationCardView);
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

        public TextView getRating() {
            return rating;
        }

        public void setRating(TextView rating) {
            this.rating = rating;
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
