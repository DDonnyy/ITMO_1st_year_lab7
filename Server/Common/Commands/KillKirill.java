package Common.Commands;

import Common.Command;
import Common.Invoker;
import Utility.ServerSender;

import java.io.IOException;
import java.net.Socket;

/**
 * The type Kill kirill.
 */
public class KillKirill implements Command {
    /**
     * Instantiates a new Kill kirill.
     */
    public KillKirill(){
        Invoker.regist("kill_kirill",this);
    }
    @Override
    public void execute(String par1, Socket clientSocket,String user) throws IOException {
        ServerSender serverSender = new ServerSender();
        if (ExecuteScript.inExecution)
            serverSender.send(clientSocket,"Кирилл успешно уничтожен."+par1,2);
    else serverSender.send(clientSocket,"Кирилл успешно уничтожен."+par1,0);
   }

    @Override
    public String getInfo() {
        return "уничтожение кирилла";
    }
}
