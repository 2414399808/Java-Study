package com.zsh.domain;

import java.util.Date;

public class Order {
    private int id;
    private Date ordertime;
    private double tolal;
    private User user;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", ordertime=" + ordertime +
                ", tolal=" + tolal +
                ", user=" + user +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public double getTolal() {
        return tolal;
    }

    public void setTolal(double tolal) {
        this.tolal = tolal;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
