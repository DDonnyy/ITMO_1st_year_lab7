package Common.Commands;
import Common.Command;
import Common.Invoker;
import Common.TicketCollection;
import java.io.IOException;
import java.net.Socket;
/**
 * The type Average of price.
 */
public class AverageOfPrice implements Command {
    /**
     * Instantiates a new Average of price.
     */
    public AverageOfPrice(){
        Invoker.regist("average_of_price",this);
    }

    @Override
    public void execute(String par1, Socket clientSocket,String user) throws IOException {
//        DBworking dBworking = new DBworking();
//        dBworking.ConnectionToDB();
//        ServerSender serverSender = new ServerSender();
//        dBworking.loadAllTickets();
//        TicketCollection ticketCollection = new TicketCollection();
//        if (ticketCollection.getSize() > 0) {
//            Double averagePrice = (ticketCollection.getTickets().entrySet().stream().mapToDouble((s) -> s.getValue().getPrice()).reduce(Double::sum).getAsDouble()) / (ticketCollection.getSize());
//            if(ExecuteScript.inExecution){
//                serverSender.send(clientSocket,"Средняя цена за билет = " + averagePrice,2);
//            } else
//                serverSender.send(clientSocket,"Средняя цена за билет = " + averagePrice,0);
//        }
//        else {
//            if(ExecuteScript.inExecution){
//                serverSender.send(clientSocket,"Коллекция пуста,средней цены нет.",2);
//            } else
//                serverSender.send(clientSocket,"Коллекция пуста,средней цены нет.",0);
//        }
    }

    @Override
    public String getInfo() {
        return "-----> Возвращает среднюю цену всех билетов";
    }
}
