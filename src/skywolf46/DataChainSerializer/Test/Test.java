package skywolf46.DataChainSerializer.Test;

import skywolf46.DataChainSerializer.Data.VariableReader;
import skywolf46.DataChainSerializer.DataChainSerializer;
import skywolf46.DataChainSerializer.Deserializers.ObjectFieldDeserializer;
import skywolf46.DataChainSerializer.Serializers.ObjectFieldSerializer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        DataChainSerializer.setUp();
        try {
            ServerSocket socket = new ServerSocket(2341);
            while (true) {
                Socket soc = socket.accept();
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("Socket Accepted");
                            ObjectOutputStream oos = new ObjectOutputStream(soc.getOutputStream());
                            ObjectFieldSerializer ofs = new ObjectFieldSerializer();
                            DateFormat df = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
                            while (true) {
                                ofs.writeString("TestMessage", "이 메시지는 " + df.format(new Date(System.currentTimeMillis())) + "에 보내졌습니다.");
                                int usingRam = (int) (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory());
                                ofs.writeObject("o1","사용중인 메모리: " + String.format("%.2f MB", ((double)Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024d / 1024d));
                                ofs.writeObject("o2","전체 메모리: " + String.format("%.2f MB", ((double) Runtime.getRuntime().totalMemory()) / 1024d / 1024d));
                                ofs.writeObject("o3","사용 가능한 메모리: " + String.format("%.2f MB", ((double) Runtime.getRuntime().freeMemory()) / 1024d / 1024d));
                                System.out.println("Write Object");
                                ofs.writeSerializer(oos, false, true);
                                System.out.println("Write Serializer");
                                Thread.sleep(5000);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static class Test2 {
        public static void main(String[] args) {
            DataChainSerializer.setUp();
            try {
                Socket socket = new Socket("localhost", 2341);
                ObjectInputStream stream = new ObjectInputStream(socket.getInputStream());
                DateFormat df = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
                while (true) {
                    ObjectFieldDeserializer des = (ObjectFieldDeserializer) DataChainSerializer.readNext(stream, false);
                    for (int i = 0; i < 15; i++)
                        System.out.println();
                    System.out.println(des.getReader("TestMessage").getObject().toString());
                    System.out.println("==============================");
                    System.out.println(des.getReader("o1").getObject().toString());
                    System.out.println(des.getReader("o2").getObject().toString());
                    System.out.println(des.getReader("o3").getObject().toString());

                    System.out.println("==============================");

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println("Complete");
        }
    }
}

