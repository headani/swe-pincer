package hu.unideb.inf.swe.pincer.util;

import java.util.ArrayList;

public class TableStatePropertyList extends ArrayList<TableStateProperty> {

    private TableStatePropertyList() {
    }

    private static final TableStatePropertyList INSTANCE = new TableStatePropertyList();

    public static TableStatePropertyList getInstance() {
        return INSTANCE;
    }
}
