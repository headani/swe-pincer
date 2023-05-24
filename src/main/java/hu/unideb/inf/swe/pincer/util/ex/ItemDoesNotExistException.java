package hu.unideb.inf.swe.pincer.util.ex;

public class ItemDoesNotExistException extends Throwable {

    public ItemDoesNotExistException() {
        super("Item does not exist!");
    }
}
