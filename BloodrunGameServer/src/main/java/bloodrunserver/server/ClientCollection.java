package bloodrunserver.server;

import java.util.ArrayList;
import java.util.List;

public class ClientCollection {

    //Stores clients
    private final static List<Client> clients = new ArrayList<Client>();

    public static List<Client> getClients() {
        return clients;
    }

    public static void add(Client client)
    {
        System.out.println(client+ " Username: " + client.username);
        clients.add(client);
    }
}
