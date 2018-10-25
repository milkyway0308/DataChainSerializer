package skywolf46.DataChainSerializer;

import org.fusesource.jansi.AnsiConsole;
import skywolf46.DataChainSerializer.API.ClassConverter;
import skywolf46.DataChainSerializer.Converters.Dates.DateConverter;
import skywolf46.DataChainSerializer.Converters.Dates.GregorianCalendarConverter;
import skywolf46.DataChainSerializer.Converters.Primary.*;
import skywolf46.DataChainSerializer.Deserializers.ObjectDeserializer;
import skywolf46.DataChainSerializer.Deserializers.ObjectFieldDeserializer;
import skywolf46.DataChainSerializer.Enums.WriteType;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class DataChainSerializer {
    private static HashMap<String, ClassConverter> converters = new HashMap<>();

    private static final Class<?> finalSuperclass = Object.class;

    public static void setUp() {
        AnsiConsole.systemInstall();
        ObjectDeserializer.setUp();
        ObjectFieldDeserializer.setUp();
        registerConverter();
        for (WriteType wr : WriteType.values()) {

            // Nothing to do
        }
    }

    private static void registerConverter(){
        registerPrimary();
        registerDates();
    }

    private static void registerPrimary(){
        registerConverter(Byte.class,new ByteConverter());
        registerConverter(Character.class,new CharConverter());
        registerConverter(Double.class,new DoubleConverter());
        registerConverter(Float.class,new FloatConverter());
        registerConverter(Integer.class,new IntegerConverter());
        registerConverter(Short.class,new ShortConverter());
        registerConverter(String.class,new StringConverter());
    }

    private static void registerDates(){
        registerConverter(GregorianCalendar.class,new GregorianCalendarConverter());
        registerConverter(Date.class,new DateConverter());
    }
    public static ObjectDeserializer deserialize(File f) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            ObjectDeserializer des = ObjectDeserializer.getDeserializer(ois.readInt()).getNewDeserializer();
            des.deserialize(ois);
            return des;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static ObjectDeserializer readNext(ObjectInputStream ois, boolean exitOnEOF) {
        try {
            // Locking stream while avaliable.
            if (ois.available() <= 0) {
                if (exitOnEOF)
                    return null;
                while (ois.available() <= 0)
                    Thread.sleep(0);
            }
            int id = ois.readInt();
            ObjectDeserializer des = ObjectDeserializer.getDeserializer(id).getNewDeserializer();
            des.deserialize(ois);
            return des;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static <T> void registerConverter(Class<? extends T> cl,ClassConverter<T> convert){
        converters.put(cl.getName(),convert);
    }

    public static ClassConverter getConverter(Class t){
        ClassConverter conv = converters.get(t.getName());
        Class lastClass = t;
        while (conv == null){
            Class c = t.getSuperclass();
            if(lastClass.equals(t))
                return null;
            conv = converters.get(c.getName());
        }
        return conv;
    }

    public static ClassConverter getConverter(String className){
        return converters.get(className);
    }

    public static Class getConvertable(Class t){
        ClassConverter conv = converters.get(t.getName());
        Class lastClass = t;
        while (conv == null){
            Class c = t.getSuperclass();
            if(lastClass.equals(t))
                return null;
            conv = converters.get(c.getName());
        }
        return t;
    }
}
