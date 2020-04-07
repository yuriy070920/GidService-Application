package com.cio.gidservice.viewController;

import com.cio.gidservice.models.Organization;

import java.util.List;

public interface DataWorker {
    void save(List<?> objects);
    void getData();
}
