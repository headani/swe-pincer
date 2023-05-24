package hu.unideb.inf.swe.pincer.bla;

import hu.unideb.inf.swe.pincer.model.Table;

import java.util.List;
import java.util.Objects;

public class TableBla {

    public TableBla() {
    }

    public TableBla(Table table, List<String> items) {
        this.id = table.getId();
        this.x = table.getX();
        this.y = table.getY();
        this.state = items.isEmpty();
        this.items = items;
    }

    private Integer id;

    private Integer x;

    private Integer y;

    private Boolean state;

    private List<String> items;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableBla tableBla = (TableBla) o;
        return Objects.equals(id, tableBla.id) && Objects.equals(x, tableBla.x) && Objects.equals(y, tableBla.y) && Objects.equals(state, tableBla.state) && Objects.equals(items, tableBla.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y, state, items);
    }

    @Override
    public String toString() {
        return "TableBla{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", state=" + state +
                ", items=" + items +
                '}';
    }
}
