package hu.unideb.inf.swe.pincer.repository;

import hu.unideb.inf.swe.pincer.jdbi.JdbiProvider;
import hu.unideb.inf.swe.pincer.jdbi.dao.ItemDao;
import hu.unideb.inf.swe.pincer.model.Item;
import org.jdbi.v3.core.Jdbi;

import java.util.Collections;
import java.util.List;

public class ItemRepository {

    private ItemRepository() {
    }

    private static final Jdbi jdbi = JdbiProvider.getInstance();

    public static void addItem(Item item) {
        jdbi.useExtension(ItemDao.class, dao -> dao.insertItem(item));
    }

    public static void updateItem(String name, Integer price) {
        jdbi.useExtension(ItemDao.class, dao -> dao.updateItem(name, price));
    }

    public static void deleteItem(String name) {
        jdbi.useExtension(ItemDao.class, dao -> dao.deleteItem(name));
    }

    public static List<Item> itemList() {
        return jdbi.withExtension(ItemDao.class, dao -> Collections.unmodifiableList(dao.listItems()));
    }
}
