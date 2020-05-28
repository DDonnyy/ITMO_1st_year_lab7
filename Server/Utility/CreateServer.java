package Utility;

import Common.Commands.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.*;
import java.nio.channels.DatagramChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLOutput;

public class CreateServer {
    public static ServerSocket server;

    public static void checkForExitCommand() throws IOException {
        Thread backgroundReaderThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
                    while (!Thread.interrupted()) {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        if (line.equalsIgnoreCase("exit")) {
                            System.out.println("Я отключаюсь,всем ня пока:(");
                            System.exit(0);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        backgroundReaderThread.setDaemon(true);
        backgroundReaderThread.start();
    }

    public static void create() throws IOException {
        RemoveAllByPerson removeAllByPerson = new RemoveAllByPerson();
        Remove_lower remove_lower = new Remove_lower();
        RemoveGreater removeGreater = new RemoveGreater();
        KillKirill killKirill = new KillKirill();
        Exit exit = new Exit();
        Show show = new Show();
        Help help = new Help();
        Clear clear = new Clear();
        Info info = new Info();
        FilterByPrice filterByPrice = new FilterByPrice();
        AverageOfPrice averageOfPrice = new AverageOfPrice();
        RemoveByKey removeByKey = new RemoveByKey();
        InsertKey insertKey = new InsertKey();
        UpdateBykey updateBykey = new UpdateBykey();
        ExecuteScript executeScript = new ExecuteScript();
        ReplaceIfGreater replaceIfGreater = new ReplaceIfGreater();
        SocketAddress it = new InetSocketAddress(InetAddress.getLocalHost(), 1200);
        CreateServer.checkForExitCommand();
        try {
            server = new ServerSocket(3016);
        } catch (BindException e){
            System.out.println("Данный порт уже занят,возможно сервер уже запущен.\n Принудительно завершаю работу.");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("ОШИБКА чегото там");
        }

    }
    public static String PasswordCoder(String st) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NullPointerException ignored){

        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while( md5Hex.length() < 32 ){
            md5Hex = "0" + md5Hex;
        }

        return md5Hex;
    }
}
