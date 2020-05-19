package Common.Commands;

import Common.Command;
import Common.FileRead;
import Common.Invoker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.AccessDeniedException;
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
    public static String getExecuteData() {
        return executeData;
    }

    private static String executeData = "";
    @Override
    public void execute(String par1, Socket clientSocket,String user) throws IOException {
        try {
            fileNames.add(par1);
            String data = FileRead.readFromFile("A:\\javaprojects\\src\\" + par1);
            String[] stroka = data.split("\r\n");
            for (int i=0;i<stroka.length;i++) {
                int number = i;
                inExecution = true;
                String[] commandAndPar;
                commandAndPar = stroka[i].split(" ");
                if (commandAndPar[0].equals("execute_script")) {
                    Iterator iterator = fileNames.iterator();
                    boolean alreadyInList = false;
                    while (iterator.hasNext()) {
                        if (commandAndPar[1].equals(iterator.next())) alreadyInList = true;
                    }
                    if (alreadyInList) {
                        System.out.println("\n!!!Попытка зациклить программу прервана,постарайтесь такого больше не допускать.\n");
                    } else {
                        fileNames.add(commandAndPar[1]);
                        ++numberOfExecution;
                        Invoker.execute(stroka[i]);
                        --numberOfExecution;
                    }
                } else {
                    if (commandAndPar[0].equals("insert_key") || commandAndPar[0].equals("update_key")) {

                        for (int j = 0; (j < 11 && i < stroka.length - 1); j++) {
                            ++i;
                            executeData += stroka[i] + ",";
                        }
                        Invoker.execute(stroka[number]);
                        executeData = "";
                    } else Invoker.execute(stroka[i]);
                }
            }
            if (numberOfExecution==0){ fileNames.clear();inExecution = false;}

        } catch (AccessDeniedException ex) {
            System.out.println("Нет доступа к файлу.");
            System.out.print("$ ");
        }
        catch (NullPointerException ex){
            System.out.println("Имя файла не указано или файл пустой.");
            System.out.print("$ ");
        }
        catch (FileNotFoundException ex){
            System.out.println("Файл не найден,попробуйте ещё раз.");
            System.out.print("$ ");
        }
    }

    @Override
    public String getInfo() {
        return "-------> Cчитать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }
}
