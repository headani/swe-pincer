package hu.unideb.inf.swe.pincer.repository;

import hu.unideb.inf.swe.pincer.jdbi.JdbiProvider;
import hu.unideb.inf.swe.pincer.jdbi.dao.TableDao;
import hu.unideb.inf.swe.pincer.jdbi.dao.TableItemJoinDao;
import hu.unideb.inf.swe.pincer.model.Table;
import org.jdbi.v3.core.Jdbi;

import java.util.Collections;
import java.util.List;

public class TableRepository {

    private TableRepository() {
    }

    private static final Jdbi jdbi = JdbiProvider.getInstance();

    public static void addTable(Table table) {
        Integer lastId = jdbi.withExtension(TableDao.class, dao ->  {
            dao.insertTable(table);
            return dao.lastInsertId();
        });
        jdbi.useExtension(TableItemJoinDao.class, dao -> dao.createTable(lastId));
    }

    public static void updateTable(Integer id, Integer x, Integer y) {
        jdbi.useExtension(TableDao.class, dao -> dao.updateTable(id, x, y));
    }

    public static void deleteTable(Integer id) {
        jdbi.useExtension(TableDao.class, dao -> dao.deleteTable(id));
        jdbi.useExtension(TableItemJoinDao.class, dao -> dao.dropTable(id));
    }

    public static List<Table> tableList() {
        return jdbi.withExtension(TableDao.class, dao -> Collections.unmodifiableList(dao.listTables()));
    }
}
