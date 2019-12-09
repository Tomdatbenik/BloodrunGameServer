package bloodrunserver.models;

import bloodrunserver.Application;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.ArrayList;
import java.util.List;

public class Lobby {

    //TODO Create JSON Encoder and Decoder

    Game game;

    public Lobby(List<Player> players) {
        this.players = players;
    }

    private static List<Player> players;

    private boolean IsFinished;

    public synchronized List<Player> getPlayers() {
        return players;
    }

    public synchronized void setPlayers(List<Player> players) {
        this.players = players;
    }

    public boolean isFinished() {
        return IsFinished;
    }

    public void setFinished(boolean finished) {
        IsFinished = finished;
    }

    public String toJson()
    {
        JSONObject JsonMessage = new JSONObject();
        JSONObject JSonPlayers = new JSONObject();

        for(int i = 1; i <= Integer.parseInt(Application.getProperties().getProperty("Game.Maxplayers")); i++)
        {
            Player player = null;
            try
            {
                player = players.get(i-1);
                JSonPlayers.put("player_" + i,player.getUsername());
            }
            catch (Exception ex)
            {
                JSonPlayers.put("player_" + i, "null");
            }
        }

        JsonMessage.put("players",JSonPlayers.toJSONString());

        return  JsonMessage.toJSONString();

    }

    public static Lobby fromJson(String Jsonstring) {
        Object jsonmessage = JSONValue.parse(Jsonstring);
        JSONObject smessage = (JSONObject) jsonmessage;

        Object jsongplayers = JSONValue.parse(smessage.get("players").toString());
        JSONObject players = (JSONObject) jsongplayers;

        List<Player> playerList = new ArrayList<Player>();

        for (int i = 1; i != 5; i++)
        {
            synchronized (playerList)
            {
                playerList.add(new Player(players.get("player_"+i).toString()));
            }
        }

        Lobby lobby = new Lobby(playerList);

        return lobby;
    }

}
