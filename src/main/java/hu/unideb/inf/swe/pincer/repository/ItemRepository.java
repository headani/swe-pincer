package hu.unideb.inf.swe.pincer.repository;

import hu.unideb.inf.swe.pincer.jdbi.JdbiProvider;
import hu.unideb.inf.swe.pincer.jdbi.dao.ItemDao;
import hu.unideb.inf.swe.pincer.model.Item;
import org.jdbi.v3.core.Jdbi;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ItemRepository {

    private final Jdbi jdbi = JdbiProvider.getInstance();

    public void addItem(Item item) {
        jdbi.useExtension(ItemDao.class, dao -> dao.insertItem(item));
    }

    public void updateItem(String name, Integer price) {
        jdbi.useExtension(ItemDao.class, dao -> dao.updateItem(name, price));
    }

    public void deleteItem(String name) {
        jdbi.useExtension(ItemDao.class, dao -> dao.deleteItem(name));
    }

    public Item getItem(String name) {
        return jdbi.withExtension(ItemDao.class, dao -> dao.getItem(name));
    }

    public List<Item> itemList() {
        return jdbi.withExtension(ItemDao.class, dao -> Collections.unmodifiableList(dao.listItems()));
    }

    public Boolean itemExists(String name) {
        return jdbi.withExtension(ItemDao.class, dao -> dao.countItemNames(name)) == 0 ? Boolean.FALSE : Boolean.TRUE;
    }
}
