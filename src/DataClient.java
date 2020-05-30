import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class DataClient {

    //consstructor for client object
    public DataClient()
    {
        try {
            //handeling socket i/o

            Socket objectSocket=new Socket(InetAddress.getByName("127.0.0.1"),6578);
            DataInputStream dataIn = new DataInputStream(objectSocket.getInputStream());
            DataOutputStream dataOut = new DataOutputStream(objectSocket.getOutputStream());

            //data being created
            byte[] buffer =this.CreateDataPacket("Mega ANIME HUGS UWU".getBytes(StandardCharsets.UTF_8)); //data to be sent to the server
            dataOut.write(buffer); //sending data to server
            dataOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //creastes a data packewt in the form of a byte array to send to the server
    private byte[] CreateDataPacket(byte[] data)
    {
        byte[] packet;

        byte[] initializeData = new byte[1];
        initializeData[0]=2;
        byte[] seperator = new byte[1];
        seperator[0]=4;
        byte[] dataLength = String.valueOf(data.length).getBytes(StandardCharsets.UTF_8); //determines the length of the data in bytes
        packet=new byte[initializeData.length+seperator.length+dataLength.length+data.length]; //the packet size

        //copying over all the nessecary packet data into the packet
        System.arraycopy(initializeData,0,packet,0,initializeData.length);
        System.arraycopy(dataLength,0,packet,initializeData.length,dataLength.length);
        System.arraycopy(seperator,0,packet,initializeData.length+dataLength.length,seperator.length);
        System.arraycopy(data,0,packet,initializeData.length+dataLength.length+seperator.length, data.length);

        return packet;
    }
}
