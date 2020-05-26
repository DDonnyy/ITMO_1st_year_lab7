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
 * The type Filter by price.
 */
public class FilterByPrice implements Command {
    /**
     * Instantiates a new Filter by price.
     */
    public FilterByPrice(){
        Invoker.regist("filter_by_price",this);
    }

    @Override
    public void execute(String par1, Socket clientSocket,String user) throws IOException, SQLException {
        DBworking dBworking = new DBworking();
        dBworking.ConnectionToDB();
        ServerSender serverSender = new ServerSender();
        ServerReceiver serverReceiver = new ServerReceiver();
        try { if(par1==null&& ExecuteScript.inExecution){
            serverSender.send(clientSocket,"Выполнение скрипта: Цена для усреднения не была указана,выполнение команды невозможно.",2);
        }
          else  if (par1 == null){
                String key;
            serverSender.send(clientSocket,"Введите цену для фильтрации.",1);
                key = (String) serverReceiver.receive(clientSocket);
            if (key.equals("") || key == null || key.equals("\n")) { serverSender.send(clientSocket,"Цена не может быть null",2);this.execute(par1,clientSocket,user);}
                else this.execute(key,clientSocket,user);
            }
            else {
                dBworking.loadAllTickets();
                TicketCollection.getLock().writeLock().lock();
                TicketCollection ticketCollection = new TicketCollection();
                Double key = Double.parseDouble(par1);
                long count=0;
                count= ticketCollection.getTickets().entrySet().stream().filter((s)->(double)s.getValue().getPrice() == key).count();
                ticketCollection.getTickets().entrySet().stream().filter((s)->(double)s.getValue().getPrice() == key).forEach((s)->serverSender.send(clientSocket,("Билет №"+(s.getValue()).getMapKey()+" иммет цену "+key),2));
               if (count==0) serverSender.send(clientSocket,"Билетов с ценой "+key+ " нету в коллекции.",2);
               if (!ExecuteScript.inExecution) serverSender.send(clientSocket,"--------------------",0);
            TicketCollection.getLock().writeLock().unlock();
            }
        } catch (NumberFormatException|NullPointerException e){
            if (ExecuteScript.inExecution) serverSender.send(clientSocket,"Цена указана некорректно,попробуйте ещё раз",2);
                else serverSender.send(clientSocket,"Цена указана некорректно,попробуйте ещё раз",0);

        }

    }

    @Override
    public String getInfo() {
        return "---> Вывод билетов с заданной ценой";
    }
}
