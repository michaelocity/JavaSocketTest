import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    //instance vars
    private static ServerSocket serverSocket;
    //constructor for server
    public Server()
    {
        try {
            serverSocket = new ServerSocket(6578);
            while (ClientWorker.online)
            {
                new Thread(new ClientWorker((serverSocket.accept()))).start(); //creates threads of client workers to check for incomming packets
            }
        }
        catch (IOException ex)
        {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null,ex);
        }
    }


}
