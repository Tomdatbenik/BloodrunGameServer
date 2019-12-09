package bloodrunserver.logic.game;

import bloodrunserver.models.Game;
import bloodrunserver.game.GameCollection;
import bloodrunserver.models.Lobby;

public class GameLogic {

    public synchronized void createGame(Lobby lobby)
    {
//        System.out.println(Thread.currentThread() + "GameLogic lobbylogic: " + lobby.getPlayers().get(0).getUsername());
        //TODO It keeps failing after this game constructor
        Game game = new Game(lobby.getPlayers());

        GameCollection.add(game);

//        System.out.println(Thread.currentThread() + "GameLogic game: " + game.getPlayers().get(0).getUsername());
    }
}
