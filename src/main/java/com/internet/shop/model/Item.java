package com.internet.shop.model;

import java.util.Objects;

public class Item {
    private static Long idGenerator = 0L;
    private final Long id;
    private String name;
    private double price;
    private int count;

    public Item(String name, double price, int count) {
        id = ++idGenerator;
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return count == item.count && Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, count);
    }

    @Override
    public String toString() {
        return "Item{" + "id=" + id
                + ", name='" + name + '\''
                + ", price=" + price
                + ", count=" + count + '}';
    }
}
