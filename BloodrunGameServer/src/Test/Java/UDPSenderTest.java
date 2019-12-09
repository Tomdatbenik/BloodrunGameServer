import bloodrunserver.Application;
import org.junit.BeforeClass;

public class UDPSenderTest {
    static Application main;

    @BeforeClass
    public static void Setup() {
        main = new Application();

        if (main.getProperties() == null) {
            main.setUpProperties();
            main.startServer();
        }
    }
}

//    @Test
//    public void TestUDPWriter() throws IOException, InterruptedException {
//        DatagramSocket clientSocket = new DatagramSocket(19234);
//
//        Message  message = new Message("TestClient","Test",MessageType.Game);
//
//        byte[] sendData = Compressor.compress("test");
//
//        DatagramPacket packet = new DatagramPacket(sendData, sendData.length,InetAddress.getByName("145.93.140.231"),19234);
//
//        Server.executor.schedule(new UDPWriter(packet),1, TimeUnit.SECONDS);
//
//        byte[] buf = new byte[128];
//        DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
//
//        clientSocket.receive(receivePacket);
//
//        String received = Compressor.decompress(receivePacket.getData());
//
//
//        clientSocket.close();
//        Assert.assertEquals("test", received);
//    }
//
//    @Test
//    public void TestUDPGame() throws IOException {
//        Socket TCPclientSocket = new Socket("localhost", 10923);
//        DataOutputStream outToServer = new DataOutputStream(TCPclientSocket.getOutputStream());
//
//        DatagramSocket clientSocket = new DatagramSocket(19234);
//
//        System.out.println(InetAddress.getLocalHost().getHostAddress());
//
//        Message ConnectMessage = new Message("TestClient",InetAddress.getLocalHost().getHostAddress() +":"+ 19234,MessageType.Connect);
//        outToServer.write(Compressor.compress(ConnectMessage.ToJson().toJSONString()));
//
//        List<Player> players = new ArrayList<Player>();
//
//        players.add(new Player("TestClient"));
//        players.add(new Player("Mario"));
//        players.add(new Player("MrLuigi"));
//        players.add(new Player("SkullCrusher"));
//
//        Lobby lobby = new Lobby(players);
//
//        Message message = new Message("WebSocket", lobby.ToJson(), MessageType.CreateLobby);
//
//        Server.executor.submit(new MessageExecutor(message));
//
//        byte[] buf = new byte[128];
//        DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
//
//        clientSocket.receive(receivePacket);
//
//        clientSocket.close();
//        Assert.assertEquals(1,1);
//    }
//
//}
