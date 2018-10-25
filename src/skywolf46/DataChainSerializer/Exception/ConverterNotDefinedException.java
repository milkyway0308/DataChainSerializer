package skywolf46.DataChainSerializer.Exception;

public class ConverterNotDefinedException extends RuntimeException{
    public ConverterNotDefinedException(Class t){
        super("Cannot convert " + t.getName() + " - ClassConverter of " + t.getName() + " not defined");
    }

    public ConverterNotDefinedException(String n){
        super(n);
    }


}
