import bloodrunserver.Application;
import bloodrunserver.game.GameCollection;
import bloodrunserver.logic.game.GameLogic;
import bloodrunserver.models.Lobby;
import bloodrunserver.models.Player;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class GameLogicTests {

    private final GameLogic gameLogic = new GameLogic();

    @Test
    public void CreateGameSucces()  {
        Application.setUpProperties();

        List<Player> players = new ArrayList<Player>();

        players.add(new Player("Tomdatbenik"));
        players.add(new Player("Mario"));
        players.add(new Player("MrLuigi"));
        players.add(new Player("SkullCrusher"));

        Lobby lobby = new Lobby(players);

        gameLogic.createGame(lobby);

        Assert.assertEquals("Tomdatbenik", GameCollection.getGames().get(0).getPlayers().get(0).getUsername());

        GameCollection.getGames().clear();
    }
}
