package Utility;

import Common.Command;
import Common.Commands.ExecuteScript;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewConnection implements Runnable {
    static ExecutorService executeIt = Executors.newCachedThreadPool();
    private static Socket clientSocket;
    private String newuser;
    public NewConnection(Socket client){
        NewConnection.clientSocket=client;
    }
    @Override
    public void run() {
        try {
            DBworking dBworking = new DBworking();
            ServerSender serverSender =new ServerSender();
            ServerReceiver serverReceiver = new ServerReceiver();
            if (dBworking.ConnectionToDB()) {
                String key = "";
                boolean islogged = false;
                boolean regist = false;
                while (!(key.toUpperCase().equals("YES") || key.toUpperCase().equals("ДА") || key.toUpperCase().equals("NO") || key.toUpperCase().equals("НЕТ"))) {
                    serverSender.send(clientSocket, "Вы зарегистрированы как пользователь?(Да|Yes/Нет|No,регистр не важен)", 1);
                    key = (String) (serverReceiver.receive(clientSocket));
                }
                    if (key.toUpperCase().equals("YES") || key.toUpperCase().equals("ДА")) {
                        key="";
                        while (!islogged) {
                            serverSender.send(clientSocket, "Введите логин", 1);
                            String login = (String) (serverReceiver.receive(clientSocket));
                            serverSender.send(clientSocket, "Введите пароль", 1);
                            String password = CreateServer.PasswordCoder((String) (serverReceiver.receive(clientSocket)));

                            if (dBworking.userExist(login, password)) {
                                newuser = login;
                                serverSender.send(clientSocket, "Вы успешно авторизованы!Теперь у вас есть доступ к информации о всех билетах и возможность изменять/добавлять свои билеты", 0);
                                islogged = true;
                                regist = false;
                            }
                            else {
                                serverSender.send(clientSocket, "Данные не были найдены.Перейти к регистрации нового пользователя?(Да|Yes/Нет|No,регистр не важен)", 1);
                                key = (String) (serverReceiver.receive(clientSocket));
                                if (key.toUpperCase().equals("YES") || key.toUpperCase().equals("ДА")) {
                                    regist = true;
                                    islogged = true;
                                    key = "";
                                }
                                else {
                                    serverSender.send(clientSocket, "Попробуйте ввести логин и пароль заново", 2);
                                    Thread.sleep(20);
                                    key = "";
                                }
                            }
                        }
                    }
                    islogged= false;
                    if (key.toUpperCase().equals("NO") || key.toUpperCase().equals("НЕТ") || regist) {
                        serverSender.send(clientSocket, "Для работы потребуется регистрация.", 2);
                        Thread.sleep(20);
                        while (!islogged) {
                            serverSender.send(clientSocket, "Введите логин", 1);
                            String login = (String) (serverReceiver.receive(clientSocket));
                            serverSender.send(clientSocket, "Введите пароль", 1);
                            String password = CreateServer.PasswordCoder((String) (serverReceiver.receive(clientSocket)));
                            if (dBworking.addNewUser(login, password)) {
                                serverSender.send(clientSocket, "Вы успешно зарегистрированы,теперь у вас есть доступ к информации о всех билетах и возможность изменять свои/добавлять свои билеты", 0);
                                islogged = true;
                                newuser = login;
                            } else serverSender.send(clientSocket,"Имя пользователя занято,попробуйте ещё раз",2);
                        }
                    }

                executeIt.execute(new executeCommand(serverReceiver,clientSocket,newuser));



            }
        }
        catch (SQLException e){
            e.printStackTrace();
            ServerSender serverSender =new ServerSender();
            serverSender.send(clientSocket,"",5);
            System.out.println("Нет подключения к бд,принудительно отключаю клиента:"+clientSocket.getLocalAddress()+clientSocket.getPort());
        } catch (IOException e) {
            System.out.println("Клиент с адресом:"+clientSocket.getLocalAddress() + clientSocket.getPort()+" отключился");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public class executeCommand implements Runnable{
        ServerReceiver serverReceiver;
        Socket socket;
        String user;

        ServerSender serverSender = new ServerSender();
        public executeCommand (ServerReceiver serverReceiver,Socket socket,String user){
            this.serverReceiver = serverReceiver;
            this.socket = socket;
            this.user= user;
        }
        @Override
        public void run() {
            try {
                while (true) {
                    System.out.println("Ожидаю команду от клиента с адресом: " + socket.getLocalAddress() + socket.getPort());
                    Map<Command, String> commandStringMap;
                    Object o = serverReceiver.receive(socket);
                    commandStringMap = (Map<Command, String>) o;
                    System.out.println("Выполняю команду " + commandStringMap.entrySet().iterator().next().getKey().getClass().getName());
                    commandStringMap.entrySet().iterator().next().getKey().execute(commandStringMap.entrySet().iterator().next().getValue(),socket,newuser);
                }
            } catch (IOException e){
                System.out.println("Клиент с адресом:"+socket.getLocalAddress() + socket.getPort()+" отключился");
            } catch (SQLException e){
                ServerSender serverSender =new ServerSender();
                serverSender.send(clientSocket,"",5);
            }
        }
    }
}
