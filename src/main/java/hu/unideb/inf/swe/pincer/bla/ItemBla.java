package hu.unideb.inf.swe.pincer.bla;

import java.util.Objects;

public class ItemBla {

    public ItemBla() {
    }

    public ItemBla(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

    private String name;

    private Integer price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemBla itemBla = (ItemBla) o;
        return Objects.equals(name, itemBla.name) && Objects.equals(price, itemBla.price);
    }

    @Override
    public String toString() {
        return "ItemBla{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}
