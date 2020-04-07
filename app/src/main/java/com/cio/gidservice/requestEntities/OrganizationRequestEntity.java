package com.cio.gidservice.requestEntities;

import com.cio.gidservice.models.Organization;
import com.cio.gidservice.models.Service;

import java.util.List;

public class OrganizationRequestEntity extends Organization {

    private String ip;

    public OrganizationRequestEntity(Long id, String name, String description, Float rating, List<Service> services, String imageUrl, String ip) {
        //super(id, name, description, rating, services, imageUrl);
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return super.toString() + ip;
    }

    /*
     * TODO:
     *  1.Сделать, чтобы на странице описание организации в шапке была фотографии организации
     *  2. Сделать Боковое меню
     *  3. Сделать чтобы привыборе "Добавить услугу", показывался список организаций
     *  4. Сделать, чтобы потом при нажатии на организацию открывалось окно добавление услуги
     *  5. Сделать добавление сервиса в компанию
     */
}
