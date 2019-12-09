package bloodrunserver.server;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.*;

import bloodrunserver.Application;
import bloodrunserver.game.GameManager;
import bloodrunserver.icewollowutils.tcp.TCPConnectionHandler;
import bloodrunserver.icewollowutils.udp.*;

public class Server{
    private static DatagramSocket UDPSocket;
    private ServerSocket TCPSocket;
    private boolean running;
    private byte[] buf = new byte[1024];

    private InetAddress address;
    private DatagramPacket packet;

    private UDPListener reader;

    private final static ClientManager clientManager = new ClientManager();

    private static ScheduledExecutorService executor;

    private static ScheduledExecutorService ScheduledMessageExecutor;

    public static DatagramSocket getUDPSocket() {
        return UDPSocket;
    }

    public static ClientManager getClientManager() {
        return clientManager;
    }

    public static ScheduledExecutorService getExecutor() {
        return executor;
    }

    //TODO Clean up this code
    public void startServer()
    {
        //Executor is a thread pool executor. Executor is filled with a instance of Scheduled thread pool
        //The maximum threads it can use is defined in the properties.

        int threads = Integer.parseInt(Application.getProperties().getProperty("Threads.max"));
        executor = Executors.newScheduledThreadPool(threads);

        //Schedule client matching every 5 seconds with a 10 second delay.
        executor.scheduleAtFixedRate(clientManager,10,5, TimeUnit.SECONDS);

        //Create new thread pool. This thread pool is only used for game broadcasts.
        ScheduledMessageExecutor = Executors.newScheduledThreadPool(Integer.parseInt(Application.getProperties().getProperty("Threads.max")));

        //Schedule broadcast every 16 milliseconds with no delay.
        ScheduledMessageExecutor.scheduleAtFixedRate(new GameManager(),0,16, TimeUnit.MILLISECONDS);

        try {
            //Try connecting UDP socket to port defined in the properties
            UDPSocket = new DatagramSocket(Integer.parseInt(Application.getProperties().getProperty("UDP.port")));

            //Try reading from UDP socket. This runs on a seperate thread.
            //TODO use the Threadpool
            reader = new UDPListener(UDPSocket);
            reader.start();
        } catch (SocketException e) {
            System.out.println(e.getMessage());
        }

        try {
            //Try connecting TCP socket to port defined in the properties
            TCPSocket = new ServerSocket(Integer.parseInt(Application.getProperties().getProperty("TCP.port")));

            //Connection handler uses the socket to accept incoming socket connections. See tcpconectionhandler class for more information
            TCPConnectionHandler tcpConnectionHandler = new TCPConnectionHandler(TCPSocket);
            tcpConnectionHandler.start();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        //future proofing for stop restart start server.
        running = true;
    }
}