import bloodrunserver.Application;
import bloodrunserver.game.GameCollection;
import bloodrunserver.logic.game.GameLogic;
import bloodrunserver.logic.player.PlayerLogic;
import bloodrunserver.models.Lobby;
import bloodrunserver.models.Location;
import bloodrunserver.models.Player;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PlayerLogicTests {

    private final GameLogic gameLogic = new GameLogic();
    private final PlayerLogic playerLogic = new PlayerLogic();

    @Test
    public void TestMoveUserSucces()
    {
        Application.setUpProperties();

        List<Player> players = new ArrayList<Player>();

        players.add(new Player("Tomdatbenik"));
        players.add(new Player("Mario"));
        players.add(new Player("MrLuigi"));
        players.add(new Player("SkullCrusher"));

        Lobby lobby = new Lobby(players);

        gameLogic.createGame(lobby);

        Player player = players.get(0);

        player.getTransform().setLocation(new Location("3","3", "3"));

        playerLogic.movePlayer(player);

        Assert.assertEquals("3", GameCollection.getGames().get(0).getPlayers().get(0).getTransform().getLocation().getY());
        Assert.assertEquals("3", GameCollection.getGames().get(0).getPlayers().get(0).getTransform().getLocation().getX());
        Assert.assertEquals("3", GameCollection.getGames().get(0).getPlayers().get(0).getTransform().getLocation().getZ());

        GameCollection.getGames().clear();
    }
}
