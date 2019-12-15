package bloodrunserver.server;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.*;

import bloodrunserver.Application;
import bloodrunserver.SoutLogger;
import bloodrunserver.game.GameManager;
import bloodrunserver.icewollowutils.tcp.TCPConnectionHandler;
import bloodrunserver.icewollowutils.udp.*;

public class Server{
    private static DatagramSocket udpsocket;
    private ServerSocket tcpsocket;
    private boolean running = false;

    private UDPListener reader;

    private final static ClientManager clientManager = new ClientManager();

    private static ScheduledExecutorService executor;

    private static ScheduledExecutorService scheduledMessageExecutor;

    public static DatagramSocket getUdpsocket() {
        return udpsocket;
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
        scheduledMessageExecutor = Executors.newScheduledThreadPool(Integer.parseInt(Application.getProperties().getProperty("Threads.max")));

        //Schedule broadcast every 16 milliseconds with no delay.
        scheduledMessageExecutor.scheduleAtFixedRate(new GameManager(),0,32, TimeUnit.MILLISECONDS);

        try {
            //Try connecting UDP socket to port defined in the properties
            udpsocket = new DatagramSocket(Integer.parseInt(Application.getProperties().getProperty("UDP.port")));

            //Try reading from UDP socket. This runs on a seperate thread.
            //TODO use the Threadpool
            reader = new UDPListener(udpsocket);
            reader.start();
        } catch (SocketException e) {
            SoutLogger.log(e.getMessage());
        }

        try {
            //Try connecting TCP socket to port defined in the properties
            tcpsocket = new ServerSocket(Integer.parseInt(Application.getProperties().getProperty("TCP.port")));

            //Connection handler uses the socket to accept incoming socket connections. See tcpconectionhandler class for more information
            TCPConnectionHandler tcpConnectionHandler = new TCPConnectionHandler(tcpsocket);
            tcpConnectionHandler.start();
        } catch (IOException e) {
            SoutLogger.log(e.getMessage());
        }

        //future proofing for stop restart start server.
        running = true;
    }
}