package skywolf46.DataChainSerializer.Data;

import skywolf46.DataChainSerializer.DataChainSerializer;
import skywolf46.DataChainSerializer.Enums.WriteType;
import skywolf46.DataChainSerializer.Serializers.ObjectSerializer;
import skywolf46.DataChainSerializer.Util.StreamStringUtil;

import java.io.ObjectOutputStream;

public class VariableWriter {
    private Object toWrite;
    private WriteType write;
    private int size;
    private ObjectSerializer sub;

    public VariableWriter(WriteType write, Object toWrite, int size) {
        this.write = write;
        this.toWrite = toWrite;
        this.size = size;
        if (write == WriteType.CUSTOM_OBJECT) {
            Class convClass = DataChainSerializer.getConvertable(toWrite.getClass());
            String ws = convClass.getName();
            this.size = 2 + 2 + ws.length() * 2;
            sub = new ObjectSerializer();
            DataChainSerializer.getConverter(toWrite.getClass()).writeData(toWrite, sub);
        }
    }

    public void write(ObjectOutputStream stream) throws Exception {
        stream.writeShort(write.getNumbering());
        switch (write) {
            case STRING: {
                String n = (String) toWrite;
                stream.writeInt(n.length());
                for (char c : n.toCharArray())
                    stream.writeChar(c);
            }
            break;
            case FLOAT:
                stream.writeFloat((Float) toWrite);
                break;
            case INTEGER:
                stream.writeInt((Integer) toWrite);
                break;
            case DOUBLE:
                stream.writeDouble((Double) toWrite);
                break;
            case BYTE:
                stream.writeByte((Integer) toWrite);
                break;
            case SHORT:
                stream.writeShort((Integer) toWrite);
                break;
            case CHAR:
                stream.writeChar((Integer) toWrite);
                break;
            case CHARS: {
                String n = (String) toWrite;
                stream.writeInt(n.length());
                for (char c : n.toCharArray())
                    stream.writeChar(c);
            }
            break;
            case BOOLEAN:
                stream.writeBoolean((Boolean) toWrite);
                break;
            case BYTES: {
                byte[] n = (byte[]) toWrite;
                stream.writeInt(n.length);
                for (byte b : n) stream.writeByte(b);
            }
            break;
            case LONG: {
                stream.writeLong((long) toWrite);
            }
            break;
            case CUSTOM_OBJECT: {
                Class convClass = DataChainSerializer.getConvertable(toWrite.getClass());
                String ws = convClass.getName();
                StreamStringUtil.writeString(stream, ws);
                // String separator(short,2 bytes) + String length * char size
                sub.writeSimple(stream);
            }
            break;
        }
    }

    public int getSize() {
        return write == WriteType.CUSTOM_OBJECT ? size + sub.getSize() : size;
    }
}
