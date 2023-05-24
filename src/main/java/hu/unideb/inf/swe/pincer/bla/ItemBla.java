package hu.unideb.inf.swe.pincer.bla;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Objects;

public class ItemBla {

    public ItemBla() {
    }

    public ItemBla(String name, Integer price) {
        this.name = name;
        this.price.set(price);
    }

    private String name;

    private final IntegerProperty price = new SimpleIntegerProperty();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price.get();
    }

    public void setPrice(Integer price) {
        this.price.set(price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemBla itemBla = (ItemBla) o;
        return Objects.equals(name, itemBla.name) && Objects.equals(price.get(), itemBla.price.get());
    }

    @Override
    public String toString() {
        return "ItemBla{" +
                "name='" + name + '\'' +
                ", price=" + price.get() +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price.get());
    }

    public IntegerProperty priceProperty() {
        return price;
    }
}
