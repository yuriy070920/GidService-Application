package com.cio.gidservice.requestEntities;

import com.cio.gidservice.models.User;

public class UserRequestEntity extends User {

    private String ip;

    public UserRequestEntity(Long id, String phoneNumber, String login, String password, String name) {
        super(phoneNumber, login, password, name);
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
