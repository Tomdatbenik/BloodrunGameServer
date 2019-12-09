package bloodrunserver.communicatie;

import bloodrunserver.icewollowutils.models.Message;
import bloodrunserver.logic.lobby.LobbyLogic;
import bloodrunserver.logic.player.PlayerLogic;
import bloodrunserver.models.Lobby;
import bloodrunserver.models.Player;
import bloodrunserver.server.Server;

public class messageExecutor implements Runnable{

    private final Message message = new Message();
    private static final LobbyLogic lobbyLogic = new LobbyLogic();
    private static final PlayerLogic PlayerLogic = new PlayerLogic();


    public messageExecutor(Message newMessage)
    {
        synchronized (this.message)
        {
            this.message.setSender(newMessage.getSender());
            this.message.setContent(newMessage.getContent());
            this.message.setType(newMessage.getType());
        }
    }

    public synchronized void run() {
        //TODO if or switch case on messageType.
        switch (message.getType()) {
            case CREATE_LOBBY:
            {
                //TODO If your in release mode this has to be commented out or else the messagereader will fail
                //System.out.println(Thread.currentThread() +"Executing: " + message.toString());
                createLobby();
                break;
            }
            case DISCONNECT:
            {
                disconnectClient();
                break;
            }
            case MOVE:
            {
                movePlayer();
                break;
            }
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
