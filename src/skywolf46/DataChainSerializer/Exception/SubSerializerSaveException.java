package skywolf46.DataChainSerializer.Exception;

public class SubSerializerSaveException extends RuntimeException {
    public SubSerializerSaveException() {
        super("SubSerializer can't save as instance.");
    }
}
