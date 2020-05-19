package Utility;

import Common.CreatePerson;
import Common.CreateTicket;
import Common.Ticket;

import java.io.*;
import java.net.*;
import java.util.Map;

public class ClientReceiver {
   public  static Socket sock;
   private static BufferedReader in;
    public static void receive() {
        byte[] buffer = new byte[1000];
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(sock.getInputStream());
            Object obj = objectInputStream.readObject();
            Map<String,Integer> answer = (Map<String, Integer>) obj;
            if (answer.entrySet().iterator().next().getValue()==0) {
                System.out.println("Ответ с сервера: "+answer.entrySet().iterator().next().getKey());
            }
            else if (answer.entrySet().iterator().next().getValue() == 1){
                System.out.println("Ответ с сервера: "+answer.entrySet().iterator().next().getKey());
                System.out.print("$ ");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String s = reader.readLine();
                ClientSender.send(s);
                ClientReceiver.receive();
            }
            else if (answer.entrySet().iterator().next().getValue() == 2){
                System.out.println("Ответ с сервера: "+answer.entrySet().iterator().next().getKey());
                ClientReceiver.receive();
            } else if (answer.entrySet().iterator().next().getValue() == 3) {
                CreateTicket ct=new CreateTicket();
                Ticket ticket = ct.create(Long.parseLong(answer.entrySet().iterator().next().getKey()));
                ClientSender.send(ticket);
                ClientReceiver.receive();
            } else if (answer.entrySet().iterator().next().getValue() == 4){
                CreatePerson cp = new CreatePerson();
                Ticket person = cp.create();
                ClientSender.send(person);
                ClientReceiver.receive();
            }
            else if (answer.entrySet().iterator().next().getValue() == 5){
                System.out.println("На сервере нет подключения к базе данных,работа невозможна.");
                sock.close();
                System.exit(0);
            }
        } catch (SocketException e) {

            System.out.println("Возможно сервер отключился,попробуйте дождаться подключения и убедитесь в работоспособности сервера.");
            ClientSender.serverisconnected = false;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Возможно сервер отключился,попробуйте дождаться подключения и убедитесь в работоспособности сервера.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
