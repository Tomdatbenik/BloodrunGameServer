package bloodrunserver.communicatie;

import bloodrunserver.icewollowutils.models.Message;
import bloodrunserver.logic.Chat.ChatLogic;
import bloodrunserver.logic.lobby.LobbyLogic;
import bloodrunserver.logic.player.PlayerLogic;
import bloodrunserver.models.Lobby;
import bloodrunserver.models.Player;
import bloodrunserver.server.Server;

public class MessageExecutor implements Runnable{

    private final Message message = new Message();
    private static final LobbyLogic lobbyLogic = new LobbyLogic();
    private static final PlayerLogic playerLogic = new PlayerLogic();
    private static final ChatLogic chatLogic = new ChatLogic();

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
            case CHAT:
                chatLogic.SendChatMessageToPlayers(this.message);
               break;
            case PUSHING:
                Player player = Player.fromJson(message.getContent());
                playerLogic.playerPush(player);
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
        synchronized(playerLogic)
        {
            Player player = Player.fromJson(message.getContent());
            System.out.println(player.getTransform().getLocation().toJson());
            playerLogic.movePlayer(player);
        }
    }
}
