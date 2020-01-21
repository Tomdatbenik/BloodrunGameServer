package bloodrunserver.game;

import bloodrunserver.Application;
import bloodrunserver.icewollowutils.models.Message;
import bloodrunserver.models.Game;
import bloodrunserver.models.dto.GameFinishedDto;
import bloodrunserver.models.dto.MicroServiceRequest;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;

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
                //TODO send message to lobby microservice who has won.

                GameFinishedDto gameFinishedDto = new GameFinishedDto(game.getPlayers(), game.getWinner());

                String url = Application.getProperties().getProperty("lobby.url");

                Message message = new Message();

                Gson gson =  new Gson();

                message.setContent(gson.toJson(gameFinishedDto));

                MicroServiceRequest.PublicCreateRequest(url,message.getContent(), String.class);
            }
        }

        //Remove games from GameCollection
        for(Game game : removedgames)
        {
            GameCollection.remove(game);
        }
    }
}
