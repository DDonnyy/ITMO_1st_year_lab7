package Common;

import Utility.CreateServer;
import Utility.ServerSender;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * The type Invoker.
 */
public class Invoker {
    private static Map<String, Command> commands = new TreeMap<>();
    /**
     * Regist.
     *
     * @param name    the name
     * @param command the command
     */
    public static void regist(String name, Command command) {
        commands.put(name, command);
    }

    /**
     * Get command collection map.
     *
     * @return the map
     */
    public static Map<String, Command> getCommandCollection(){
        return commands;
    }

    /**
     * Execute.
     *
     * @param s the  getting string.
     * @throws IOException the io exception
     */
    public static void execute(String s, Socket clientSocket, String user) throws IOException, SQLException {
        Map<Command,String> commandStringMap = new HashMap<>();
        String name[]=s.split(" ");
        ServerSender serverSender = new ServerSender();
        Command command = commands.get(name[0]);
        if (s.equals("")){ System.out.print("$ "); }
       else if (command == null || name.length>2){
            serverSender.send(clientSocket,"Такой команды не существует,попробуйте другую. Для справки введите \"help\"",2);
       }
        else if (name.length == 1){
            command.execute(null,clientSocket,user);

        }
        else if (name.length == 2){
            command.execute(name[1],clientSocket,user);

        }

    }

    }

