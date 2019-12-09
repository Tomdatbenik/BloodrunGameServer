package bloodrunserver.logic.lobby;

import bloodrunserver.logic.game.GameLogic;
import bloodrunserver.models.Lobby;
import bloodrunserver.models.Player;

import java.util.List;

public class LobbyLogic {

    private static GameLogic gameLogic;

    public void createLobby(List<Player> players)
    {
        gameLogic = new GameLogic();

        Lobby lobby = new Lobby(players);

        gameLogic.createGame(lobby);
    }

}
