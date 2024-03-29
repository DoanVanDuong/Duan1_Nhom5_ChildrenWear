package com.example.duan1_nhom5.model;
public class SanPham {
    private int id;
    private String name;
    private int quantity;
    private int price;
    private String color;
    private String size;
    private String brand;

    public SanPham() {
    }

    public SanPham(int id, String name, int quantity, int price, String color, String size, String brand) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.color = color;
        this.size = size;
        this.brand = brand;
    }

    public SanPham(String name, int quantity, int price, String color, String size, String brand) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.color = color;
        this.size = size;
        this.brand = brand;
    }

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
