package bloodrunserver;

import bloodrunserver.communicatie.MessageExecutor;
import bloodrunserver.icewollowutils.models.Message;
import bloodrunserver.icewollowutils.models.MessageType;
import bloodrunserver.models.*;
import bloodrunserver.models.Player;
import bloodrunserver.server.Server;

import java.util.ArrayList;
import java.util.List;

public class CreateTestLobby {
    //TODO Create test lobby

    public void testLobyy()
    {
        List<Player> players = new ArrayList<Player>();

        players.add(new Player("Tomdatbenik"));
//        players.add(new Player("Piet"));
        players.add(new Player("Mario"));
//        players .add(new Player("MrLuigi"));
//        players.add(new Player("Aap"));

        Lobby lobby = new Lobby(players);

        Message message = new Message("WebSocket", lobby.toJson(), MessageType.CREATE_LOBBY);

        Server.getExecutor().submit(new MessageExecutor(message));

        players = new ArrayList<Player>();

//        players.add(new Player("Mario"));
        players.add(new Player("Aap"));
//        players.add(new Player("Henk"));
//        players.add(new Player("Jan"));
//        players.add(new Player("Piet"));
//        players.add(new Player("Ron"));

        lobby = new Lobby(players);

        message.setContent( lobby.toJson());

        Server.getExecutor().submit(new MessageExecutor(message));

        players = new ArrayList<Player>();

        players.add(new Player("piet"));
        players.add(new Player("sint"));
        players.add(new Player("kerstman"));
        players.add(new Player("elf"));

        lobby = new Lobby(players);

        message.setContent( lobby.toJson());

        Server.getExecutor().submit(new MessageExecutor(message));
    }
}
