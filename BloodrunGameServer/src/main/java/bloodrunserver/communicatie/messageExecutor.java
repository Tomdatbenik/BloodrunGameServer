package bloodrunserver.communicatie;

import bloodrunserver.icewollowutils.models.Message;
import bloodrunserver.logic.lobby.LobbyLogic;
import bloodrunserver.logic.player.PlayerLogic;
import bloodrunserver.models.Lobby;
import bloodrunserver.models.Player;
import bloodrunserver.server.Server;

public class MessageExecutor implements Runnable{

    private final Message message = new Message();
    private static final LobbyLogic lobbyLogic = new LobbyLogic();
    private static final PlayerLogic PlayerLogic = new PlayerLogic();

    public MessageExecutor(Message newMessage)
    {
        synchronized (this.message)
        {
            this.message.setSender(newMessage.getSender());
            this.message.setContent(newMessage.getContent());
            this.message.setType(newMessage.getType());
        }
    }

    public synchronized void run() {
        switch (message.getType()) {
            case CREATE_LOBBY:
                createLobby();
                break;
            case DISCONNECT:
                disconnectClient();
                break;
            case MOVE:
                movePlayer();
                break;
            default:
                break;
        }
    }

    private void createLobby()
    {
        synchronized(lobbyLogic)
        {
            lobbyLogic.createLobby(Lobby.fromJson(message.getContent()).getPlayers());
        }
    }

    private void disconnectClient()
    {
        Server.getClientManager().unMatchClient(message.getSender());
    }

    private void movePlayer()
    {
        synchronized(PlayerLogic)
        {
            Player player = Player.fromJson(message.getContent());
            PlayerLogic.movePlayer(player);
        }
    }
}
