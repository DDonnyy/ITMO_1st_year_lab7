package Common.Commands;

import Common.Command;
import Common.Ticket;
import Common.TicketCollection;
import Common.Invoker;

import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

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
    public void execute(String par1, Socket clientSocket,String user) throws IOException {
        try { if(par1==null&& ExecuteScript.inExecution){
            //ServerSender.send("Выполнение скрипта: Цена для усреднения не была указана,выполнение команды невозможно.",1);
        }
        else  if (par1 == null){
            String key;
          // ServerSender.send("Введите цену для фильтрации.",1);
          //  key = (String) ByteToObject.Cast(ServerReceiver.receive());
          //  if (key.equals("") || key == null || key.equals("\n")) { ServerSender.send("Цена не может быть null",2);this.execute(par1);}
           // else this.execute(key);
        }
        else {
          //  TicketCollection ticketCollection = new TicketCollection();
         //   Double key = Double.parseDouble(par1);
          //  long count=0;
          //  count= ticketCollection.getTickets().entrySet().stream().filter((s)->(double)s.getValue().getPrice() == key).count();
         //   ticketCollection.getTickets().entrySet().stream().filter((s)->(double)s.getValue().getPrice() == key).forEach((s)->ServerSender.send(("Билет №"+(s.getValue()).getMapKey()+" иммет цену "+key),2));
          //  if (count==0) ServerSender.send(("Билетов с ценой "+key+ " нету в коллекции."),2);
           // ServerSender.send("",0);
        }
        } catch (NumberFormatException|NullPointerException e){
          //  ServerSender.send("Цена указана некорректно,попробуйте ещё раз",0);
        }
    }


    @Override
    public String getInfo() {
        return "------> Вывод билетов с заданной ценой";
    }
}
