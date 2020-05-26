package Common.Commands;

import Common.Command;
import Common.TicketCollection;
import Common.Invoker;

import java.io.IOException;
import java.net.Socket;

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

    @Override
    public void execute(String par1, Socket clientSocket,String user) throws IOException {
        TicketCollection ticketCollection = new TicketCollection();
        if (ticketCollection.getTickets().size()>0){
            ticketCollection.getTickets().clear();
            System.out.println("Коллекция очищена.");
            ticketCollection.setDateOFCreation(null);
        }
        else System.out.println("Коллекция уже пуста.");
        System.out.print("$ ");
    }

    @Override
    public String getInfo() {
        return "----------------> Удаление элементов,принадлежащих вам.";
    }
}
