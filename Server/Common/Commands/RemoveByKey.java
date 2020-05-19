package Common.Commands;

import Common.Command;
import Common.Invoker;
import Common.Ticket;
import Common.TicketCollection;
import Utility.DBworking;
import Utility.ServerReceiver;
import Utility.ServerSender;
import sun.swing.plaf.GTKKeybindings;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
    public void execute(String par1, Socket clientSocket,String user) throws IOException, SQLException {
        DBworking dBworking = new DBworking();
        dBworking.ConnectionToDB();
        ServerSender serverSender = new ServerSender();
        ServerReceiver serverReceiver = new ServerReceiver();
        dBworking.loadAllTickets();
        TicketCollection.getLock().writeLock().lock();
        if(par1==null&& ExecuteScript.inExecution){
            serverSender.send(clientSocket,"Параметр не был указан,выполнение команды \"remove_key\" невозможно.",2);
        } else
        if (par1 == null) {
            String key;
            serverSender.send(clientSocket,"Введите ключ",1);
            key = (String) serverReceiver.receive(clientSocket);
            if (key.equals("") || key == null) {
                serverSender.send(clientSocket,"Ключ не может быть null",2);
                this.execute(par1,clientSocket,user);
            } else this.execute(key,clientSocket,user);
        } else {
            try {
                TicketCollection ticketCollection = new TicketCollection();
                if (ticketCollection.getSize() == 0) {
                    if (ExecuteScript.inExecution) serverSender.send(clientSocket,"Коллекция как бы пустая.",2);
                    else serverSender.send(clientSocket,"Коллекция как бы пустая.",0);
                } else {
                    long givenId = Long.parseLong(par1);
                    List<Long> keysToDelete = new ArrayList<>();
                    int oldSize = ticketCollection.getSize();
                    ticketCollection
                            .getTickets()
                            .entrySet()
                            .stream()
                            .filter(s -> s.getValue().getUser().equals(user))
                            .filter((s) -> s.getValue().getMapKey() == givenId)
                            .forEach((s) -> keysToDelete.add(s.getKey()));

                    if (keysToDelete.size()>0) {
                        keysToDelete.forEach(ticketCollection::removeTicket);
                        dBworking.uploadAllTickets();
                    }
                    int newSize = ticketCollection.getSize();
                    if (newSize < oldSize) {
                        if (ExecuteScript.inExecution)
                            serverSender.send(clientSocket,"Элемент с ключом " + givenId + " удалён из коллекции.", 2);
                        else serverSender.send(clientSocket,"Элемент с ключом " + givenId + " удалён из коллекции.", 0);
                    } else if (ExecuteScript.inExecution)
                        serverSender.send(clientSocket,"В коллекции нет элемента с заданным ключом или этот элемент вам не принадлежит.", 2);
                    else serverSender.send(clientSocket,"В коллекции нет элемента с заданным ключом или этот элемент вам не принадлежит.", 0);
                }

            } catch (NumberFormatException | NullPointerException e) {
                if (ExecuteScript.inExecution)
                    serverSender.send(clientSocket,"Ключ указан некорректно,попробуйте ещё раз.", 2);
                else serverSender.send(clientSocket,"Ключ указан некорректно,попробуйте ещё раз.", 0);
            }
        }
        TicketCollection.getLock().writeLock().unlock();
    }
    @Override
    public String getInfo() {
        return "---> Удалить элемент из коллекции по ключу.";
    }
}
