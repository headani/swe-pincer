package hu.unideb.inf.swe.pincer.bla;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Objects;

public class ItemOccurrenceBla {

    private String name;

    private final IntegerProperty occurrence = new SimpleIntegerProperty();

    public ItemOccurrenceBla() {
    }

    public ItemOccurrenceBla(String name, Integer occurrence) {
        this.name = name;
        this.occurrence.set(occurrence);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOccurrence() {
        return occurrence.get();
    }

    public IntegerProperty occurrenceProperty() {
        return occurrence;
    }

    public void setOccurrence(int occurrence) {
        this.occurrence.set(occurrence);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemOccurrenceBla that = (ItemOccurrenceBla) o;
        return Objects.equals(name, that.name) && Objects.equals(occurrence.get(), that.occurrence.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, occurrence.get());
    }

    @Override
    public String toString() {
        return "ItemOccurrenceBla{" +
                "name='" + name + '\'' +
                ", occurrence=" + occurrence.get() +
                '}';
    }
}
