package Utility;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.Map;

public class ClientSender {
  public static Boolean serverisconnected = false;
        public static void send(Object o) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(ClientReceiver.sock.getOutputStream());
            objectOutputStream.writeObject(o);

    } catch (IOException e) {
            System.out.println("Произошла некоторая ошибка,необходимо переподключение.");
            ClientSender.serverisconnected = false;
        }
    }
    public static void tryToConnect() throws InterruptedException {
        while(!serverisconnected)
            try {
                Socket socket = new Socket("localhost", 3016);
                serverisconnected = true;
                System.out.println("Успешно подключено к серверу.");
                ClientReceiver.sock = socket ;

            }
            catch (ConnectException e){
                System.out.println("Похоже,что сервер отключен или недоступен,попробуйте позже.");
                Thread.sleep(2000);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}

