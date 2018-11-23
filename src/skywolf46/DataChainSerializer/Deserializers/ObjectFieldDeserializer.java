package skywolf46.DataChainSerializer.Deserializers;


import skywolf46.DataChainSerializer.Data.VariableReader;
import skywolf46.DataChainSerializer.Enums.WriteType;
import skywolf46.DataChainSerializer.Util.StreamStringUtil;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ObjectFieldDeserializer extends ObjectDeserializer {
    private LinkedHashMap<String, VariableReader> readers = new LinkedHashMap<>();

    private LinkedHashMap<String, ObjectDeserializer> ofd = new LinkedHashMap<>();

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
            String n = StreamStringUtil.readString(ois);
            VariableReader vr = new VariableReader(ois);
            readers.put(n, vr);
        }
        for (int i = 0; i < desSize; i++) {
            String fieldName = StreamStringUtil.readString(ois);
            int id = ois.readInt();
            int byteLength = ois.readInt();
            int varLength = ois.readInt();
            int serLength = ois.readInt();
            ObjectDeserializer des = getDeserializer(id);
            if (des == null) {
                ois.skipBytes(byteLength);
                continue;
            }
            des.readCoreData(byteLength, varLength, serLength,0);
            des.deserialize(ois);
            ofd.put(fieldName, des);
        }
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

    public String getString(String key){
        VariableReader reader = getReader(key);
        if(reader.getType() != WriteType.STRING)
            throw new ClassCastException("DataChainSerializer | Type of Reader(Key \"" + key + "\") is not string");
        return (String) reader.getObject();
    }

    public int getInteger(String key){
        VariableReader reader = getReader(key);
        if(reader.getType() != WriteType.INTEGER)
            throw new ClassCastException("DataChainSerializer | Type of Reader(Key \"" + key + "\") is not integer");
        return (int) reader.getObject();
    }

    public boolean getBoolean(String key){
        VariableReader reader = getReader(key);
        if(reader.getType() != WriteType.BOOLEAN)
            throw new ClassCastException("DataChainSerializer | Type of Reader(Key \"" + key + "\") is not boolean");
        return (boolean) reader.getObject();
    }

    public float getFloat(String key){
        VariableReader reader = getReader(key);
        if(reader.getType() != WriteType.FLOAT)
            throw new ClassCastException("DataChainSerializer | Type of Reader(Key \"" + key + "\") is not float");
        return (float) reader.getObject();
    }

    public double getDouble(String key){
        VariableReader reader = getReader(key);
        if(reader.getType() != WriteType.DOUBLE)
            throw new ClassCastException("DataChainSerializer | Type of Reader(Key \"" + key + "\") is not double");
        return (double) reader.getObject();
    }

    public byte getByte(String key){
        VariableReader reader = getReader(key);
        if(reader.getType() != WriteType.BOOLEAN)
            throw new ClassCastException("DataChainSerializer | Type of Reader(Key \"" + key + "\") is not byte");
        return (byte) reader.getObject();
    }

    public short getShort(String key){
        VariableReader reader = getReader(key);
        if(reader.getType() != WriteType.SHORT)
            throw new ClassCastException("DataChainSerializer | Type of Reader(Key \"" + key + "\") is not short");
        return (short) reader.getObject();
    }

    public char getChar(String key){
        VariableReader reader = getReader(key);
        if(reader.getType() != WriteType.CHAR)
            throw new ClassCastException("DataChainSerializer | Type of Reader(Key \"" + key + "\") is not char");
        return (char) reader.getObject();
    }

    public char[] getChars(String key){
        VariableReader reader = getReader(key);
        if(reader.getType() != WriteType.BOOLEAN)
            throw new ClassCastException("DataChainSerializer | Type of Reader(Key \"" + key + "\") is not char array(chars)");
        return (char[]) reader.getObject();
    }

    public byte[] getBytes(String key){
        VariableReader reader = getReader(key);
        if(reader.getType() != WriteType.BOOLEAN)
            throw new ClassCastException("DataChainSerializer | Type of Reader(Key \"" + key + "\") is not byte");
        return (byte[]) reader.getObject();
    }



    @Override
    public void clear() {
        super.clear();
        readers.clear();
        ofd.clear();
    }
}
