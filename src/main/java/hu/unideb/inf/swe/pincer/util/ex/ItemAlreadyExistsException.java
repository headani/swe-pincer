package hu.unideb.inf.swe.pincer.util.ex;

public class ItemAlreadyExistsException extends Throwable {
    public ItemAlreadyExistsException() {
        super("Item already exists!");
    }
}
