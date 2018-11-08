package skywolf46.DataChainSerializer.Util;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class StreamStringUtil {
    public static String readString(ObjectInputStream ois) throws Exception {
        char[] c = new char[ois.readInt()];
        for (int v = 0; v < c.length; v++)
            c[v] = ois.readChar();
        return new String(c);
    }

    public static void writeString(ObjectOutputStream oos, String str) throws Exception {
        oos.writeInt(str.length());
        for (int i = 0; i < str.length(); i++)
            oos.writeChar(str.charAt(i));
    }
}
