package bloodrunserver.logic.lobby;

import bloodrunserver.logic.game.GameLogic;
import bloodrunserver.models.Lobby;
import bloodrunserver.models.Player;

import java.util.List;

public class LobbyLogic {

    private final GameLogic gameLogic = new GameLogic();;

    public void createLobby(List<Player> players)
    {
        Lobby lobby = new Lobby(players);

        gameLogic.createGame(lobby);
    }

}
