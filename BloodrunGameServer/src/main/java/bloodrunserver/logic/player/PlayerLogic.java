package bloodrunserver.logic.player;

import bloodrunserver.SoutLogger;
import bloodrunserver.game.GameCollection;
import bloodrunserver.models.*;

public class PlayerLogic {

    public void movePlayer(Player player)
    {
        for(Game game : GameCollection.getGames())
        {
            for(Player p : game.getPlayers())
            {
                if(player.getUsername().equals(p.getUsername()))
                {
                    p.setTransform(player.getTransform());
                    p.setRunning(player.isRunning());
                    p.setVertical(player.getVertical());
                }
            }
        }
    }

    public void playerPush(Player player)
    {
        for(Game game : GameCollection.getGames())
        {
            for(Player p : game.getPlayers())
            {
                if(player.getUsername().equals(p.getUsername()))
                {
                    p.setPushing(player.isPushing());
                }
            }
        }
    }
}
