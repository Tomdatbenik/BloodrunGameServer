package bloodrunserver.icewollowutils.tcp;

import bloodrunserver.SoutLogger;
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

            dataOutputStream.write(msg);
            dataOutputStream.flush();

//            SoutLogger.log("Sending TCP message: " + message.toString());
        } catch (IOException e) {
            SoutLogger.log(e.getMessage());
        }
    }
}
