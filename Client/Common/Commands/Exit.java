package Common.Commands;

import Common.Command;
import Common.Invoker;

import java.io.IOException;
import java.net.Socket;

/**
 * The type Common.Commands.Exit.
 */
public class Exit implements Command {
    /**
     * Instantiates a new Common.Commands.Exit.
     */
    public Exit(){
        Invoker.regist("exit",this);
    }
    @Override
    public void execute(String par1, Socket clientSocket,String user) throws IOException {
        System.exit(0);
    }

    @Override
    public String getInfo() {
        return "-----------------> Выход из программы.";
    }
}
