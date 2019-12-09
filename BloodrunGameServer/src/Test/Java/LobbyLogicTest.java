import bloodrunserver.Application;
import bloodrunserver.game.GameCollection;
import bloodrunserver.logic.game.GameLogic;
import bloodrunserver.logic.lobby.LobbyLogic;
import bloodrunserver.models.Lobby;
import bloodrunserver.models.Player;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class LobbyLogicTest {

    private final LobbyLogic lobbyLogic = new LobbyLogic();;

    @Test
    public void CreateLobbyTest()
    {
        Application.setUpProperties();

        List<Player> players = new ArrayList<Player>();

        players.add(new Player("Tomdatbenik"));
        players.add(new Player("Mario"));
        players.add(new Player("MrLuigi"));
        players.add(new Player("SkullCrusher"));

        lobbyLogic.createLobby(players);

        Assert.assertEquals("Tomdatbenik", GameCollection.getGames().get(0).getPlayers().get(0).getUsername());

        GameCollection.getGames().clear();
    }

}
