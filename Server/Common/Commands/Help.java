package Common.Commands;

import Common.Command;
import Common.Invoker;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

/**
 * The type Common.Commands.Help.
 */
public class Help implements Command {
    /**
     * Instantiates a new Common.Commands.Help.
     */
    public Help(){
    Invoker.regist("help",this);
}
    @Override
    public void execute(String par1, Socket clientSocket,String user) throws IOException {
    }
    @Override
    public String getInfo() {
        return "---> Вывести справку по доступным командам.";
    }

}

