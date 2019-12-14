package bloodrunserver.logic.Chat;

import bloodrunserver.game.GameCollection;
import bloodrunserver.icewollowutils.models.Message;
import bloodrunserver.models.Game;
import bloodrunserver.models.Player;

public class ChatLogic {

    public synchronized void SendChatMessageToPlayers(Message message)
    {
        for(Game game : GameCollection.getGames())
        {
            Player player = game.getPlayers()
                    .stream()
                    .filter(player1 -> player1.getUsername().equals(message.getSender()))
                    .findAny()
                    .orElse(null);

            if(player != null)
            {
                for(Player p : game.getPlayers())
                {
                    if(!p.getUsername().equals(message.getSender()))
                    {
                        p.getClient().sendTCPMessage(message);
                    }
                }
            }
        }
    }
}
