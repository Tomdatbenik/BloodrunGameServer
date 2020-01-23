package bloodrunserver.game;

import bloodrunserver.Application;
import bloodrunserver.SoutLogger;
import bloodrunserver.icewollowutils.models.Message;
import bloodrunserver.models.Game;
import bloodrunserver.models.dto.GameCloseDto;
import bloodrunserver.models.dto.GameFinishedDto;
import bloodrunserver.models.dto.MicroServiceRequest;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameManager implements Runnable{

    public synchronized void run() {
//        System.out.println("managing games on thread: " + Thread.currentThread());
        //Get all games from the game collection and broadcasting them.
        List<Game> removedgames = new ArrayList<Game>();

        for(Game game : GameCollection.getGames())
        {
            if(!game.isFinished())
            {
                //Send the game itself to all the players that belong to the game.
                game.updateGame();
                game.udpBroadcast();
            }
            else
            {
                game.setFinished(true);
                game.tcpBroadCast();
                removedgames.add(game);

                GameFinishedDto gameFinishedDto = new GameFinishedDto(game.getPlayers(), game.getWinner());

                String url = "http://" + Application.getProperties().getProperty("lobby.url");
                Gson gson =  new Gson();

                try {
                    MicroServiceRequest.PublicCreateRequest(url,gson.toJson(gameFinishedDto), new GameCloseDto() ,HttpMethod.POST);
                } catch (IOException e) {
                    SoutLogger.log(e.getMessage());
                }

                game.getPlayers().stream().forEach(p-> p = null);
                game.getPlayers().clear();
            }
        }

        //Remove games from GameCollection
        for(Game game : removedgames)
        {
            GameCollection.remove(game);
        }
    }
}
