package bloodrunserver.icewollowutils.tcp;

import bloodrunserver.SoutLogger;
import bloodrunserver.communicatie.MessageExecutor;
import bloodrunserver.icewollowutils.models.Message;
import bloodrunserver.server.Server;

import java.io.*;
import java.net.Socket;
import java.util.zip.GZIPInputStream;

public class TCPListener extends Thread{

    private Socket socket;
    private Boolean isOpen = true;

    //TCP listener needs a socket to send and receive!
    public TCPListener(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public void run() {
        super.run();
        SoutLogger.log("Started listening on thread: " + this);
        while (Boolean.TRUE.equals(isOpen))
        {
            try {
                Message msg = readmessage();

                if(msg != null)
                {
                    addToBuffer(msg);
                }
            } catch (IOException e) {
                SoutLogger.log(e.getMessage());
                isOpen = false;
                try {
                    socket.close();
                    SoutLogger.log("Socket closed");
                } catch (IOException ex) {
                    SoutLogger.log("Socket can't be closed");
                    SoutLogger.log(e.getMessage());
                }
            }
        }
    }

    public Message readmessage() throws IOException {

        GZIPInputStream zip = new GZIPInputStream(socket.getInputStream());

        StringBuilder sb = new StringBuilder();

        while (true) {
            int c;
            c = zip.read();
            if (c == -1)
                break;
            sb.append((char)c);
        }

        String json = sb.toString();

        if(json != null)
        {
            SoutLogger.log("TCPListener read: " + json);
            return Message.fromJson(json);
        }

        return null;
    }

    public void addToBuffer(Message message)
    {
        Server.getExecutor().submit(new MessageExecutor(message));
    }
}
