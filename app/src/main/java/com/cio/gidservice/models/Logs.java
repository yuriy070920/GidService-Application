package com.cio.gidservice.models;

import java.time.LocalDateTime;


public class Logs {

    private Long id;
    private Long userID;
    private String password;
    private LocalDateTime time;
    private String ip;

    public Logs(Long userID, String password, LocalDateTime localDateTime, String ip) {
        this.userID = userID;
        this.password = password;
        this.time = localDateTime;
        this.ip = ip;
    }

    public Logs(Long id, Long userID, String password, LocalDateTime time, String ip) {
        this.id = id;
        this.userID = userID;
        this.password = password;
        this.time = time;
        this.ip = ip;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
