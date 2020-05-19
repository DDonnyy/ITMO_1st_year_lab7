
import Common.*;
import Common.Commands.*;
import Utility.*;
import org.w3c.dom.ls.LSOutput;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {
    static ExecutorService executeIt = Executors.newFixedThreadPool(10);
    public static void main(String[] args) throws IOException, SQLException {
        CreateServer.create();
        System.out.println("Сервер запущен.Для отключения введите exit в консоль.");
            while (!CreateServer.server.isClosed()) {
                Socket client = CreateServer.server.accept();
                executeIt.execute(new NewConnection(client));
                System.out.println("Принял покдлючение от клиента с адресом: "+client.getLocalAddress()+client.getPort());
                }
            executeIt.shutdown();
    }
}

