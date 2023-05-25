package hu.unideb.inf.swe.pincer.util;

import java.util.LinkedList;

public class TableIdQueue extends LinkedList<Integer> {

    private TableIdQueue() {
    }

    private static final TableIdQueue INSTANCE = new TableIdQueue();

    public static TableIdQueue getInstance() {
        return INSTANCE;
    }
}
