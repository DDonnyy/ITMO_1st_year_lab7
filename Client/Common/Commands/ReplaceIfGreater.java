package Common.Commands;

import Common.Command;
import Common.Invoker;
import Common.Ticket;
import Common.TicketCollection;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;

public class ReplaceIfGreater implements Command {
    public ReplaceIfGreater(){
        Invoker.regist("replace_if_greater",this);
    }
    @Override
    public void execute(String par1, Socket clientSocket,String user) throws IOException {
//        if(par1==null&& ExecuteScript.inExecution){
//           // ServerSender.send("Параметр не был указан,выполнение команды \"replace_if_greater\" невозможно.",2);
//        } else
//        if (par1 == null) {
//            String key = null;
//          //  ServerSender.send("Введите ключ",1);
//        //    key = (String) ByteToObject.Cast(ServerReceiver.receive());
//            if (key.equals("") || key == null) {
//              //  ServerSender.send("Ключ не может быть null",2);
//                this.execute(par1);
//            } else this.execute(key);
//        } else {
//            String price = null;
//           // ServerSender.send("Введите цену для замены.", 1);
//           // price = (String) ByteToObject.Cast(ServerReceiver.receive());
//            if (price.equals("") || price == null || (Float.parseFloat(price)) <= 0) {
//             ///   ServerSender.send("Ключ не может быть нулевым или меньше нуля.", 2);
//                this.execute(par1);
//            } else {
//                    TicketCollection ticketCollection = new TicketCollection();
//                    if (ticketCollection.getSize() == 0) {
//                   //     ServerSender.send("Коллекция как бы пустая.", 0);
//                    } else {
//                        long givenkey = Long.parseLong(par1);
//                        boolean keyExist = false;
//                        TreeMap<Long, Ticket> tickets = ticketCollection.getTickets();
//                            for (Map.Entry<Long, Ticket> entry : tickets.entrySet()) {
//                                if (entry.getKey() == givenkey) {
//                                    keyExist = true;
//                            }
//                        }
//                            if (keyExist) {
//                                Ticket ticket =ticketCollection.getTicket(givenkey);
//                                if (ticket.getPrice()<Float.parseFloat(price)) ticket.setPrice(Float.parseFloat(price));
//                         //       else ServerSender.send("Указанная цена меньше,чем у данного билета,ничего не изменено.",0);
//                            }  //ServerSender.send("Билет с указанным номером отсутвует в коллекции.",0);
//                    }
//            }
//        }
    }

    @Override
    public String getInfo() {
        return "---> Заменить значение по ключу,если новое значение больше старого.";
    }
}
