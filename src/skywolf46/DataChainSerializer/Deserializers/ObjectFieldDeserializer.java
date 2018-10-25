package skywolf46.DataChainSerializer.Deserializers;


import skywolf46.DataChainSerializer.Data.VariableReader;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ObjectFieldDeserializer extends ObjectDeserializer {
    private HashMap<String, VariableReader> readers = new HashMap<>();

    private HashMap<String, ObjectDeserializer> ofd = new HashMap<>();

    public ObjectFieldDeserializer() {

    }

    public static void setUp() {
        try {
            new ObjectFieldDeserializer().init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObjectDeserializer getNewDeserializer() {
        return new ObjectFieldDeserializer();
    }

    @Override
    public String getDeserializeName() {
        return "ObjectFieldDeserializer-Fielded Deserializer";
    }

    @Override
    protected void readDeserializeData(ObjectInputStream ois) throws Exception {
        clear();
        int size = ois.readInt();
        int desSize = ois.readInt();
        for (int i = 0; i < size; i++) {
            String n = readString(ois);
            VariableReader vr = new VariableReader(ois,new AtomicInteger(size));
            readers.put(n, vr);
        }
        for (int i = 0; i < desSize; i++) {
            String fieldName = readString(ois);
            int id = ois.readInt();
            int byteLength = ois.readInt();
            int varLength = ois.readInt();
            int serLength = ois.readInt();
            ObjectDeserializer des = getDeserializer(id);
            if (des == null) {
                ois.skipBytes(byteLength);
                continue;
            }
            des.readCoreData(byteLength, varLength, serLength);
            des.deserialize(ois);
            ofd.put(fieldName, des);
        }
    }

    private String readString(ObjectInputStream ois) throws Exception {
        short s = ois.readShort();
        char[] c = new char[s];
        for (int v = 0; v < c.length; v++)
            c[v] = ois.readChar();
        return new String(c);
    }

    public int getDeserializeID() {
        return "ObjectFieldDeserializer-Fielded Deserializer".hashCode();
    }

    public ObjectDeserializer getFieldDeserializer(String name) {
        return ofd.get(name);
    }

    public List<String> getKeys() {
        return new ArrayList<>(readers.keySet());
    }

    public List<String> getDeserializerKeys() {
        return new ArrayList<>(ofd.keySet());
    }

    public VariableReader getReader(String name) {
        return readers.get(name);
    }

    @Override
    public void clear() {
        super.clear();
        readers.clear();
        ofd.clear();
    }
}
