package Common.Commands;

import Common.Command;
import Common.Invoker;
import Common.Ticket;
import Common.TicketCollection;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;

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
    public void execute(String par1, Socket clientSocket,String user) throws IOException {
//        DBworking dBworking = new DBworking();
//        dBworking.ConnectionToDB();
//        dBworking.loadAllTickets();
//        ServerSender serverSender = new ServerSender();
//        TreeMap <Long,Ticket> tickets = ticketCollection.getTickets();
//        if(tickets.size()>0){
//            tickets.entrySet().stream().forEach((ticket)->serverSender.send(clientSocket,ticket.getValue().getTicket()+"\n",2));
//            if (ExecuteScript.inExecution)  serverSender.send(clientSocket,"------------------------",2);
//            else serverSender.send(clientSocket,"------------------------",0);
//        }   else  if (ExecuteScript.inExecution) serverSender.send(clientSocket,"Коллекция пуста.",2);
//        else serverSender.send(clientSocket,"Коллекция пуста.",0);

    }

    @Override
    public String getInfo() {
        return "-----------------> Вывести все элементы коллекции и их информацию.";
    }

}
