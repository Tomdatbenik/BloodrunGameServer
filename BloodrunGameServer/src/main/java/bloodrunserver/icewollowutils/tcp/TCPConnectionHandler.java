package bloodrunserver.icewollowutils.tcp;

import bloodrunserver.SoutLogger;
import bloodrunserver.icewollowutils.models.Message;
import bloodrunserver.icewollowutils.models.MessageType;
import bloodrunserver.server.Client;
import bloodrunserver.server.ClientCollection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPConnectionHandler extends Thread {

    //TCP needs a server socket to send and receive data!
    private ServerSocket serverSocket;

    //base constructor
    public TCPConnectionHandler(ServerSocket serverSocket)
    {
        this.serverSocket = serverSocket;
    }

    //This will run forever and ever and ever!
    @Override
    public void run() {
        super.run();
        SoutLogger.log("TCPConnectionHandler Started.");

        while(true)
        {
            try {
                //Server waits for someone to connect to this serversocket.
                Socket s = serverSocket.accept();
                //Server has accepted the connection from another tcp socket.
                linkClient(s);
            } catch (IOException e) {
                SoutLogger.log(e.getMessage());
            }
        }
    }

    //TODO Clean up this code.
    private void linkClient(Socket s) throws IOException {
        //Create a new listener so the client can send messages after the first.
        TCPListener tcpListener = new TCPListener(s);

        //Start a thread for the tcplistener to listen on.
        Thread thread = new Thread();
        thread.start();

        //Read first message so the server can set up UDP connection.
        //First message contains IP and Port of the client.
        Message message = tcpListener.readmessage();

        if(message.getType() == MessageType.CONNECT)
        {
            String[] ipAddress = message.getContent().split(":");
            //TODO create IPAddress model
            String ip = ipAddress[0];
            String port = ipAddress[1];

            //Create new client
            Client client = new Client(s, tcpListener, message.getSender(),ip,Integer.parseInt(port));

            //Add client to Clientcollection, this is used to match players to clients.
            ClientCollection.add(client);

            //Give the client a conformation he connected to the server.
            client.sendTCPMessage(new Message("Server","Connected Success",MessageType.CONNECT));
        }
    }
}
