package com.cio.gidservice.requestEntities;


import com.cio.gidservice.models.Logs;
import java.time.LocalDateTime;

public class LogsRequestEntity extends Logs {

    public LogsRequestEntity(Long userID, String password, LocalDateTime localDateTime, String ip) {
        super(userID, password, localDateTime, ip);
    }

    public LogsRequestEntity(Long id, Long userID, String password, LocalDateTime time, String ip) {
        super(id, userID, password, time, ip);
    }
}
