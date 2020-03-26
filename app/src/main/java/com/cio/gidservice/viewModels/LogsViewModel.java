package com.cio.gidservice.viewModels;

import com.cio.gidservice.models.Logs;

import java.time.LocalDateTime;

public class LogsViewModel {

    private Logs logs;

    public LogsViewModel(Logs logs) {
        this.logs = logs;
    }

    public LogsViewModel(Long id, Long userID, String password, LocalDateTime time, String ip) {
        logs.setId(id);
        logs.setIp(ip);
        logs.setPassword(password);
        logs.setTime(time);
        logs.setUserID(userID);
    }
}
