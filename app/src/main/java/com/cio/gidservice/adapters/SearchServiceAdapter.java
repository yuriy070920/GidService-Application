package com.cio.gidservice.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cio.gidservice.R;
import com.cio.gidservice.activities.DetailsSearchActivity;
import com.cio.gidservice.models.Service;
import com.cio.gidservice.network.RetrofitClientInstance;
import com.cio.gidservice.network.SearchAPIManager;
import com.google.gson.GsonBuilder;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchServiceAdapter extends RecyclerView.Adapter<SearchServiceAdapter.SearchServiceViewHolder> {

    private View view;
    private List<Service> services;
    private Context context;

    private static final String TAG = "SearchServiceAdapter";

    public SearchServiceAdapter(Context context, List<Service> services) {
        this.services = services;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_search_item, parent, false);
        return new SearchServiceAdapter.SearchServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchServiceViewHolder holder, int position) {
        //Загружаем имя
        holder.name.setText(services.get(position).getName());
        //Загружаем цену
        holder.price.setText(String.valueOf(services.get(position).getPrice()));
        //Загружаем картинку
        Glide.with(context)
                .asBitmap()
                .fitCenter()
                .load(services.get(position).getImageUrl())
                .placeholder(R.drawable.placeholder_image)
                .into(holder.image);
        LatLng latLng = new LatLng();
        //Добавляем слушателя для всей области элемента
        holder.cardView.setOnClickListener(v -> {
            //Создаем клиент для получения местоположения
            SearchAPIManager apiManager = RetrofitClientInstance.getRetrofitInstance().create(SearchAPIManager.class);
            //Отправляем запрос на сервер
            apiManager.getLocationForService(services.get(position).getId()).enqueue(new Callback<Map<String, Double>>() {
                //Получили ответ
                @Override
                public void onResponse(Call<Map<String, Double>> call, Response<Map<String, Double>> response) {
                    //Если все удачно, то получаем данные и добавляем в переменную локации
                    if(response.isSuccessful()){
                        Map<String, Double> map = response.body();
                        try{
                            latLng.setLatitude(map.get("lat"));
                            latLng.setLongitude(map.get("lng"));
                            Log.d(TAG, "onResponse: latlng = " + latLng.toString());
                        } catch (NullPointerException e){
                            Toast.makeText(context, "Cannot get answer from request", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse: response none success");
                        }
                    }
                    //Создаем интент для запуска
                    Intent intent = new Intent(context, DetailsSearchActivity.class);
                    Log.d(TAG, "onBindViewHolder: latlng = " + latLng.toString());
                    String serviceString = new GsonBuilder().create().toJson(services.get(position));
                    intent.putExtra("lat", latLng.getLatitude());
                    intent.putExtra("lng", latLng.getLongitude());
                    intent.putExtra("service", serviceString);
                    view.getContext().startActivity(intent);
                }

                //Добавляем тост, об ошибке
                @Override
                public void onFailure(Call<Map<String, Double>> call, Throwable t) {
                    Toast.makeText(context, "No location data", Toast.LENGTH_SHORT).show();
                    latLng.setLongitude(0);
                    latLng.setLatitude(0);
                    Log.d(TAG, "onFailure: failure with getting location");
                    //Создаем интент для запуска
                    Intent intent = new Intent(context, DetailsSearchActivity.class);
                    Log.d(TAG, "onBindViewHolder: latlng = " + latLng.toString());
                    String serviceString = new GsonBuilder().create().toJson(services.get(position));
                    intent.putExtra("lat", latLng.getLatitude());
                    intent.putExtra("lng", latLng.getLongitude());
                    intent.putExtra("service", serviceString);
                    view.getContext().startActivity(intent);
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class SearchServiceViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView name;
        private TextView price;
        private ImageView image;
        private CardView cardView;

        public SearchServiceViewHolder(View view) {
            super(view);
            this.view = view;
            name = view.findViewById(R.id.searchName);
            price = view.findViewById(R.id.searchPrice);
            image = view.findViewById(R.id.searchImageView);
            cardView = view.findViewById(R.id.searchCardView);
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

        public ImageView getImage() {
            return image;
        }

        public void setImage(ImageView image) {
            this.image = image;
        }

        public CardView getCardView() {
            return cardView;
        }

        public void setCardView(CardView cardView) {
            this.cardView = cardView;
        }
    }

}
