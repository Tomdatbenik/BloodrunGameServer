package bloodrunserver.server;

import bloodrunserver.models.Game;
import bloodrunserver.game.GameCollection;
import bloodrunserver.models.Player;

import java.util.ArrayList;
import java.util.List;

public class ClientManager implements Runnable{

    private static List<Client> MatchedClients = new ArrayList<Client>();

    public void run() {
        //Announce matching clients in console
        System.out.println("Matching clients on thread: " + Thread.currentThread());

        matchClients();
    }

    //TODO Clean up code
    public void matchClients()
    {
        for(Client client : ClientCollection.getClients())
        {
            for(Game game : GameCollection.getGames())
            {
                for(Player player : game.getPlayers())
                {
                    if(player.getUsername().equals(client.getUsername()))
                    {
                        player.setClient(client);
                        player.setConnected(true);
                        //System.out.println("Matched player: " + player.getUsername());
                        //Add client to matched client list to remove it from the client collection
                        MatchedClients.add(client);
                    }
                }
            }
        }

        for (Client client : MatchedClients)
        {
            //Remove client from client collection
            ClientCollection.getClients().remove(client);
        }
    }

    public void unMatchClient(String username)
    {
        for(Game game : GameCollection.getGames())
        {
            for(Player player : game.getPlayers())
            {
                if(player.getUsername().equals(username))
                {
                    player.setClient(null);
                    player.setConnected(false);
                    //System.out.println("UnMatched player: " + player.getUsername());
                }
            }
        }
    }

}
