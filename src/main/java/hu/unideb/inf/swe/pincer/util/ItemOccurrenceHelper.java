package hu.unideb.inf.swe.pincer.util;

import hu.unideb.inf.swe.pincer.bla.ItemOccurrenceBla;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ItemOccurrenceHelper {

    private ItemOccurrenceHelper() {
    }

    public static List<ItemOccurrenceBla> getOccurrences(List<String> itemList) {
        Set<String> distinct = new HashSet<>(itemList);
        return distinct.stream().map(d -> new ItemOccurrenceBla(d, Collections.frequency(itemList, d))).collect(Collectors.toUnmodifiableList());
    }
}
