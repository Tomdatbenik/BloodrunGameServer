package bloodrunserver.logic.game;

import bloodrunserver.icewollowutils.models.Message;
import bloodrunserver.icewollowutils.models.MessageType;
import bloodrunserver.models.Game;
import bloodrunserver.game.GameCollection;
import bloodrunserver.models.Lobby;
import bloodrunserver.models.Player;

public class GameLogic {

    public synchronized void createGame(Lobby lobby) {
        //TODO It keeps failing after this game constructor
        Game game = new Game(lobby.getPlayers());

        GameCollection.add(game);
    }

    public synchronized void setWinner(Player player) {
        for (Game game : GameCollection.getGames()) {
            if(!game.isFinished())
            {
                Player p = game.getPlayers().stream().filter(fp -> fp.getUsername().equals(player.getUsername())).findAny().orElse(null);
                if (p != null) {
                    game.setFinished(true);
                    game.setWinner(p);
                    game.getPlayers().stream().forEach(pl ->
                            pl.getClient().sendTCPMessage(
                                    new Message("Server", "Winner is: "+ p.getUsername(), MessageType.FINISH)));
                }
            }
        }
    }
}
