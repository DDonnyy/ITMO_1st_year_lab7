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

/**
 * The type Common.Commands.Info.
 */
public class Info implements Command {
    /**
     * Instantiates a new Common.Commands.Info.
     */
    public Info(){
        Invoker.regist("info",this);

    }

    @Override
    public void execute(String par1, Socket clientSocket,String user) throws IOException, SQLException {
        DBworking dBworking = new DBworking();
        dBworking.ConnectionToDB();
        dBworking.loadAllTickets();
        TicketCollection.getLock().readLock().lock();
        ServerSender serverSender = new ServerSender();
        TicketCollection ticketCollection = new TicketCollection();
        if (ExecuteScript.inExecution)  serverSender.send(clientSocket,ticketCollection.getInfo(),2);
        else serverSender.send(clientSocket,ticketCollection.getInfo(),0);
        TicketCollection.getLock().readLock().unlock();
    }

    @Override
    public String getInfo() {
        return "---> Вывести информацию об коллекции.";
    }
}
