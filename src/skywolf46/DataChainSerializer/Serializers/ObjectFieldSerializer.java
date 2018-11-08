package skywolf46.DataChainSerializer.Serializers;


import skywolf46.DataChainSerializer.API.ClassConverter;
import skywolf46.DataChainSerializer.Data.VariableWriter;
import skywolf46.DataChainSerializer.DataChainSerializer;
import skywolf46.DataChainSerializer.Enums.WriteType;

import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

public class ObjectFieldSerializer extends ObjectSerializer {
    private LinkedHashMap<String, VariableWriter> varWriters = new LinkedHashMap<>();
    private LinkedHashMap<String,ObjectSerializer> ofd = new LinkedHashMap<>();

    public ObjectFieldSerializer() {

    }

    @Override
    public int getDeserializeID() {
        return "ObjectFieldDeserializer-Fielded Deserializer".hashCode();
    }

    @Override
    protected void writeSerializeData(ObjectOutputStream oos) throws Exception {
        oos.writeInt(varWriters.size());
        oos.writeInt(ofd.size());

        for (Map.Entry<String, VariableWriter> vr : varWriters.entrySet()) {
            writeString(oos, vr.getKey());
            vr.getValue().write(oos);
        }

        for(Map.Entry<String,ObjectSerializer> os : ofd.entrySet()){
            writeString(oos,os.getKey());
            os.getValue().writeSerializer(oos);
        }

    }

    public <T extends ObjectSerializer> T addSubSerializer(String serializerID,T ser){
        ofd.put(serializerID,ser);
        return ser;
    }

    private void writeString(ObjectOutputStream oos, String str) throws Exception {
        oos.writeShort(str.length());
        for (int i = 0; i < str.length(); i++)
            oos.writeChar(str.charAt(i));
    }

    public ObjectFieldSerializer writeInt(String key, int val) {
        if (varWriters.containsKey(key)) {
            VariableWriter vr = varWriters.get(key);
            subtractSize(vr.getSize());
        } else
            addFileSize(WriteType.STRING.getCalculator().calculateBytes(key.length())).addFileSize(2 /* String size short*/);
        addFileSize(4).addFileSize(2 /* String separator size*/);
        varWriters.put(key,
                new VariableWriter(WriteType.INTEGER, val, WriteType.INTEGER.getCalculator().calculateBytes(0)));
        return this;
    }

    public ObjectFieldSerializer writeChar(String key, char val) {
        if (varWriters.containsKey(key)) {
            VariableWriter vr = varWriters.get(key);
            subtractSize(vr.getSize());
        } else
            addFileSize(WriteType.STRING.getCalculator().calculateBytes(key.length())).addFileSize(2 /* String size short*/);
        addFileSize(4).addFileSize(2 /* String separator size*/);
        varWriters.put(key,
                new VariableWriter(WriteType.CHAR, val, WriteType.CHAR.getCalculator().calculateBytes(0)));
        return this;
    }

    public ObjectFieldSerializer writeChars(String key, String val) {
        if (varWriters.containsKey(key)) {
            VariableWriter vr = varWriters.get(key);
            subtractSize(vr.getSize());
        } else
            addFileSize(WriteType.STRING.getCalculator().calculateBytes(key.length())).addFileSize(2 /* String size short*/);
        addFileSize(val.length() * 2).addFileSize(2/* Array size separator size */).addFileSize(2 /* Variable separator size*/);
        varWriters.put(key,
                new VariableWriter(WriteType.CHARS, val, WriteType.CHARS.getCalculator().calculateBytes(val.length())));
        return this;
    }


    public ObjectFieldSerializer writeFloat(String key, float val) {
        if (varWriters.containsKey(key)) {
            VariableWriter vr = varWriters.get(key);
            subtractSize(vr.getSize());
        } else
            addFileSize(WriteType.STRING.getCalculator().calculateBytes(key.length())).addFileSize(2 /* String size short*/);
        addFileSize(4).addFileSize(2 /* Variable separator size*/);
        varWriters.put(key,
                new VariableWriter(WriteType.FLOAT, val, WriteType.FLOAT.getCalculator().calculateBytes(0)));
        return this;
    }

    public ObjectFieldSerializer writeDouble(String key, double val) {
        if (varWriters.containsKey(key)) {
            VariableWriter vr = varWriters.get(key);
            subtractSize(vr.getSize());
        } else
            addFileSize(WriteType.STRING.getCalculator().calculateBytes(key.length())).addFileSize(2 /* String size short*/);
        addFileSize(8).addFileSize(2 /* Variable separator size*/);
        varWriters.put(key,
                new VariableWriter(WriteType.DOUBLE, val, WriteType.DOUBLE.getCalculator().calculateBytes(0)));
        return this;
    }

