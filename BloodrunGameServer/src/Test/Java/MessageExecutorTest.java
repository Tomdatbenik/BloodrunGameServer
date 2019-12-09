import bloodrunserver.Application;
import bloodrunserver.communicatie.MessageExecutor;
import bloodrunserver.game.GameCollection;
import bloodrunserver.icewollowutils.models.Message;
import bloodrunserver.icewollowutils.models.MessageType;
import bloodrunserver.logic.game.GameLogic;
import bloodrunserver.logic.player.PlayerLogic;
import bloodrunserver.models.Game;
import bloodrunserver.models.Lobby;
import bloodrunserver.models.Location;
import bloodrunserver.models.Player;
import bloodrunserver.server.Server;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MessageExecutorTest {
    static final Application main = new Application();
    private final GameLogic gameLogic = new GameLogic();
    private final PlayerLogic playerLogic = new PlayerLogic();

    @BeforeClass
    public static void Setup()
    {
        main.setUpProperties();
        main.startServer();
    }

    public static void AddGamesToExecutor()
    {
        List<Player> players = new ArrayList<Player>();

        players.add(new Player("Tomdatbenik"));
        players.add(new Player("Mario"));
        players.add(new Player("MrLuigi"));
        players.add(new Player("SkullCrusher"));

        Lobby lobby = new Lobby(players);

        Message message = new Message("WebSocket", lobby.toJson(), MessageType.CREATE_LOBBY);

        Server.getExecutor().submit(new MessageExecutor(message));

        players = new ArrayList<Player>();

        players.add(new Player("Henk"));
        players.add(new Player("Jan"));
        players.add(new Player("Piet"));
        players.add(new Player("Ron"));

        lobby = new Lobby(players);

        Message message1 = new Message("WebSocket", lobby.toJson(), MessageType.CREATE_LOBBY);

        Server.getExecutor().submit(new MessageExecutor(message1));
    }

//    @Test
//    public void TestAddMessage() throws InterruptedException {
//        List<Player> players = new ArrayList<Player>();
//
//        players.add(new Player("Tomdatbenik"));
//        players.add(new Player("Mario"));
//        players.add(new Player("MrLuigi"));
//        players.add(new Player("SkullCrusher"));
//
//        Lobby lobby = new Lobby(players);
//
//        Message message = new Message("WebSocket", lobby.ToJson(), MessageType.CreateLobby);
//
//        main.server.executor.submit(new MessageExecutor(message));
//
//        Thread.sleep(1);
//
//        Assert.assertEquals(1,GameCollection.getGames().size());
//        Assert.assertEquals("Tomdatbenik", GameCollection.getGames().get(0).getPlayers().get(0).getUsername());
//    }

    @Test
    public void TestAddMultipleGames()  {
        AddGamesToExecutor();

        while(GameCollection.getGames().size() != 2)
        {

        }

        if(GameCollection.getGames().get(0).getPlayers().get(0).getUsername().equals("Henk"))
        {
            Assert.assertEquals("Henk", GameCollection.getGames().get(0).getPlayers().get(0).getUsername());
            Assert.assertEquals("Tomdatbenik", GameCollection.getGames().get(1).getPlayers().get(0).getUsername());
        }
        else if(GameCollection.getGames().get(0).getPlayers().get(0).getUsername().equals("Tomdatbenik"))
        {
            List<Game> games = GameCollection.getGames();
            Assert.assertEquals("Tomdatbenik", GameCollection.getGames().get(0).getPlayers().get(0).getUsername());
            Assert.assertEquals("Henk", GameCollection.getGames().get(1).getPlayers().get(0).getUsername());
        }

        GameCollection.getGames().clear();
    }

    @Test
    public void TestMovePlayer()
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

        Server.getExecutor().submit(new MessageExecutor(
                new Message(player.getUsername(),
                player.getTransform().getLocation().toJson().toJSONString(),
                MessageType.MOVE)));

        Assert.assertEquals("3", GameCollection.getGames().get(0).getPlayers().get(0).getTransform().getLocation().getY());
        Assert.assertEquals("3", GameCollection.getGames().get(0).getPlayers().get(0).getTransform().getLocation().getX());
        Assert.assertEquals("3", GameCollection.getGames().get(0).getPlayers().get(0).getTransform().getLocation().getZ());

        GameCollection.getGames().clear();
    }
}
