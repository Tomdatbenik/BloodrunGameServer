package bloodrunserver.icewollowutils.tcp;

import bloodrunserver.communicatie.messageExecutor;
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
        System.out.println("Started listening on thread: " + this);
        while (isOpen)
        {
            try {
                Message msg = null;
                    try
                        {
                            msg = readmessage();
                        }
                    catch (EOFException ex){
                        System.out.println(ex.getMessage());
                        isOpen = false;
                        socket.close();
                        System.out.println("Socket closed");
                    }
                    if(msg != null)
                    {
                        addToBuffer(msg);
                    }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                isOpen = false;
                try {
                    socket.close();
                    System.out.println("Socket closed");
                } catch (IOException ex) {
                    System.out.println("Socket can't be closed");
                    System.err.println(ex.getMessage());
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

        String Json = sb.toString();

        if(Json != null)
        {
            System.out.println(Json);
            Message message = Message.fromJson(Json);
            return message;
        }

        return null;
    }

    public void addToBuffer(Message message)
    {
        Server.getExecutor().submit(new messageExecutor(message));
    }
}
