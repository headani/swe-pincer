package hu.unideb.inf.swe.pincer.service;

import hu.unideb.inf.swe.pincer.bla.TableBla;
import hu.unideb.inf.swe.pincer.model.Table;
import hu.unideb.inf.swe.pincer.repository.TableRepository;
import hu.unideb.inf.swe.pincer.util.Coordinates;
import hu.unideb.inf.swe.pincer.util.ex.TableDoesNotExistException;

import java.util.List;
import java.util.stream.Collectors;

public class TableService {

    private final TableRepository tableRepository = new TableRepository();
    private final ItemService itemService = new ItemService();

    public TableBla getTableById(Integer id) throws TableDoesNotExistException {
        if (tableRepository.tableExists(id)) {
            Table table = tableRepository.getTable(id);
            List<String> items = tableRepository.tableItemList(id);
            return new TableBla(table, items);
        } else {
            throw new TableDoesNotExistException();
        }
    }

    public Coordinates getTableCoordinates(Integer id) throws TableDoesNotExistException {
        if (tableRepository.tableExists(id)) {
            Table table = tableRepository.getTable(id);
            return new Coordinates(table.getX(), table.getY());
        } else {
            throw new TableDoesNotExistException();
        }
    }

    public void updateTablePosition(Integer id, Coordinates coordinates) throws TableDoesNotExistException {
        if (tableRepository.tableExists(id)) {
            tableRepository.updateTable(id, coordinates.getX(), coordinates.getY());
        } else {
            throw new TableDoesNotExistException();
        }
    }

    public void deleteTable(Integer id) throws TableDoesNotExistException {
        if (tableRepository.tableExists(id)) {
            tableRepository.deleteTable(id);
        } else {
            throw new TableDoesNotExistException();
        }
    }

    public Integer addNewTable(Coordinates coordinates) {
        Table table = new Table();
        table.setX(coordinates.getX());
        table.setY(coordinates.getY());
        return tableRepository.addTable(table);
    }

    public List<TableBla> getAllTables() {
        return tableRepository.tableList().stream().map(t -> new TableBla(t, tableRepository.tableItemList(t.getId()))).collect(Collectors.toUnmodifiableList());
    }

    public Integer getSubtotalForTable(Integer id) throws TableDoesNotExistException {
        if (tableRepository.tableExists(id)) {
            return tableRepository.tableItemList(id).stream().map(itemService::getItemPriceByName).collect(Collectors.toUnmodifiableList()).stream().mapToInt(Integer::intValue).sum();
        } else {
            throw new TableDoesNotExistException();
        }
    }
}
