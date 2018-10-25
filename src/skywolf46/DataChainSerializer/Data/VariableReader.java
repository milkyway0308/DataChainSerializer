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

    private int bytes = 0;

    public VariableReader(ObjectInputStream stream, AtomicInteger leftByte) throws Exception {
        bytes += 2;
        int a = stream.readShort();
        type = WriteType.get(a);
        switch (type) {
            case STRING: {
                leftByte.set(leftByte.get()-4);
                char[] b = new char[stream.readInt()];
                for (int i = 0; i < b.length; i++){
                    leftByte.set(leftByte.get()-2);
                    b[i] = stream.readChar();
                }
                obj = new String(b);
            }
            break;
            case FLOAT:
                leftByte.set(leftByte.get()-4);
                obj = stream.readFloat();
                break;
            case INTEGER:
                leftByte.set(leftByte.get()-4);
                obj = stream.readInt();
                break;
            case DOUBLE:
                leftByte.set(leftByte.get()-8);
                obj = stream.readDouble();
                break;
            case BYTE:
                leftByte.set(leftByte.get()-1);
                obj = stream.readByte();
                break;
            case SHORT:
                leftByte.set(leftByte.get()-2);
                obj = stream.readShort();
                break;
            case CHAR:
                leftByte.set(leftByte.get()-2);;
                obj = stream.readChar();
                break;
            case BOOLEAN:
                leftByte.set(leftByte.get()-1);
                obj = stream.readBoolean();
                break;
            case CHARS: {
                leftByte.set(leftByte.get()-4);
                char[] b = new char[stream.readInt()];
                for (int i = 0; i < b.length; i++){
                    leftByte.set(leftByte.get()-2);
                    b[i] = stream.readChar();
                }
                obj = b;
            }
            break;
            case BYTES: {
                leftByte.set(leftByte.get()-4);
                byte[] b = new byte[stream.readInt()];
                for (int i = 0; i < b.length; i++) {
                    leftByte.set(leftByte.get()-1);
                    b[i] = stream.readByte();
                }
                obj = b;
            }
            break;
            case CUSTOM_OBJECT: {
                leftByte.set(leftByte.get()-2);
                String rs = StreamStringUtil.readString(stream);
                leftByte.set(leftByte.get() - rs.length() * 2);
                ClassConverter conv = DataChainSerializer.getConverter(rs);
                if (conv == null)
                    throw new Exception("Class " + rs + " cannot deserialize in this chain deserializer");
                ObjectDeserializer des = ObjectDeserializer.getDeserializer(stream.readInt());
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
