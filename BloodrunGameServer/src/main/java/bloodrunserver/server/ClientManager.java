package bloodrunserver.server;

import bloodrunserver.SoutLogger;
import bloodrunserver.models.Game;
import bloodrunserver.game.GameCollection;
import bloodrunserver.models.Player;

import java.util.ArrayList;
import java.util.List;

public class ClientManager implements Runnable{

    private static List<Client> matchedClients = new ArrayList<Client>();

    public void run() {
        //Announce matching clients in console
        SoutLogger.log("Matching clients on thread: " + Thread.currentThread());

        matchClients();
    }

    public void matchClients()
    {
        for(Client client : ClientCollection.getClients())
        {
            for(Game game : GameCollection.getGames())
            {

                Player player = game.getPlayers()
                        .stream()
                        .filter(player1 -> player1.getUsername().equals(client.getUsername()))
                        .findAny()
                        .orElse(null);

                if(player != null)
                {
                    player.setClient(client);
                    player.setConnected(true);
                    SoutLogger.log("Matched player: " + player.getUsername());

                    //Add client to matched client list to remove it from the client collection
                    matchedClients.add(client);
                }
            }
        }

        for (Client client : matchedClients)
        {
            //Remove client from client collection
            ClientCollection.getClients().remove(client);
        }
    }

    public void unMatchClient(String username)
    {
        for(Game game : GameCollection.getGames())
        {
            Player player = game.getPlayers()
                    .stream()
                    .filter(player1 -> player1.getUsername().equals(username))
                    .findAny()
                    .orElse(null);

            if(player != null)
            {
                player.setClient(null);
                player.setConnected(false);
                System.out.println("UnMatched player: " + player.getUsername());
            }
        }
    }

}
