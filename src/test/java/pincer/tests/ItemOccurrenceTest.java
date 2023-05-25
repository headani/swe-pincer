package pincer.tests;

import hu.unideb.inf.swe.pincer.bla.ItemOccurrenceBla;
import hu.unideb.inf.swe.pincer.util.ItemOccurrenceHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class ItemOccurrenceTest {

    final String CICA = "cica";
    final String KUTYA = "kutya";
    final String LEPKE = "lepke";
    final String KUTYI = "kutyi";

    @Test
    void testOccurrenceCounter() {

        List<String> itemList = new ArrayList<>();
        itemList.add(CICA);
        itemList.add(CICA);
        itemList.add(KUTYA);
        itemList.add(KUTYA);
        itemList.add(CICA);
        itemList.add(LEPKE);
        itemList.add(KUTYI);

        List<ItemOccurrenceBla> result = ItemOccurrenceHelper.getOccurrences(itemList);

        assertEquals(3, result.stream().filter(o -> o.getName().equals(CICA)).findFirst().map(ItemOccurrenceBla::getOccurrence).get());
        assertEquals(2, result.stream().filter(o -> o.getName().equals(KUTYA)).findFirst().map(ItemOccurrenceBla::getOccurrence).get());
        assertEquals(1, result.stream().filter(o -> o.getName().equals(LEPKE)).findFirst().map(ItemOccurrenceBla::getOccurrence).get());
        assertEquals(1, result.stream().filter(o -> o.getName().equals(KUTYI)).findFirst().map(ItemOccurrenceBla::getOccurrence).get());
    }
}
