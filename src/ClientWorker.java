import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientWorker implements Runnable{
    // Instance vars
    private Socket clientSocket;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;
    static boolean online =true;

    //creates a client with the specified server socket
    public ClientWorker(Socket clientSocket)
    {
        this.clientSocket=clientSocket;
        createDataStreams();
    }
    //creates the i/o streams to be sent
    public void createDataStreams()
    {
        try {
            dataIn = new DataInputStream(clientSocket.getInputStream());
            dataOut = new DataOutputStream(clientSocket.getOutputStream());
        }
        catch (IOException ex)
        {
            Logger.getLogger(ClientWorker.class.getName()).log(Level.SEVERE, null,ex);
        }
    }
    //tread method to run the program parallely
    // reads out the data when recived
    @Override
    public void run()
    {
        while (online)
        {
            byte[] initializedData = new byte[1];
            try {
                dataIn.read(initializedData,0,initializedData.length);
                //we check for 2 since 2 signals the start of the client data packet
                if(initializedData[0]==2)
                {
                    System.out.println(new String((ReadStream())));
                    online=false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //read from data stream and return data in as a byte array
    private byte[] ReadStream()
    {
        byte[] dataBuffer= null;
        int b=0;
        String lengthBuffer = "";
        //4 is used as shorthand for the data seperator
        try {

            while ((b = dataIn.read()) != 4) {
                lengthBuffer+=(char) b;
            }
            int dataLength = Integer.parseInt(lengthBuffer);
            dataBuffer=new byte[dataLength];
            int byteRead=0;
            int byteOffset=0;
            while (byteOffset<dataLength)
            {
                byteRead=dataIn.read(dataBuffer, byteOffset,dataLength-byteOffset);
                byteOffset+=byteRead;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return dataBuffer;
    }

    //getter for being online
    public boolean isOnline()
    {
        return online;
    }
    //setter for being offline
    public void setOnline(boolean status)
    {
        online=status;
    }

}
