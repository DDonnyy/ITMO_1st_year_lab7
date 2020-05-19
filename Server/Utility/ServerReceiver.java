package Utility;

import Utility.CreateServer;
import com.sun.corba.se.spi.ior.IORFactories;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Map;

public class ServerReceiver {
    public  Object receive(Socket client) throws IOException,SocketException {
        Object obj = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(client.getInputStream());
            obj = objectInputStream.readObject();
            }
        catch ( ClassNotFoundException e) {
        }
        return obj;
    }
}

