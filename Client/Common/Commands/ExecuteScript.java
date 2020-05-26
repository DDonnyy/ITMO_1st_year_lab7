package Common.Commands;

import Common.Command;
import Common.FileRead;
import Common.Invoker;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * The type Execute script.
 */
public class ExecuteScript implements Command {
    /**
     * Instantiates a new Execute script.
     */
    public ExecuteScript(){
        Invoker.regist("execute_script",this);
    }
    private static TreeSet<String> fileNames = new TreeSet<>();
    private static int numberOfExecution=0;
    /**
     * The constant inExecution - shows if the script executed from file.
     */
    public static boolean inExecution = false;


    /**
     * Gets execute data.
     *
     * @return the execute data
     */
    public static String[] getExecuteData() {
        return executeData;
    }

    private static String[] executeData = new String[12];
    @Override
    public void execute(String par1, Socket clientSocket,String user) throws IOException {
//        ServerSender serverSender = new ServerSender();
//        try {
//            fileNames.add(par1);
//            String data = FileRead.readFromFile( par1);
//            String[] stroka = data.split("\r\n");
//            for (int i=0;i<stroka.length;i++) {
//                int number = i;
//                inExecution = true;
//                String[] commandAndPar;
//                commandAndPar = stroka[i].split(" ");
//                if (commandAndPar[0].equals("execute_script")) {
//                    Iterator iterator = fileNames.iterator();
//                    boolean alreadyInList = false;
//                    while (iterator.hasNext()) {
//                        if (commandAndPar[1].equals(iterator.next())) alreadyInList = true;
//                    }
//                    if (alreadyInList) {
//                        serverSender.send(clientSocket,"\n!!!Попытка зациклить программу прервана,постарайтесь такого больше не допускать.\n",2);
//                    } else {
//                        fileNames.add(commandAndPar[1]);
//                        ++numberOfExecution;
//                        Invoker.execute(stroka[i],clientSocket,user);
//                        --numberOfExecution;
//                    }
//                } else {
//                    if (commandAndPar[0].equals("insert_key") || commandAndPar[0].equals("update_key")) {
//
//                        for (int j = 0; (j < 12 && i < stroka.length - 1); j++) {
//                            ++i;
//                            executeData[j] =stroka[i];
//                        }
//                        Invoker.execute(stroka[number],clientSocket,user);
//                        executeData = null;
//                    } else Invoker.execute(stroka[i],clientSocket,user);
//                }
//                Thread.sleep(50);
//            }
//            if (numberOfExecution==0){ fileNames.clear();inExecution = false;serverSender.send(clientSocket,"",0);}
//
//        } catch (AccessDeniedException ex) {
//            if (ExecuteScript.inExecution) serverSender.send(clientSocket,"Нет доступа к файлу.",2);
//            else serverSender.send(clientSocket,"Нет доступа к файлу.",0);
//        }
//        catch (NullPointerException ex){
//            if (ExecuteScript.inExecution) serverSender.send(clientSocket,"Имя файла не указано или файл пустой.",2);
//            else serverSender.send(clientSocket,"Имя файла не указано или файл пустой.",0);
//
//        }
//        catch (FileNotFoundException ex){
//            if (ExecuteScript.inExecution) serverSender.send(clientSocket,"Файл не найден,попробуйте ещё раз.",2);
//            else serverSender.send(clientSocket,"Файл не найден,попробуйте ещё раз.",0);;
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public String getInfo() {
        return "---> Cчитать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }
}
