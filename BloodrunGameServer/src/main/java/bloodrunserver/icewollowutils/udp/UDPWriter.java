package bloodrunserver.icewollowutils.udp;

import bloodrunserver.SoutLogger;
import bloodrunserver.server.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPWriter implements Runnable {
    private DatagramSocket socket;
    private DatagramPacket packet;

    public UDPWriter(DatagramPacket packet) {
        //UDP needs a Socket and A Packet.
        //Socket is the port it will send from, this is opened in the Server
        //Packet has the data in bytes (GZIP), Address and Port belonging to the unity client.
        this.socket = Server.getUdpsocket();
        this.packet = packet;
    }

    public synchronized void run() {
        write();
    }

    private void write(){
       try {
           //System.out.println("Write to: " + packet.getAddress()+ ":" + packet.getPort() + ", Message: " + Compressor.decompress(packet.getData()));
            //Sends Message to unity client with UDP
            socket.send(packet);
        } catch (IOException e) {
           SoutLogger.log(e.getMessage());
        }
    }
}

