package exception;

public class CyclicExceptionInCollections extends Exception{
    public CyclicExceptionInCollections() {
        super("Serializable class has a collection, which uses the same generic type as a class.");
    }
}
