package com.cio.gidservice.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cio.gidservice.R;
import com.cio.gidservice.activities.MainActivity;
import com.cio.gidservice.dao.App;
import com.cio.gidservice.dao.UserDao;
import com.cio.gidservice.models.User;
import com.cio.gidservice.models.UserProperties;
import com.cio.gidservice.network.RetrofitClientInstance;
import com.cio.gidservice.network.UserAPIManager;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton login = view.findViewById(R.id.login_next_button);
        login.setOnClickListener(v -> {
            EditText loginText = view.findViewById(R.id.phone_login_field);
            EditText passText = view.findViewById(R.id.password_login_field);

            String loginStr = loginText.getText().toString();
            String passwdStr = passText.getText().toString();
            addUser(loginStr, passwdStr);
        });
    }

    private void addUser(String login, String pass) {
        UserAPIManager apiManager = RetrofitClientInstance.getRetrofitInstance().create(UserAPIManager.class);

        User user = new User("", login, pass, "Admin");

        apiManager.login(user).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    UserDao db = App.getInstance().getDatabase().userDao();
                    try {
                        assert response.body() != null;
                        user.setId(Long.valueOf(response.body().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    db.addUser(user);
                    UserProperties.setIsAdmin(true);
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else {
                    if(response.body() != null)
                        Toast.makeText(getContext(), response.body().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Check your internet connection and try later!", Toast.LENGTH_LONG).show();
            }
        });
    }
}

/**
 * TODO:
 *  1. Доделать добавление организации на сервер.
 *  2. Доделать добавление сервисов в организацию:
 *      a) добавить агрумент MultipartData.
 *      b) сделать запрос и предусмотреть исключения
 *  3. Начать делать работу с картой:
 *      a) сделать вывод карты на экран
 *      b) сделать перемещение курсора
 *      c) сделать подтверждение метки
 *      d) сделать поиск места на карте
 *      f) сделать добавление места для организации
 */
