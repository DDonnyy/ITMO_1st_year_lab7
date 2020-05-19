package Common.Commands;

import Common.Command;
import Common.Invoker;
import Common.Ticket;
import Common.TicketCollection;
import Utility.DBworking;
import Utility.NewConnection;
import Utility.ServerReceiver;
import Utility.ServerSender;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.*;

/**
 * The type Common.Commands.Show.
 */
public class Show implements Command {

    /**
     * Instantiates a new Common.Commands.Show.
     */
    public Show(){
        Invoker.regist("show",this);
    }
    @Override
    public void execute(String par1, Socket clientSocket,String user) throws IOException, SQLException {
        TicketCollection ticketCollection = new TicketCollection();
        DBworking dBworking = new DBworking();
        dBworking.ConnectionToDB();
        dBworking.loadAllTickets();
        TicketCollection.getLock().readLock().lock();
        ServerSender serverSender = new ServerSender();
        TreeMap <Long,Ticket> tickets = ticketCollection.getTickets();
        if(tickets.size()>0){
            List<String> answer = new ArrayList<>() ;
            tickets.entrySet().forEach((ticket)->answer.add(ticket.getValue().getTicket()+"\n"));
            Iterator iterator = answer.iterator();
            while (iterator.hasNext()){
                serverSender.send(clientSocket, (String) iterator.next(),2);
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (ExecuteScript.inExecution)  serverSender.send(clientSocket,"------------------------",2);
            else serverSender.send(clientSocket,"------------------------",0);
        }   else  if (ExecuteScript.inExecution) serverSender.send(clientSocket,"Коллекция пуста.",2);
        else serverSender.send(clientSocket,"Коллекция пуста.",0);
        TicketCollection.getLock().readLock().unlock();
    }

    @Override
    public String getInfo() {
        return "---> Вывести все элементы коллекции и их информацию.";
    }

}
