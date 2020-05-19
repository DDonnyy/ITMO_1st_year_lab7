package Common.Commands;

import Common.Command;
import Common.Invoker;

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
        System.out.println("Кирилл успешно уничтожен."+par1);
        System.out.print("$ ");
    }

    @Override
    public String getInfo() {
        return "----------> Уничтожение кирилла";
    }
}
