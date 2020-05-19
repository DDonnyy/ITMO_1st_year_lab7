package Common.Commands;

import Common.*;
import Utility.DBworking;
import Utility.ServerReceiver;
import Utility.ServerSender;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

public class ReplaceIfGreater implements Command {
    public ReplaceIfGreater(){
        Invoker.regist("replace_if_greater",this);
    }
    @Override
    public void execute(String par1, Socket clientSocket,String user) throws IOException, SQLException {
        DBworking dBworking = new DBworking();
        dBworking.ConnectionToDB();
        ServerSender serverSender = new ServerSender();
        ServerReceiver serverReceiver = new ServerReceiver();
        dBworking.loadAllTickets();
        TicketCollection.getLock().writeLock().lock();
        try{
        if (par1 == null && ExecuteScript.inExecution) {
            serverSender.send(clientSocket,"Параметр не был указан,выполнение команды \"replace_if_greater\" невозможно.", 2);
        } else if (par1 == null) {
            String key;
            serverSender.send(clientSocket,"Введите ключ", 1);
            key = (String) serverReceiver.receive(clientSocket);
            if (key.equals("") || key == null) {
                serverSender.send(clientSocket,"Ключ не может быть null", 2);
                this.execute(par1,clientSocket,user);
            } else this.execute(key,clientSocket,user);
        } else {
            String price;
            serverSender.send(clientSocket,"Введите цену для замены.", 1);
            price = (String)serverReceiver.receive(clientSocket);
            if (price.equals("") || price == null || (Float.parseFloat(price)) <= 0) {
                serverSender.send(clientSocket,"Ключ не может быть нулевым или меньше нуля.", 2);
                this.execute(par1,clientSocket,user);
            } else {
                dBworking.loadAllTickets();
                TicketCollection ticketCollection = new TicketCollection();
                if (ticketCollection.getSize() == 0) {
                   if (ExecuteScript.inExecution) serverSender.send(clientSocket,"Коллекция как бы пустая.", 2);
                   else serverSender.send(clientSocket,"Коллекция как бы пустая.", 0);
                } else {
                    long givenkey = Long.parseLong(par1);
                    boolean keyExist = false;
                    String ticketUser = "";
                    TreeMap<Long, Ticket> tickets = ticketCollection.getTickets();
                    for (Map.Entry<Long, Ticket> entry : tickets.entrySet()) {
                        if (entry.getKey() == givenkey) {
                            keyExist = true;
                            ticketUser = entry.getValue().getUser();
                        }
                    }
                    if (keyExist&&user.equals(ticketUser)) {
                        Ticket ticket = ticketCollection.getTicket(givenkey);
                        float oldprice = ticket.getPrice();
                        if (oldprice < Float.parseFloat(price)) {
                            ticket.setPrice(Float.parseFloat(price));
                            serverSender.send(clientSocket,"Цена билета №" + givenkey + " изменена с " + oldprice + " на " + ticket.getPrice() + ".", 0);
                            dBworking.uploadAllTickets();
                        } else serverSender.send(clientSocket,"Указанная цена меньше,чем у данного билета,ничего не изменено.", 0);
                    } else serverSender.send(clientSocket,"Билет с указанным номером отсутвует в коллекции или не принадлежит вам.", 0);
                }
            }
        }
    } catch (NumberFormatException e){
            serverSender.send(clientSocket,"Неверно указано одно из значений.\nОбратите внимание,что ключ должен быть целым числом и цена числом больше 0.",0);
        }
        finally {
            TicketCollection.getLock().writeLock().unlock();
        }
    }

    @Override
    public String getInfo() {
        return "-----> Заменить значение по ключу,если новое значение больше старого.";
    }
}
