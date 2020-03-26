package com.cio.gidservice.viewModels;

import com.cio.gidservice.models.User;

public class UserViewModel {

    private User user;

    public UserViewModel(User user) {
        this.user = user;
    }

    public UserViewModel(Long id, String phoneNumber, String login, String password, String name) {
        user = new User(id, phoneNumber, login, password, name);
    }
}
