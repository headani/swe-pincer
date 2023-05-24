package hu.unideb.inf.swe.pincer.util.ex;

public class TableDoesNotExistException extends Throwable {

    public TableDoesNotExistException() {
        super("Table does not exist!");
    }
}
