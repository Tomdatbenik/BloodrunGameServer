package bloodrunserver.server;

import bloodrunserver.icewollowutils.Compressor;
import bloodrunserver.icewollowutils.models.Message;
import bloodrunserver.icewollowutils.tcp.TCPListener;
import bloodrunserver.icewollowutils.tcp.TCPWriter;
import bloodrunserver.icewollowutils.udp.UDPWriter;

import java.io.IOException;
import java.net.*;

public class Client {

    private Socket TCPSocket;

    private TCPListener tcpListener;
    private UDPWriter udpWriter;
    private TCPWriter tcpWriter;

    private InetAddress address;
    int port;

    String username;

    public String getUsername() {
        return username;
    }

    public Client()
    {

    }

    public Client(Socket socket,TCPListener tcpListener,String Username,String ip, int Port) {
        //When client is crated save socket so the server can talk with the client.
        this.TCPSocket = socket;

        //Server will start a listener so the server can listen to the client.
        this.tcpListener = tcpListener;
        this.username = Username;
        this.port = Port;
        try {
            this.address = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            System.err.println(e.getMessage());
        }
        tcpListener.start();
    }

    public void sendTCPMessage(Message message)
    {
        //Create tcpwriter and put it in the executor Queue of the server.
        tcpWriter = new TCPWriter(TCPSocket,message);
        Server.getExecutor().submit(tcpWriter);
    }

    public void sendUDPMessage(Message message) throws IOException {
        //UDP requires a packet to send data to client.
        byte[] buf = Compressor.compress(message.toJson().toJSONString());
        DatagramPacket packet = new DatagramPacket(buf, buf.length, this.address, this.port);

        //Create udpwriter and put it in the executor Queue of the server.
        udpWriter = new UDPWriter(packet);
        Server.getExecutor().submit(udpWriter);
    }
}
