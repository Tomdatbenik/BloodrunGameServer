package bloodrunserver.icewollowutils.tcp;

import bloodrunserver.icewollowutils.Compressor;
import bloodrunserver.icewollowutils.models.Message;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TCPWriter implements Runnable{

    Socket socket;
    Message message;

    public TCPWriter(Socket socket, Message message) {
        this.socket = socket;
        this.message = message;
    }

    public void run() {
        DataOutputStream dataOutputStream = null;
        try {
            dataOutputStream= new DataOutputStream(socket.getOutputStream());
            byte[] msg = Compressor.compress(message.toJson().toString());

            System.out.println(msg);

            dataOutputStream.write(msg);
            dataOutputStream.flush();

            System.out.println("Sending TCP message: " + message.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
