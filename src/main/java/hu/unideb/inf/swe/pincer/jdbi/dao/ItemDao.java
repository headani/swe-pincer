package hu.unideb.inf.swe.pincer.jdbi.dao;

import hu.unideb.inf.swe.pincer.model.Item;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

public interface ItemDao {

    @SqlUpdate("create table if not exists items (id int auto_increment not null, name varchar(255) not null unique, price int not null, primary key(id))")
    void createTable();

    @SqlUpdate("insert into items (name, price) values (:name, :price)")
    void insertItem(@BindBean Item item);

    @SqlUpdate("delete from items where name like :name")
    void deleteItem(@Bind("name") String name);

    @SqlUpdate("update items set price = :price where name = :name")
    void updateItem(@Bind("name") String name, @Bind("price") Integer price);

    @SqlQuery("select * from items where name = :name")
    @RegisterBeanMapper(Item.class)
    Item getItem(@Bind("name") String name);

    @SqlQuery("select count(name) from items where name = :name")
    Integer countItemNames(@Bind("name") String name);

    @SqlQuery("select * from items order by name")
    @RegisterBeanMapper(Item.class)
    List<Item> listItems();
}
