package hu.unideb.inf.swe.pincer.jdbi;

import org.jdbi.v3.core.Jdbi;

public class JdbiProvider {

    private JdbiProvider() {
    }

    private static final Jdbi instance = Jdbi.create("jdbc:mysql://localhost:3306/pincer", "root", "root");

    public static Jdbi getInstance() {
        return instance;
    }
}
