package com.baontq.asm.DTO;

public class KhoanThu {
    private int id, idLt;
    private String name, time;
    private double cost;

    public KhoanThu(int id, int idLc, String name, String time, double cost) {
        this.id = id;
        this.idLt = idLc;
        this.name = name;
        this.time = time;
        this.cost = cost;
    }

    public KhoanThu() {

    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdLt() {
        return idLt;
    }

    public void setIdLt(int idLc) {
        this.idLt = idLc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return name;
    }
}
