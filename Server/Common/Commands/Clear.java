package Common.Commands;

import Common.Command;
import Common.Invoker;
import Common.TicketCollection;
import Utility.DBworking;
import Utility.ServerSender;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Common.Commands.Clear.
 */
public class Clear implements Command {
    /**
     * Instantiates a new Common.Commands.Clear.
     */
    public Clear(){
        Invoker.regist("clear",this);
    }

    TicketCollection ticketCollection = new TicketCollection();
    @Override
    public void execute(String par1, Socket clientSocket,String user) throws IOException, SQLException {
        DBworking dBworking = new DBworking();
        dBworking.ConnectionToDB();
        ServerSender serverSender = new ServerSender();
        dBworking.loadAllTickets();
        TicketCollection.getLock().writeLock().lock();
        if (ticketCollection.getTickets().size()>0){
            ticketCollection.deleteUserTickets(user);
            dBworking.uploadAllTickets();
            if(ExecuteScript.inExecution){
                serverSender.send(clientSocket,"Коллекция очищена от ваших билетов!",2);
            } else serverSender.send(clientSocket,"Коллекция очищена от ваших билетов!",0);
        }
        else
        if(ExecuteScript.inExecution){
            serverSender.send(clientSocket,"Коллекция уже пуста.",2);
        } else serverSender.send(clientSocket,"Коллекция уже пуста.",0);
        TicketCollection.getLock().writeLock().unlock();
    }

    @Override
    public String getInfo() {
        return "---> Удаление элементов,принадлежащих вам";
    }
}
