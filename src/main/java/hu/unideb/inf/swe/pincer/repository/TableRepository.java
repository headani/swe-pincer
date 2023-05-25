package hu.unideb.inf.swe.pincer.repository;

import hu.unideb.inf.swe.pincer.jdbi.JdbiProvider;
import hu.unideb.inf.swe.pincer.jdbi.dao.TableDao;
import hu.unideb.inf.swe.pincer.jdbi.dao.TableItemJoinDao;
import hu.unideb.inf.swe.pincer.model.Table;
import org.jdbi.v3.core.Jdbi;

import java.util.Collections;
import java.util.List;

public class TableRepository {

    private final Jdbi jdbi = JdbiProvider.getInstance();

    public Integer addTable(Table table) {
        Integer lastId = jdbi.withExtension(TableDao.class, dao ->  {
            dao.insertTable(table);
            return dao.lastInsertId();
        });
        jdbi.useExtension(TableItemJoinDao.class, dao -> dao.createTable(lastId));
        return lastId;
    }

    public void updateTable(Integer id, Integer x, Integer y) {
        jdbi.useExtension(TableDao.class, dao -> dao.updateTable(id, x, y));
    }

    public Table getTable(Integer id) {
        return jdbi.withExtension(TableDao.class, dao -> dao.getTable(id));
    }

    public Boolean tableExists(Integer id) {
        return jdbi.withExtension(TableDao.class, dao -> dao.countTableIds(id)) == 0 ? Boolean.FALSE : Boolean.TRUE;
    }

    public void deleteTable(Integer id) {
        jdbi.useExtension(TableDao.class, dao -> dao.deleteTable(id));
        jdbi.useExtension(TableItemJoinDao.class, dao -> dao.dropTable(id));
    }

    public List<Table> tableList() {
        return jdbi.withExtension(TableDao.class, dao -> Collections.unmodifiableList(dao.listTables()));
    }

    public List<String> tableItemList(Integer id) {
        return jdbi.withExtension(TableItemJoinDao.class, dao -> Collections.unmodifiableList(dao.listTableItems(id)));
    }

    public void addItemToTable(Integer id, String name) {
        jdbi.useExtension(TableItemJoinDao.class, dao -> dao.insertItemToTable(id, name));
    }

    public void emptyTable(Integer id) {
        jdbi.useExtension(TableItemJoinDao.class, dao -> dao.truncateTable(id));
    }
}
