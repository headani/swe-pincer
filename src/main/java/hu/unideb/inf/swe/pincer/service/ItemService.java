package hu.unideb.inf.swe.pincer.service;

import hu.unideb.inf.swe.pincer.bla.ItemBla;
import hu.unideb.inf.swe.pincer.model.Item;
import hu.unideb.inf.swe.pincer.repository.ItemRepository;
import hu.unideb.inf.swe.pincer.util.ex.ItemAlreadyExistsException;
import hu.unideb.inf.swe.pincer.util.ex.ItemDoesNotExistException;

import java.util.List;
import java.util.stream.Collectors;

public class ItemService {

    private final ItemRepository itemRepository = new ItemRepository();
    private final TableService tableService = new TableService();

    public ItemBla getItemByName(String name) throws ItemDoesNotExistException {
        if (itemRepository.itemExists(name)) {
            Item item = itemRepository.getItem(name);
            return new ItemBla(item.getName(), item.getPrice());
        } else {
            throw new ItemDoesNotExistException();
        }
    }

    public Integer getItemPriceByName(String name) {
        if (itemRepository.itemExists(name)) {
            return itemRepository.getItem(name).getPrice();
        } else {
            return 0;
        }
    }

    public void updateItemPriceByName(String name, Integer price) throws ItemDoesNotExistException {
        if (itemRepository.itemExists(name)) {
            itemRepository.updateItem(name, price);
        } else {
            throw new ItemDoesNotExistException();
        }
    }

    public void deleteItemByName(String name) throws ItemDoesNotExistException {
        if (itemRepository.itemExists(name)) {
            itemRepository.deleteItem(name);
        } else {
            throw new ItemDoesNotExistException();
        }
    }

    public void addNewItem(ItemBla itemBla) throws ItemAlreadyExistsException {
        if (itemRepository.itemExists(itemBla.getName())) {
            throw new ItemAlreadyExistsException();
        } else {
            Item item = new Item();
            item.setName(itemBla.getName());
            item.setPrice(itemBla.getPrice());
            itemRepository.addItem(item);
        }
    }

    public List<ItemBla> getAllItems() {
        return itemRepository.itemList().stream().map(i -> new ItemBla(i.getName(), i.getPrice())).collect(Collectors.toUnmodifiableList());
    }
}
