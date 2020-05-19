package Common;

import Common.Command;
import Utility.ClientReceiver;
import Utility.ClientSender;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * The type Invoker.
 */
public class Invoker  {
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
    public static Map<String,Command> getCommandCollection(){
        return commands;
    }

    /**
     * Execute.
     *
     * @param s the  getting string.
     * @throws IOException the io exception
     */
    public static Map<Command,String> execute(String s) throws IOException {
        Socket socket = null;
        Map<Command,String> commandStringMap = new HashMap<>();
        String name[]=s.split(" ");
        Command command = commands.get(name[0]);
        if (s.equals("")){ System.out.print("$ "); }
       else if (command == null || name.length>2){
            System.out.println("Такой команды не существует,попробуйте другую. Для справки введите \"help\"");
            System.out.print("$ ");
            return null;
       }
       else if (name[0].equals("help")) {
        command.execute("",null,null);
        }
       else if(name[0].equals("exit")){
           commandStringMap.put(command,null);
            ClientSender.send(commandStringMap);

           command.execute("",null,null);
        }
       else if(name[0].equals("update_key")||name[0].equals("insert_key")){
           String key;
           if (name.length==2)key=name[1]; else key = null;
            commandStringMap.put(command,key);
            ClientSender.send(commandStringMap);
            ClientReceiver.receive();
        }
        else if (name.length == 1){
            commandStringMap.put(command,null);
            return commandStringMap;
        }
        else if (name.length == 2){
            commandStringMap.put(command,name[1]);
            return commandStringMap;
        }
        return null;
    }

    }

