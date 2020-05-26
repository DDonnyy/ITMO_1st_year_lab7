package Common.Commands;
import Common.Command;
import Common.Invoker;
import Common.Ticket;
import Common.TicketCollection;
//import Utility.ByteToObject;
//import Utility.ServerReceiver;
//import Utility.ServerSender;

import java.io.IOException;
import java.net.Socket;
import java.util.TreeMap;

public class RemoveAllByPerson implements Command {
   public RemoveAllByPerson(){
            Invoker.regist("remove_all_by_person",this);
    }
    @Override
    public void execute(String par1, Socket clientSocket,String user) throws IOException {
   //     TicketCollection ticketCollection = new TicketCollection();
      //  int collectionSize = ticketCollection.getSize();
       // if (collectionSize==0) ServerSender.send("Коллекция пуста,сравнивать не с чем.",0);
       // else {
          //  ServerSender.send("",4);
          //  Ticket t = (Ticket) ByteToObject.Cast(ServerReceiver.receive());
         //   TreeMap<Long, Ticket> tickets = new TreeMap<>();
         //   ticketCollection
         //           .getTickets()
          //          .entrySet()
         //           .stream()
          //          .filter((s)->s.getValue().getPerson().compareTo(t.getPerson())!=0)
          //          .forEach((s)->tickets.put(s.getKey(),s.getValue()));
         //   ticketCollection.setTickets(tickets);
         //   if (tickets.size()==0) ServerSender.send("Билетов с таким Person не было найдено.",0);
         //   else ServerSender.send("Билеты с указанным человеком были удалены.",0);
       // }
    }

    @Override
    public String getInfo() {
        return "-> Удалить из коллекции все элементы,значение поля person которого совпадает с введённым.";
    }
}
