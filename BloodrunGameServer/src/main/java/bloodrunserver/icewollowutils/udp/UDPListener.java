package bloodrunserver.icewollowutils.udp;

import bloodrunserver.communicatie.MessageExecutor;
import bloodrunserver.icewollowutils.Compressor;
import bloodrunserver.icewollowutils.models.Message;
import bloodrunserver.server.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPListener extends Thread{
    private DatagramSocket socket;
    private DatagramPacket packet;

    public UDPListener(DatagramSocket socket) {
        this.socket = socket;
        byte[] ary = new byte[1024];
        this.packet = new DatagramPacket(ary, 1024);
    }

    @Override
    public void run() {
        System.out.println("UDPReader Started.");

        while(true) {
            try {
                read();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void read() throws IOException {
        socket.receive(packet);
        byte[] compressed = packet.getData();
        String data = Compressor.decompress(compressed);
        //System.out.println("From: " + packet.getAddress() + " Port: " + packet.getPort() + ", Message: " + data + ", Bytes: " + compressed );
        //System.out.println("Received message is:" + data);

        Message message = Message.fromJson(data);

        Server.getExecutor().submit(new MessageExecutor(message));
    }
}