    public ObjectFieldSerializer writeLong(String key, long val) {
        if (varWriters.containsKey(key)) {
            VariableWriter vr = varWriters.get(key);
            subtractSize(vr.getSize());
        } else
            addFileSize(WriteType.STRING.getCalculator().calculateBytes(key.length())).addFileSize(2 /* String size short*/);
        addFileSize(8).addFileSize(2 /* Variable separator size*/);
        varWriters.put(key,
                new VariableWriter(WriteType.LONG, val, WriteType.LONG.getCalculator().calculateBytes(0)));
        return this;
    }


    public ObjectFieldSerializer writeByte(String key, byte val) {
        if (varWriters.containsKey(key)) {
            VariableWriter vr = varWriters.get(key);
            subtractSize(vr.getSize());
        } else
            addFileSize(WriteType.STRING.getCalculator().calculateBytes(key.length())).addFileSize(2 /* String size short*/);
        addFileSize(4).addFileSize(2 /* Variable separator size*/);
        varWriters.put(key,
                new VariableWriter(WriteType.BYTE, val, WriteType.BYTE.getCalculator().calculateBytes(0)));
        return this;
    }

    public ObjectFieldSerializer writeBoolean(String key, boolean val) {
        if (varWriters.containsKey(key)) {
            VariableWriter vr = varWriters.get(key);
            subtractSize(vr.getSize());
        } else
            addFileSize(WriteType.STRING.getCalculator().calculateBytes(key.length())).addFileSize(2 /* String size short*/);
        addFileSize(1).addFileSize(2 /* Variable separator size*/);
        varWriters.put(key,
                new VariableWriter(WriteType.BOOLEAN, val, WriteType.BOOLEAN.getCalculator().calculateBytes(0)));
        return this;
    }


    public ObjectFieldSerializer writeShort(String key, short val) {
        if (varWriters.containsKey(key)) {
            VariableWriter vr = varWriters.get(key);
            subtractSize(vr.getSize());
        } else
            addFileSize(WriteType.STRING.getCalculator().calculateBytes(key.length())).addFileSize(2 /* String size short*/);
        addFileSize(4).addFileSize(2 /* Variable separator size*/);
        varWriters.put(key,
                new VariableWriter(WriteType.SHORT, val, WriteType.SHORT.getCalculator().calculateBytes(0)));
        return this;
    }

    public ObjectFieldSerializer writeString(String key, String val) {
        if (varWriters.containsKey(key)) {
            VariableWriter vr = varWriters.get(key);
            subtractSize(vr.getSize());
        } else
            addFileSize(WriteType.STRING.getCalculator().calculateBytes(key.length())).addFileSize(2 /* String size short*/);
        addFileSize(val.length() * 2).addFileSize(2/* Array size separator size */).addFileSize(2 /* Variable separator size*/);
        varWriters.put(key,
                new VariableWriter(WriteType.STRING, val, WriteType.STRING.getCalculator().calculateBytes(val.length())));
        return this;
    }

    public ObjectFieldSerializer writeBytes(String key, byte[] val) {
        if (varWriters.containsKey(key)) {
            VariableWriter vr = varWriters.get(key);
            subtractSize(vr.getSize());
        } else
            addFileSize(WriteType.STRING.getCalculator().calculateBytes(key.length())).addFileSize(2 /* String size short*/);
        addFileSize(val.length).addFileSize(2/* Array size separator size */).addFileSize(2 /* Variable separator size*/);
        varWriters.put(key,
                new VariableWriter(WriteType.BYTES, val, WriteType.BYTES.getCalculator().calculateBytes(val.length)));
        return this;
    }

    public ObjectSerializer writeObject(String key,Object obj){
        ClassConverter conv = DataChainSerializer.getConverter(obj.getClass());
        if(conv == null)
            throw new RuntimeException("Cannot serialize class " + obj.getClass() + " - ClassConverter not exist");
        VariableWriter vw = new VariableWriter(WriteType.CUSTOM_OBJECT,obj,0);
        varWriters.put(key,vw);
        addFileSize(vw.getSize()).addFileSize(2).addFileSize(2 * key.length());
        return this;
    }

    @Override
    public void clear() {
        super.clear();
        varWriters.clear();
        ofd.clear();
    }
}
