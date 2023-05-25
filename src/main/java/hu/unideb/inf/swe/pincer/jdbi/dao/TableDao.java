package hu.unideb.inf.swe.pincer.jdbi.dao;

import hu.unideb.inf.swe.pincer.model.Table;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface TableDao {

    @SqlUpdate("create table if not exists tables (id int auto_increment not null, x int not null, y int not null, primary key(id))")
    void createTable();

    @SqlQuery("select LAST_INSERT_ID()")
    Integer lastInsertId();

    @SqlUpdate("insert into tables (x, y) values (:x, :y)")
    void insertTable(@BindBean Table table);

    @SqlUpdate("delete from tables where id like :id")
    void deleteTable(@Bind("id") Integer id);

    @SqlUpdate("update tables set x = :x, y = :y where id = :id")
    void updateTable(@Bind("id") Integer id, @Bind("x") Integer x, @Bind("y") Integer y);

    @SqlQuery("select * from tables where id = :id")
    @RegisterBeanMapper(Table.class)
    Table getTable(@Bind("id") Integer id);

    @SqlQuery("select count(id) from tables where id = :id")
    Integer countTableIds(@Bind("id") Integer id);

    @SqlQuery("select * from tables order by id")
    @RegisterBeanMapper(Table.class)
    List<Table> listTables();
}
