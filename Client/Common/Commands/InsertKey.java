package Common.Commands;

import Common.*;

import java.io.IOException;
import java.net.Socket;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * The type Insert key.
 */
public class InsertKey implements Command {
    /**
     * Instantiates a new Insert key.
     */
    public InsertKey(){
        Invoker.regist("insert_key",this);
    }
    @Override
    public void execute(String par1, Socket clientSocket,String user) throws IOException {
//        try {
//            if (ExecuteScript.inExecution){
//                if(ExecuteScript.getExecuteData().equals("")) {
//                    System.out.println("Поля для билеты не были заполнены,билет не создан.");
//                } else {
//                    if (!(par1.equals(""))) { Decoder.setKeyForInsertAndUpdate(Long.parseLong(par1));}
//                    Map.Entry entry = (Decoder.decodeIntoCollection(ExecuteScript.getExecuteData()).firstEntry());
//                    Ticket ticket = (Ticket)entry.getValue();
//                    Long key = (long)entry.getKey();
//                    ticketCollection.putTicket(key,ticket);
//                    System.out.println("В коллекцию был добавлен элемент.");
//                    Decoder.setKeyForInsertAndUpdate((long)0);
//                }
//                System.out.print("$ ");
//            }
//            else
//            if (par1 == null) {
//                Scanner scanner = new Scanner(System.in);
//                String key;
//                System.out.println("Введите ключ");
//                System.out.print("$ ");
//                key = scanner.nextLine();
//                if (key.equals("") || key == null) {
//                    System.out.println("Ключ не может быть null");
//                    this.execute(par1);
//                } else this.execute(key);
//            } else {
//                long key = Long.parseLong(par1);
//                boolean keyExist = false;
//                TreeMap<Long, Ticket> tickets = ticketCollection.getTickets();
//                if (tickets.size() > 0) {
//                    for (Map.Entry<Long, Ticket> entry : tickets.entrySet()) {
//                        if (entry.getKey() == key) {
//                            keyExist = true;
//                        }
//                    }
//                }
//                if (tickets.size() == 0 || !keyExist) {
//                    TicketCollection.setDateOFCreation(ZonedDateTime.now());
//                    ticketCollection.putTicket(key, createTicket.create(key));
//                    System.out.println("В коллекцию успешно добавлен элемент.");
//                    System.out.print("$ ");
//                }
//                if (keyExist) {
//                    System.out.println("Элемент с таким ключом уже есть в коллекции,желаете заменить его на новый?(YES/ДА)");
//                    System.out.print("$ ");
//                    Scanner scanner = new Scanner(System.in);
//                    String answer = scanner.nextLine();
//                    if (answer.toUpperCase().equals("YES") || answer.toUpperCase().equals("ДА")) {
//                        ticketCollection.putTicket(key, createTicket.create(key));
//                        System.out.println("В коллекцию успешно добавлен элемент.");
//                    } else System.out.println("Ну тогда ничего не выйдет,попробуйте что-нибудь другое получается соответственно.");
//                    System.out.print("$ ");
//                }
//            }
//        } catch (NumberFormatException e) {
//            System.out.println("Число должно быть типа Long,попробуйте ещё раз.");
//            this.execute(null);
//        }
    }

    @Override
    public String getInfo() {
        return "-----------> Добавить элемент в коллекцию.";
    }
}
