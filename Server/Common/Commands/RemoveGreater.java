package Common.Commands;

import Common.Command;
import Common.Invoker;
import Common.TicketCollection;
import Utility.DBworking;
import Utility.ServerReceiver;
import Utility.ServerSender;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Remove greater.
 */
public class RemoveGreater implements Command {
    /**
     * Instantiates a new Remove greater.
     */
    public RemoveGreater(){
        Invoker.regist("remove_greater",this);
    }
    @Override
    public void execute(String par1, Socket clientSocket,String user) throws IOException, SQLException {
        DBworking dBworking = new DBworking();
        dBworking.ConnectionToDB();
        ServerSender serverSender = new ServerSender();
        ServerReceiver serverReceiver = new ServerReceiver();
        dBworking.loadAllTickets();
        if(par1==null&& ExecuteScript.inExecution){
            serverSender.send(clientSocket,"Параметр не был указан,выполнение команды \"remove_greater\" невозможно.",2);
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
                TicketCollection.getLock().writeLock().lock();
                TicketCollection ticketCollection = new TicketCollection();
                if (ticketCollection.getSize() == 0) {
                    if (ExecuteScript.inExecution)  serverSender.send(clientSocket,"Коллекция как бы пустая.",2);
                    else  serverSender.send(clientSocket,"Коллекция как бы пустая.",0);
                } else {
                    long givenId = Long.parseLong(par1);
                    List<Long> keysToDelete = new ArrayList<>();
                    ticketCollection
                            .getTickets()
                            .entrySet()
                            .stream()
                            .filter(s -> s.getValue().getUser().equals(user))
                            .filter((s)->s.getValue().getMapKey()>givenId)
                            .forEach((ticket)->keysToDelete.add(ticket.getKey()));
                    if (keysToDelete.size() == 0) {
                        if (ExecuteScript.inExecution)
                            serverSender.send(clientSocket, "Элементы для удаления не были найдены.", 2);
                        else serverSender.send(clientSocket, "Элементы для удаления не были найдены.", 0);
                    } else {
                        keysToDelete.forEach(ticketCollection::removeTicket);
                        dBworking.uploadAllTickets();
                        if (ExecuteScript.inExecution)
                            serverSender.send(clientSocket, "Все возможные обьекты были удалены.", 2);
                        else serverSender.send(clientSocket, "Все возможные обьекты были удалены.", 0);
                    }

                }
            } catch (NumberFormatException | NullPointerException e) {
                if (ExecuteScript.inExecution) serverSender.send(clientSocket,"Ключ указан некорректно,попробуйте ещё раз.",2);
                else serverSender.send(clientSocket,"Ключ указан некорректно,попробуйте ещё раз.",0);
            }
        }
        TicketCollection.getLock().writeLock().unlock();
    }

    @Override
    public String getInfo() {
        return "---> Удалить все элементы,чей ключ больше данного" ;
    }
}
