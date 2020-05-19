package Common.Commands;

import Common.*;
import Utility.DBworking;
import Utility.ServerReceiver;
import Utility.ServerSender;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;
import java.util.TreeMap;

/**
 * The type Insert key.
 */
public class InsertKey implements Command {

    TicketCollection ticketCollection = new TicketCollection();
    CreateTicket createTicket = new CreateTicket();

    /**
     * Instantiates a new Insert key.
     */
    public InsertKey(){
        Invoker.regist("insert_key",this);
    }
    @Override
    public void execute(String par1, Socket clientSocket,String user) throws IOException, SQLException {
        ServerSender serverSender = new ServerSender();
        ServerReceiver serverReceiver = new ServerReceiver();
        DBworking dBworking = new DBworking();
        dBworking.ConnectionToDB();
        dBworking.loadAllTickets();
        TicketCollection.getLock().writeLock().lock();
        try {
            if (ExecuteScript.inExecution){
                if(ExecuteScript.getExecuteData().equals("")||par1.equals("")||par1==null) {
                    serverSender.send(clientSocket,"Поля для билеты не были заполнены,билет не создан.",2);
                } else {
                    Map.Entry entry = (Decoder.decodeIntoCollection(ExecuteScript.getExecuteData()).firstEntry());
                    Ticket ticket = (Ticket)entry.getValue();
                    Long key =Long.parseLong(par1);
                    ticket.setMapKey(key);
                    ticket.setUser(user);
                    ticket.setCreationDate(Timestamp.from(Instant.now()));
                        ticketCollection.putTicket(key, ticket);
                        dBworking.uploadAllTickets();
                        serverSender.send(clientSocket, "В коллекцию был добавлен элемент.", 2);

                }
            }
            else
            if (par1 == null) {
                String key;
                serverSender.send(clientSocket,"Укажите ключ для нового элемента.",1);
                key = (String)serverReceiver.receive(clientSocket);
                if (key.equals("") || key == null) {
                    serverSender.send(clientSocket,"Ключ не может быть null",2);
                    this.execute(par1,clientSocket,user);
                } else this.execute(key,clientSocket,user);
            } else {
                long key = Long.parseLong(par1);
                boolean keyExist = false;
                String objectUser = "";
                TreeMap<Long, Ticket> tickets = ticketCollection.getTickets();
                if (tickets.size() > 0) {
                    for (Map.Entry<Long, Ticket> entry : tickets.entrySet()) {
                        if (entry.getKey() == key) {
                            keyExist = true;
                            objectUser = entry.getValue().getUser();
                        }
                    }
                }
                if (!keyExist) {
                    serverSender.send(clientSocket,(String.valueOf(key)),3);
                    Ticket ticket = (Ticket)serverReceiver.receive(clientSocket);
                    ticket.setUser(user);

                    ticketCollection.putTicket(key,ticket);
                    dBworking.uploadAllTickets();
                    serverSender.send(clientSocket,"В коллекцию успешно добавлен элемент.",0);

                }
                if (keyExist) {
                    serverSender.send(clientSocket,"Элемент с таким ключом уже есть в коллекции,желаете заменить его на новый?(YES/ДА|Регистр не важен.)",1);
                    String answer = (String) serverReceiver.receive(clientSocket);
                    if (answer.toUpperCase().equals("YES") || answer.toUpperCase().equals("ДА")) {
                        if (!objectUser.equals(user)) { serverSender.send(clientSocket,"Этот билет вам не принадлежит,выберите другой ключ.",2); this.execute(null,clientSocket,user);}
                        else {
                            serverSender.send(clientSocket,(String.valueOf(key)),3);
                            Ticket ticket = (Ticket)serverReceiver.receive(clientSocket);
                            ticket.setUser(user);

                            ticketCollection.putTicket(key,ticket);
                            dBworking.uploadAllTickets();
                            serverSender.send(clientSocket,"В коллекцию успешно добавлен элемент.",0);
                        }
                    } else serverSender.send(clientSocket,"Ну тогда ничего не выйдет,попробуйте что-нибудь другое получается соответственно.",0);

                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            serverSender.send(clientSocket,"Число должно быть типа Long,попробуйте ещё раз.",2);
            this.execute(null,clientSocket,user);
        }
        finally {
            TicketCollection.getLock().writeLock().unlock();
        }
    }

    @Override
    public String getInfo() {
        return "---> Добавить элемент в коллекцию.";
    }
}
