package com.oscarhmg.orderit.model;

import java.util.List;

/**
 * Created by OscarHMG on 09/11/2017.
 */

public class Request {

    private String phone;
    private String name;
    private String address;
    private String total;
    // Status -> 0: Placed, 1: Shipping, 2: Shipped.
    private String status;
    private List<Order> foodsOrdered;

    public Request() {
    }

    public Request(String phone, String name, String address, String total, List<Order> foodsOrdered) {
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.total = total;
        this.foodsOrdered = foodsOrdered;
        this.status = "0"; //By default is not 'done'
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getFoodsOrdered() {
        return foodsOrdered;
    }

    public void setFoodsOrdered(List<Order> foodsOrdered) {
        this.foodsOrdered = foodsOrdered;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
