package Common.Commands;

import Common.Command;
import Common.Ticket;
import Common.TicketCollection;
import Common.Invoker;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * The type Remove by key.
 */
public class RemoveByKey implements Command {
    /**
     * Instantiates a new Remove by key.
     */
    public RemoveByKey(){
        Invoker.regist("remove_key",this);
    }
    @Override
    public void execute(String par1, Socket clientSocket,String user) throws IOException {
//        if(par1==null&&ExecuteScript.inExecution){
//            System.out.println("Параметр не был указан,выполнение команды \"remove_key\" невозможно.");
//        } else
//        if (par1 == null) {
//            Scanner scanner = new Scanner(System.in);
//            String key;
//            System.out.println("Введите ключ");
//            System.out.print("$ ");
//            key = scanner.nextLine();
//            if (key.equals("") || key == null) {
//                System.out.println("Ключ не может быть null");
//                this.execute(par1);
//            } else this.execute(key);
//        } else {
//            try {
//                TicketCollection ticketCollection = new TicketCollection();
//                TreeMap<Long, Ticket> tickets = ticketCollection.getTickets();
//                Long key = Long.parseLong(par1);
//                boolean keyExist = false;
//                if (tickets.size() > 0) {
//                    for (Map.Entry<Long, Ticket> entry : tickets.entrySet()) {
//                        if (entry.getKey() == key) {
//                            keyExist = true;
//                        }
//                    }
//                } else System.out.println("Коллекция пуста");
//                if (keyExist) {
//                    ticketCollection.removeTicket(key);
//                    System.out.println("Элемент с ключом " + key + " удалён из коллекции.");
//                } else {
//                    System.out.println("Элемент с заданным ключом не существует,попробуйте ещё раз ввести команду.");
//                }
//                System.out.print("$ ");
//
//            } catch (NumberFormatException | NullPointerException e) {
//                System.out.println("Ключ указан некорректно,попробуйте ещё раз.");
//            }
//        }
    }
    @Override
    public String getInfo() {
        return "-----------> Удалить элемент из коллекции по ключу.";
    }
}
