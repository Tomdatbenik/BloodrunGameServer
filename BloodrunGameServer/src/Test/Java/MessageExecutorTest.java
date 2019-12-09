import bloodrunserver.Application;
import bloodrunserver.communicatie.MessageExecutor;
import bloodrunserver.game.GameCollection;
import bloodrunserver.icewollowutils.models.Message;
import bloodrunserver.icewollowutils.models.MessageType;
import bloodrunserver.models.Lobby;
import bloodrunserver.models.Player;
import bloodrunserver.server.Server;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MessageExecutorTest {
    static final Application main = new Application();;

    @BeforeClass
    public static void Setup()
    {
        main.setUpProperties();
        main.startServer();
    }

    public static List<Message> AddMessagesToBuffer()
    {
        List<Message> messages = new ArrayList<Message>();

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

        messages.add(message);
        messages.add(message1);

        return messages;
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
    public void TestAddMultipleMessage()  {
        AddMessagesToBuffer();

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
            Assert.assertEquals("Tomdatbenik", GameCollection.getGames().get(0).getPlayers().get(0).getUsername());
            Assert.assertEquals("Henk", GameCollection.getGames().get(1).getPlayers().get(0).getUsername());
        }

        GameCollection.getGames().clear();
    }
}
