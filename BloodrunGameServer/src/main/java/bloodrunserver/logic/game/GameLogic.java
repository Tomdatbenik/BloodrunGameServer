package bloodrunserver.logic.game;

import bloodrunserver.models.Game;
import bloodrunserver.game.GameCollection;
import bloodrunserver.models.Lobby;

public class GameLogic {

    public synchronized void createGame(Lobby lobby)
    {
        //TODO It keeps failing after this game constructor
        Game game = new Game(lobby.getPlayers());

        GameCollection.add(game);
    }
}
