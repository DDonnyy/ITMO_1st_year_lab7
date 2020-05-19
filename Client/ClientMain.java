import Common.CreatePerson;
import Utility.ClientReceiver;
import Utility.ClientSender;
import Common.Invoker;
import Common.Commands.*;
import Common.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.channels.DatagramChannel;
import java.sql.SQLOutput;
import java.util.Map;

public class ClientMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        RemoveAllByPerson removeAllByPerson = new RemoveAllByPerson();
        Remove_lower remove_lower = new Remove_lower();
        RemoveGreater removeGreater = new RemoveGreater();
        KillKirill killKirill = new KillKirill();
        Exit exit = new Exit();
        Show show = new Show();
        Help help = new Help();
        Clear clear = new Clear();
        Info info = new Info();
        FilterByPrice filterByPrice = new FilterByPrice();
        AverageOfPrice averageOfPrice = new AverageOfPrice();
        RemoveByKey removeByKey = new RemoveByKey();
        InsertKey insertKey = new InsertKey();
        UpdateBykey updateBykey = new UpdateBykey();
        ExecuteScript executeScript = new ExecuteScript();
        ReplaceIfGreater replaceIfGreater = new ReplaceIfGreater();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ClientSender.tryToConnect();
        while (true) {
            ClientReceiver.receive();
            while (ClientSender.serverisconnected) {
                System.out.println("Введите команду для отправки на сервер: ");
                System.out.print("$ ");
                String s = reader.readLine();
                Map<Command, String> commandparamMap = Invoker.execute(s);
                if (commandparamMap != null&&ClientSender.serverisconnected) {
                    ClientSender.send(commandparamMap);
                    ClientReceiver.receive();
                }
            }
            ClientSender.tryToConnect();
        }
    }
    }


