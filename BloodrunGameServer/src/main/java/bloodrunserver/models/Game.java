package bloodrunserver.models;

import bloodrunserver.Application;
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

    private boolean IsFinished = false;
    private Player winner;


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
        return IsFinished;
    }

    public void setFinished(boolean finished) {
        IsFinished = finished;
    }

    //TODO Create JSON Encoder and Decoder

    public synchronized void broadcast() {
        for(Player player : players)
        {
            Message message = new Message("Server", this.toJson().toJSONString(), MessageType.GAME);
            try {
                if(player.getClient() != null)
                {
                    player.getClient().sendUDPMessage(message);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public JSONObject toJson() {
        JSONObject JsonMessage = new JSONObject();

        //Players
        JSONObject JSonPlayers = new JSONObject();

        for(int i = 1; i <= Integer.parseInt(Application.getProperties().getProperty("Game.Maxplayers")); i++)
        {
            Player player = players.get(i-1);

            JSonPlayers.put("player_" + i, player.toJson());

        }

        JsonMessage.put("players",JSonPlayers);

        //Projectiles
        JSONObject JsonProjectiles = new JSONObject();

        for (Projectile projectile : projectiles)
        {
            JsonProjectiles.put("projectile", projectile.toJson());
        }

        JsonMessage.put("projectiles",JsonProjectiles);

        //Traps
        JSONArray jatraps = new JSONArray();

        for (Trap trap : traps)
        {
            jatraps.add(trap.toJson());
        }

        JsonMessage.put("traps", jatraps);

        for(int i = 1; i <= Integer.parseInt(Application.getProperties().getProperty("Game.Maxplayers")); i++)
        {
            Player player = players.get(i-1);

            JSonPlayers.put("player_" + i, player.toJson());

        }

        JsonMessage.put("players",JSonPlayers);

        return JsonMessage;
    }
}
