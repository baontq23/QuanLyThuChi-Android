package com.baontq.asm.DTO;

public class KhoanChi {
    private int id, idLc;
    private String name, time;
    private double cost;

    public KhoanChi(int id, int idLc, String name, String time, double cost) {
        this.id = id;
        this.idLc = idLc;
        this.name = name;
        this.time = time;
        this.cost = cost;
    }

    public KhoanChi() {
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

    public int getIdLc() {
        return idLc;
    }

    public void setIdLc(int idLc) {
        this.idLc = idLc;
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
}
