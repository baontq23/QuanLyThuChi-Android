package com.baontq.asm.DTO;

public class LoaiThu {
    private int id;
    private String name;

    public LoaiThu(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public LoaiThu() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
