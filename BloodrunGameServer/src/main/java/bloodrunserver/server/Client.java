package bloodrunserver.server;

import bloodrunserver.SoutLogger;
import bloodrunserver.icewollowutils.Compressor;
import bloodrunserver.icewollowutils.models.Message;
import bloodrunserver.icewollowutils.tcp.TCPListener;
import bloodrunserver.icewollowutils.tcp.TCPWriter;
import bloodrunserver.icewollowutils.udp.UDPWriter;

import java.io.IOException;
import java.net.*;

public class Client {

    private Socket tcpsocket;

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

    public Client(Socket socket,TCPListener tcpListener,String username,String ip, int port) {
        //When client is crated save socket so the server can talk with the client.
        this.tcpsocket = socket;

        //Server will start a listener so the server can listen to the client.
        this.tcpListener = tcpListener;
        this.username = username;
        this.port = port;
        try {
            this.address = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            SoutLogger.log(e.getMessage());
        }
        tcpListener.start();
    }

    public void sendTCPMessage(Message message)
    {
        //Create tcpwriter and put it in the executor Queue of the server.
        tcpWriter = new TCPWriter(tcpsocket,message);
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
