package skywolf46.DataChainSerializer.Deserializers;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import skywolf46.DataChainSerializer.Data.VariableReader;
import skywolf46.DataChainSerializer.Iterate.DefaultVariableReaderIterator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ObjectDeserializer {
    private List<ObjectDeserializer> subDeserializers = new ArrayList<>();
    private List<VariableReader> variables = new ArrayList<>();
    private int byteSize = 0;
    private int variableLength = 0;
    private int serializerLength = 0;

    private static HashMap<Integer, ObjectDeserializer> deserializerHashMap = new HashMap<>();

    public ObjectDeserializer() {

    }

    public static void setUp() {
        new ObjectDeserializer().init();
    }

    public void init() {
        if (!deserializerHashMap.containsKey(getDeserializeID())) {
            deserializerHashMap.put(getDeserializeID(), this);
        }
    }


    public static ObjectDeserializer getDeserializer(int id) {
        return deserializerHashMap.get(id);
    }

    private int getCurrentByte() {
        return byteSize;
    }

    public int getByte() {
        int size = getCurrentByte();
        for (ObjectDeserializer obj : subDeserializers)
            size += obj.getByte();
        return size;
    }

    public String getDeserializeName() {
        return "DefaultDeserializer";
    }

    public ObjectDeserializer getNewDeserializer() {
        return new ObjectDeserializer();
    }

    public final void deserialize(ObjectInputStream ois) throws Exception {
        clear();
        try {
            readCoreData(ois.readInt(), ois.readInt(), ois.readInt(),0);
            // Deserialize level 0
            deserialize_finally(ois, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public final void readCoreData(int size, int varlength, int serlength,int depth) throws Exception {
        byteSize = size;
        variableLength = varlength;
        serializerLength = serlength;
    }

    protected void readDeserializeData(ObjectInputStream ois) throws Exception {

    }


    // On first read, pre-read core data at before method/
    protected void deserialize_finally(ObjectInputStream ois, int depthLevel) throws Exception {
        AtomicInteger leftByte = new AtomicInteger(byteSize);
        try {
            // Read deserialize sub data
            readDeserializeData(ois);
            // read variable.
            for (int i = 0; i < variableLength; i++) {
                VariableReader vr = new VariableReader(ois);
                this.variables.add(vr);
            }

            int byteLength = 0;
            // try to read serializer
            for (int i = 0; i < serializerLength; i++) {
                try {
                    int id = ois.readInt();
                    byteLength = ois.readInt();
                    int varLength = ois.readInt();
                    int serLength = ois.readInt();
                    leftByte = new AtomicInteger(byteLength);
                    ObjectDeserializer subdes = getDeserializer(id);
                    if (subdes == null) {
                        ois.skipBytes(byteLength);
                        throw new Exception();
                    }
                    subdes = subdes.getNewDeserializer();
                    subdes.readCoreData(byteLength, varLength, serLength,depthLevel+1);
                    subdes.deserialize_finally(ois, depthLevel + 1);
                    subDeserializers.add(subdes);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    AnsiConsole.out().println(Ansi.ansi().a("[DataChainSerializer] Deserialize fail on " + getDeserializeName() + " / Deserialize depth level " + depthLevel + ",skipping left bytes (" + leftByte.get() + "byte)"));
                    ois.skipBytes(leftByte.get());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            AnsiConsole.out().println(Ansi.ansi().a("[DataChainSerializer] Deserialize fail on " + getDeserializeName() + ",skipping left bytes (" + leftByte.get() + "byte)"));
        }
    }

    public void clear() {
        variables.clear();
        subDeserializers.clear();
    }

    public int getDeserializeID() {
        return "Deserializer=DefaultDeserializer".hashCode();
    }

    public Iterator<VariableReader> iterator() {
        return new DefaultVariableReaderIterator(variables);
    }

    public List<VariableReader> getVariables() {
        return new ArrayList<>(variables);
    }

    public List<ObjectDeserializer> getSubDeserializers() {
        return new ArrayList<>(subDeserializers);
    }


    public int test() {
        return variableLength;
    }
}
