package hu.unideb.inf.swe.pincer.jdbi;

import hu.unideb.inf.swe.pincer.jdbi.dao.ItemDao;
import hu.unideb.inf.swe.pincer.jdbi.dao.TableDao;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class InitializeDatabase {

    private InitializeDatabase() {
    }

    private static final Jdbi jdbi = JdbiProvider.getInstance();

    public static void Initialize() {
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.useExtension(ItemDao.class, ItemDao::createTable);
        jdbi.useExtension(TableDao.class, TableDao::createTable);
    }
}
