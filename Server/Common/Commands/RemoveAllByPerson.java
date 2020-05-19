package Common.Commands;
import Common.Command;
import Common.Invoker;
import Common.Ticket;
import Common.TicketCollection;
import Utility.DBworking;
import Utility.ServerReceiver;
import Utility.ServerSender;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RemoveAllByPerson implements Command {
     public RemoveAllByPerson(){
            Invoker.regist("remove_all_by_person",this);
         }
    @Override
    public void execute(String par1, Socket clientSocket,String user) throws IOException, SQLException {
        DBworking dBworking = new DBworking();
        dBworking.ConnectionToDB();
        ServerSender serverSender = new ServerSender();
        ServerReceiver serverReceiver = new ServerReceiver();
        dBworking.loadAllTickets();
        TicketCollection.getLock().writeLock().lock();
        TicketCollection ticketCollection = new TicketCollection();
        int collectionSize = ticketCollection.getSize();
        if (collectionSize == 0) {
            if (ExecuteScript.inExecution) serverSender.send(clientSocket, "Коллекция пуста,сравнивать не с чем.", 2);
            else serverSender.send(clientSocket, "Коллекция пуста,сравнивать не с чем.", 0);
        } else {
            serverSender.send(clientSocket, "", 4);
            Ticket t = (Ticket) serverReceiver.receive(clientSocket);
            List<Long> keysToDelete = new ArrayList<>();
            ticketCollection
                    .getTickets()
                    .entrySet()
                    .stream()
                    .filter(s -> s.getValue().getUser().equals(user))
                    .filter((s) -> (s.getValue().getPerson().compareTo(t.getPerson())) != 0)
                    .forEach((s) -> keysToDelete.add(s.getKey()));
            if (keysToDelete.size() == 0) {
                if (ExecuteScript.inExecution)
                    serverSender.send(clientSocket, "Билетов c указанным человеком не было найдено.", 2);
                else serverSender.send(clientSocket, "Билетов c указанным человеком не было найдено.", 0);
            } else {
                keysToDelete.forEach(ticketCollection::removeTicket);
                dBworking.uploadAllTickets();
                if (ExecuteScript.inExecution)
                    serverSender.send(clientSocket, "Все возможные обьекты с указанным человеком были удалены.", 2);
                else serverSender.send(clientSocket, "Все возможные обьекты с указанным человеком были удалены.", 0);
            }
        }
        TicketCollection.getLock().writeLock().unlock();
    }

    @Override
    public String getInfo() {
        return "------>Удалить из коллекции все элементы,значение поля person которого совпадает с введённым.";
    }
}
