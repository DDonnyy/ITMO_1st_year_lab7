package Utility;

import Utility.ServerReceiver;
import com.sun.corba.se.impl.activation.ServerMain;

import javax.annotation.processing.SupportedAnnotationTypes;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

//Параметр needAnswer имеет 3 значения
//(0 - ответ от клиента не ожидается,Готов делать след команду.)
//1 - Необходим ответ от клиента,делать след команду не готов
//2 - Просто сообщение,но делать команду не готов(особенность)))))))))))))
//5 - критическая ошибка,принудительное отключение клиента.
public class ServerSender {
    ForkJoinPool forkJoinPool = new ForkJoinPool();
    public  void send(Socket client, String message, Integer needAnswer) {
        Sender sender = new Sender(client, message, needAnswer);
        forkJoinPool.execute(sender);

        System.out.println("Отправляю данные клиенту c адресом: "+client.getLocalAddress()+client.getPort());
    }
    public class Sender implements Runnable{
        private Socket client;
        private String message;
        private Integer needAnswer;

        public Sender(Socket client, String message, Integer needAnswer){
            this.client = client;
            this.message =message;
            this.needAnswer = needAnswer;

        }
        @Override
        public void run() {

            Map<String, Integer> answer = new HashMap<>();
            answer.put(message, needAnswer);
            Object o = answer;
            try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                objectOutputStream.writeObject(o);
                answer.clear();
                objectOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}