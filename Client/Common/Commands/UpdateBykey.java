package Common.Commands;

import Common.Command;
import Common.Ticket;
import Common.TicketCollection;
import Common.Invoker;
import Common.CreateTicket;
import Common.Decoder;

import java.net.Socket;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * The type Update bykey.
 */
public class UpdateBykey implements Command {
    /**
     * Instantiates a new Update bykey.
     */
    public UpdateBykey(){
        Invoker.regist("update_key",this);
    }
    @Override
    public void execute(String par1, Socket clientSocket,String user) {
//        if(ExecuteScript.inExecution&&(par1==null||par1.equals(""))){
//            System.out.println("Параметр не был указан,выполнение команды \"update_key\" невозможно.");
//            System.out.print("$ ");
//        } else if(ExecuteScript.inExecution){
//            Long key = Long.parseLong(par1);
//            TicketCollection ticketCollection = new TicketCollection();
//            TreeMap<Long, Ticket> tickets = ticketCollection.getTickets();
//            boolean keyExist = false;
//            if(tickets.size()>0) {
//                for (Map.Entry<Long, Ticket> entry : tickets.entrySet()) {
//                    if (entry.getKey() == key) {
//                        keyExist = true;
//                        break;
//                    }
//                }
//            } else System.out.println("Коллекция пуста");
//            if (keyExist) {
//                if (!(par1.equals(""))) { Decoder.setKeyForInsertAndUpdate(Long.parseLong(par1));}
//                Map.Entry entry = (Decoder.decodeIntoCollection(ExecuteScript.getExecuteData()).firstEntry());
//                Ticket ticket = (Ticket)entry.getValue();
//                key = (long)entry.getKey();
//                ticketCollection.replaceMovie(key,ticket);
//                System.out.println("Элемент с ключом "+key+" обновлён.");
//            } else {
//                System.out.println("Элемент с заданным ключом не существует,попробуйте ввести команду снова.");
//            }
//            System.out.print("$ ");
//        } else
//        if (par1 == null){
//            Scanner scanner = new Scanner(System.in);
//            String key;
//            System.out.println("Введите ключ");
//            System.out.print("$ ");
//            key = scanner.nextLine();
//            if (key.equals("") || key == null) {System.out.println("Ключ не может быть null");this.execute(par1);}
//            else this.execute(key);
//        } else {
//            Long key = Long.parseLong(par1);
//            TicketCollection ticketCollection = new TicketCollection();
//            TreeMap<Long, Ticket> tickets = ticketCollection.getTickets();
//            boolean keyExist = false;
//            if(tickets.size()>0) {
//                for (Map.Entry<Long, Ticket> entry : tickets.entrySet()) {
//                    if (entry.getKey() == key) {
//                        keyExist = true;
//                        break;
//                    }
//                }
//            } else System.out.println("Коллекция пуста");
//            CreateTicket createTicket = new CreateTicket();
//            if (keyExist) {
//                ticketCollection.replaceMovie(key, createTicket.create(key));
//                System.out.println("Элемент с ключом "+key+" обновлён.");
//            } else {
//                System.out.println("Элемент с заданным ключом не существует,попробуйте ввести команду снова.");
//            }
//            System.out.print("$ ");
//        }
    }

    @Override
    public String getInfo() {
        return "-----------> Обновить значения элемента по его ключу.";
    }
}
