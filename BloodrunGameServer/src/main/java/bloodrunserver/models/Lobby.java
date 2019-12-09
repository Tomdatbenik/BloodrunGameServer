package bloodrunserver.models;

import bloodrunserver.Application;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.ArrayList;
import java.util.List;

public class Lobby {
    private List<Player> players;

    private boolean IsFinished;

    public synchronized List<Player> getPlayers() {
        return players;
    }

    public boolean isFinished() {
        return IsFinished;
    }

    public void setFinished(boolean finished) {
        IsFinished = finished;
    }

    public Lobby(List<Player> players) {
        this.players = players;
    }

    public String toJson()
    {
        JSONObject jsonMessage = new JSONObject();
        JSONObject jSonPlayers = new JSONObject();
        String playerTemplate = "player_";

        for(int i = 1; i <= Integer.parseInt(Application.getProperties().getProperty("Game.Maxplayers")); i++)
        {
            Player player = null;
            try
            {
                player = players.get(i-1);
                jSonPlayers.put(playerTemplate + i,player.getUsername());
            }
            catch (Exception ex)
            {
                jSonPlayers.put(playerTemplate + i, "null");
            }
        }

        jsonMessage.put(playerTemplate,jSonPlayers.toJSONString());

        return  jsonMessage.toJSONString();

    }

    public static Lobby fromJson(String jsonstring) {
        Object jsonmessage = JSONValue.parse(jsonstring);
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

        return new Lobby(playerList);
    }

}
