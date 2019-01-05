package skywolf46.DataChainSerializer.Data;


import skywolf46.DataChainSerializer.API.ClassConverter;
import skywolf46.DataChainSerializer.DataChainSerializer;
import skywolf46.DataChainSerializer.Deserializers.ObjectDeserializer;
import skywolf46.DataChainSerializer.Enums.WriteType;
import skywolf46.DataChainSerializer.Util.StreamStringUtil;

import java.io.ObjectInputStream;
import java.util.concurrent.atomic.AtomicInteger;

public class VariableReader<K> {
    private WriteType type;
    private Object obj;

    private static int acceptSize = 1024000;
    private int bytes = 0;
    public VariableReader(ObjectInputStream stream) throws Exception {
        int a = stream.readShort();
        type = WriteType.get(a);
        switch (type) {
            case STRING: {
                obj = StreamStringUtil.readString(stream);
            }
            break;
            case FLOAT:
                obj = stream.readFloat();
                break;
            case INTEGER:
                obj = stream.readInt();
                break;
            case DOUBLE:
                obj = stream.readDouble();
                break;
            case BYTE:
                obj = stream.readByte();
                break;
            case SHORT:
                obj = stream.readShort();
                break;
            case CHAR:
                obj = stream.readChar();
                break;
            case BOOLEAN:
                obj = stream.readBoolean();
                break;
            case CHARS: {
                int size = stream.readInt();
                if(size * 2 > ObjectDeserializer.getLimitation()){
                    stream.close();
                    throw new Exception("Acceptable size is " + ObjectDeserializer.getLimitation() + " byte, but " + size * 2 + "byte requested");
                }
                char[] b = new char[size];
                for (int i = 0; i < b.length; i++){
                    b[i] = stream.readChar();
                }
                obj = b;
            }
            break;
            case BYTES: {
//                if(size * 2 > ObjectDeserializer.getLimitation()){
//                    stream.close();
//                    throw new Exception("Acceptable size is " + ObjectDeserializer.getLimitation() + " byte, but " + size * 2 + "byte requested");
//                }
                byte[] b = new byte[stream.readInt()];
                for (int i = 0; i < b.length; i++) {
                    b[i] = stream.readByte();
                }
                obj = b;
            }
            break;
            case CUSTOM_OBJECT: {
                String rs = StreamStringUtil.readString(stream);
                ClassConverter conv = DataChainSerializer.getConverter(rs);
                if (conv == null){
                    throw new Exception("Class " + rs + " cannot deserialize in this chain deserializer");
                }
                ObjectDeserializer des = ObjectDeserializer.getDeserializer(stream.readInt());
                des = des.getNewDeserializer();
                des.deserialize(stream);
                obj = conv.readData(des);
            }
            break;
        }
    }

    public WriteType getType() {
        return type;
    }

    public Object getObject() {
        return obj;
    }

    public String asString() {
        return (String) obj;
    }

    public float asFloat() {
        return (float) obj;
    }

    public double asDouble() {
        return (double) obj;
    }

    public byte asByte() {
        return (byte) obj;
    }

    public int asInt() {
        return (int) obj;
    }

    public byte[] asBytes() {
        return (byte[]) obj;
    }

    public char asChar() {
        return (char) obj;
    }

    public char[] asChars() {
        return (char[]) obj;
    }

    public boolean asBoolean() {
        return (boolean) obj;
    }

    public short asShort() {
        return (short) obj;
    }


    public long asLong() {
        return (long) obj;
    }
}
