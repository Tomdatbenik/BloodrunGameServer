package bloodrunserver.models;

import bloodrunserver.Application;
import bloodrunserver.SoutLogger;
import bloodrunserver.icewollowutils.models.Message;
import bloodrunserver.icewollowutils.models.MessageType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {
    //TODO Create properties ip and port

    private List<Player> players;
    private List<Projectile> projectiles = new ArrayList<Projectile>();
    private List<Trap> traps = new ArrayList<Trap>();
    private Dungeon dungeon;

    private boolean isFinished = false;
    private Player winner = null;

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Game(List<Player> players) {
        this.players = players;
        dungeon = new Dungeon();

        this.traps = dungeon.trapList;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public synchronized void updateGame()
    {
        for (Trap trap : traps)
        {
            trap.activateTrap();
        }
    }

    public synchronized void udpBroadcast() {
        for(Player player : players)
        {
            Message message = new Message("Server", this.toJson().toJSONString(), MessageType.GAME);
            try {
                if(player.getClient() != null)
                {
                    player.getClient().sendUDPMessage(message);
                }
            } catch (IOException e) {
                SoutLogger.log(e.getMessage());
            }
        }
    }

    public synchronized void tcpBroadCast()
    {
        for(Player player : players) {
            Message message = new Message("Server", this.toJson().toJSONString(), MessageType.GAME);
            if (player.getClient() != null) {
                player.getClient().sendTCPMessage(message);
            }
        }
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        //Players
        JSONObject jsonplayers = new JSONObject();

        for(int i = 1; i <= Integer.parseInt(Application.getProperties().getProperty("Game.Maxplayers")); i++)
        {
            Player player = players.get(i-1);

            jsonplayers.put("player_" + i, player.toJson());
        }

        jsonObject.put("players",jsonplayers);

        //Projectiles
        JSONObject jsonProjectiles = new JSONObject();

        for (Projectile projectile : projectiles)
        {
            jsonProjectiles.put("projectile", projectile.toJson());
        }

        jsonObject.put("projectiles",jsonProjectiles);

        //Traps
        JSONArray jatraps = new JSONArray();

        for (Trap trap : traps)
        {
            jatraps.add(trap.toJson());
        }

        //checkpoints
        JSONArray jaCheckpoints = new JSONArray();

        for (Checkpoint checkpoint :  dungeon.checkpoints)
        {
            jaCheckpoints.add(checkpoint.toJson());
        }

        jsonObject.put("checkpoints",jaCheckpoints);

        jsonObject.put("traps", jatraps);

        jsonObject.put("players",jsonplayers);

        jsonObject.put("finish", dungeon.finish.toJson());

        return jsonObject;
    }
}
