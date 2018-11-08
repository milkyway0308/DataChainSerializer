package skywolf46.DataChainSerializer.Serializers;

import skywolf46.DataChainSerializer.API.ClassConverter;
import skywolf46.DataChainSerializer.Data.VariableWriter;
import skywolf46.DataChainSerializer.DataChainSerializer;
import skywolf46.DataChainSerializer.Enums.WriteType;
import skywolf46.DataChainSerializer.Exception.ConverterNotDefinedException;
import skywolf46.DataChainSerializer.Exception.SubSerializerSaveException;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ObjectSerializer {
    private List<ObjectSerializer> nextSerializer = new ArrayList<>();

    private List<VariableWriter> writers = new ArrayList<>();

    private ObjectSerializer parentSerializier;

    private AtomicInteger size = new AtomicInteger(0);

    private AtomicInteger variableLength = new AtomicInteger(0);

    public ObjectSerializer() {
    }

    private ObjectSerializer(ObjectSerializer parent) {
        this.parentSerializier = parent;
    }

    public int getDeserializeID() {
        return "Deserializer=DefaultDeserializer".hashCode();
    }


    public ObjectSerializer createSubSerializer() {
        ObjectSerializer tser = new ObjectSerializer(this);
        nextSerializer.add(tser);
        return tser;
    }

    public <T extends ObjectSerializer> T addSubSerializer(T os) {
        nextSerializer.add(os);
        return os;
    }

    public void writeSerializer(ObjectOutputStream oos, boolean closeStream, boolean clearData) {
        if (parentSerializier != null)
            throw new SubSerializerSaveException();
        try {
            writeSerializer(oos);
            oos.flush();
            if (closeStream)
                oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (clearData) {
            clear();
        }
    }

    public void clear() {
        nextSerializer.clear();
        writers.clear();
        variableLength.set(0);
        size.set(0);
    }

    private void writeCoreData(ObjectOutputStream oos) throws Exception {
        oos.writeInt(getDeserializeID());
        oos.writeInt(getSize());
        oos.writeInt(getVariableLength());
        oos.writeInt(nextSerializer.size());
    }

    protected void writeSerializeData(ObjectOutputStream oos) throws Exception {

    }

    protected final void writeSerializer(ObjectOutputStream oos) throws Exception {
        writeCoreData(oos);
        writeSerializeData(oos);
        for (VariableWriter vw : writers)
            vw.write(oos);
        for (ObjectSerializer sub : nextSerializer)
            sub.writeSerializer(oos);
    }

    public final int getVariableLength() {
        return variableLength.get();
    }

    public final int getSize() {
        return size.get();
    }

    public int getSubSerializerLength() {
        return nextSerializer.size();
    }

    public ObjectSerializer writeInt(int data) {
        writers.add(new VariableWriter(WriteType.INTEGER, data
                , WriteType.INTEGER.getCalculator().calculateBytes(0)));
        addFileSize(4).addFileSize(2);
        variableLength.getAndIncrement();
        return this;
    }

    public ObjectSerializer writeString(String data) {
        writers.add(new VariableWriter(WriteType.STRING, data
                , WriteType.STRING.getCalculator().calculateBytes(data.length())));
        addFileSize(data.length() * 2).addFileSize(2).addFileSize(2);
        variableLength.getAndIncrement();
        return this;
    }

    public ObjectSerializer writeBytes(byte[] data) {
        writers.add(new VariableWriter(WriteType.BYTES, data,
                WriteType.BYTES.getCalculator().calculateBytes(data.length)));
        addFileSize(data.length).addFileSize(2).addFileSize(2);
        variableLength.getAndIncrement();
        return this;
    }

    public ObjectSerializer writeChars(String data) {
        writers.add(new VariableWriter(WriteType.CHARS, data,
                WriteType.CHARS.getCalculator().calculateBytes(data.length())));
        addFileSize(data.length() * 2).addFileSize(2).addFileSize(2);
        variableLength.getAndIncrement();
        return this;
    }

    public ObjectSerializer writeChar(char data) {
        writers.add(new VariableWriter(WriteType.CHAR, data,
                WriteType.CHAR.getCalculator().calculateBytes(0)));
        addFileSize(2).addFileSize(2);
        variableLength.getAndIncrement();
        return this;
    }


    public ObjectSerializer writeDouble(double data) {
        writers.add(new VariableWriter(WriteType.DOUBLE, data,
                WriteType.DOUBLE.getCalculator().calculateBytes(0)));
        addFileSize(8).addFileSize(2);
        variableLength.getAndIncrement();
        return this;
    }


    public ObjectSerializer writeShort(short data) {
        writers.add(new VariableWriter(WriteType.SHORT, data,
                WriteType.SHORT.getCalculator().calculateBytes(0)));
        addFileSize(2).addFileSize(2);
        variableLength.getAndIncrement();
        return this;
    }


    public ObjectSerializer writeLong(long data) {
        writers.add(new VariableWriter(WriteType.LONG, data,
                WriteType.LONG.getCalculator().calculateBytes(0)));
        addFileSize(8).addFileSize(2);
        variableLength.getAndIncrement();
        return this;
    }


    public ObjectSerializer writeFloat(float data) {
        writers.add(new VariableWriter(WriteType.FLOAT, data,
                WriteType.FLOAT.getCalculator().calculateBytes(0)));
        addFileSize(4).addFileSize(2);
        return this;
    }

    public ObjectSerializer writeByte(byte data) {
        writers.add(new VariableWriter(WriteType.BYTE, data,
                WriteType.BYTE.getCalculator().calculateBytes(0)));
        addFileSize(1).addFileSize(2);
        variableLength.getAndIncrement();
        return this;
    }

    public ObjectSerializer writeObject(Object obj){
        ClassConverter conv = DataChainSerializer.getConverter(obj.getClass());
        if(conv == null)
            throw new ConverterNotDefinedException("Cannot serialize class " + obj.getClass() + " - ClassConverter not exist");
        VariableWriter vw = new VariableWriter(WriteType.CUSTOM_OBJECT,obj,0);
        writers.add(vw);
        addFileSize(vw.getSize());
        variableLength.getAndIncrement();
        return this;
    }

    public ObjectSerializer addFileSize(int size) {
        this.size.addAndGet(size);
        return this;
    }

    public ObjectSerializer subtractSize(int size) {
        this.size.set(this.size.get() - size);
        return this;
    }


    public void writeSimple(ObjectOutputStream oos) throws Exception{
        writeSerializer(oos);
    }
}
