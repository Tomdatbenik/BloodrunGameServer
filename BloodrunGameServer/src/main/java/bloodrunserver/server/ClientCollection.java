package bloodrunserver.server;

import bloodrunserver.SoutLogger;

import java.util.ArrayList;
import java.util.List;

public class ClientCollection {

    private ClientCollection(){}

    //Stores clients
    private static final List<Client> clients = new ArrayList<Client>();

    public static List<Client> getClients() {
        return clients;
    }

    public static void add(Client client)
    {
        SoutLogger.log(client+ " Username: " + client.username);
        clients.add(client);
    }
}
