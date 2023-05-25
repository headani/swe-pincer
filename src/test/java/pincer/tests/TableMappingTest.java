package pincer.tests;

import hu.unideb.inf.swe.pincer.bla.TableBla;
import hu.unideb.inf.swe.pincer.model.Table;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TableMappingTest {

    @Test
    void testTableMapping1() {

        Table table = new Table();
        table.setId(1);
        table.setX(100);
        table.setY(100);

        List<String> items = new ArrayList<>();
        items.add("Baracklé");
        items.add("Chips");

        TableBla bla = new TableBla(table, items);

        assertEquals(1, bla.getId());
        assertEquals(100, bla.getX());
        assertEquals(100, bla.getY());
        assertFalse(bla.getState());
        assertEquals(2, bla.getItems().size());
    }

    @Test
    void testTableMapping2() {

        Table table = new Table();
        table.setId(2);
        table.setX(200);
        table.setY(200);

        List<String> items = Collections.emptyList();

        TableBla bla = new TableBla(table, items);

        assertEquals(2, bla.getId());
        assertEquals(200, bla.getX());
        assertEquals(200, bla.getY());
        assertTrue(bla.getState());
        assertEquals(0, bla.getItems().size());
    }
}
