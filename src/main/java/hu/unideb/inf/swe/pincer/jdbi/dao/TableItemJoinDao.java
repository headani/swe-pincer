package hu.unideb.inf.swe.pincer.jdbi.dao;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface TableItemJoinDao {

    @SqlUpdate("create table table_:id (item_name varchar(255) not null)")
    void createTable(@Bind("id") Integer id);

    @SqlUpdate("insert into table_:id (item_name) values (:name)")
    void insertItemToTable(@Bind("id") Integer id, @Bind("name") String name);

    @SqlUpdate("drop table table_:id")
    void dropTable(@Bind("id") Integer id);
}
