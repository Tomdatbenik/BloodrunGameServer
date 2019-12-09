package bloodrunserver.game;

import bloodrunserver.models.Game;

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
                game.broadcast();
            }
            else
            {
                game.setFinished(true);
                game.broadcast();
                removedgames.add(game);
                //TODO maybe make a tcp broadcast also remove the game from the gamemanger and remove this from gamelist
            }
        }

        //Remove games from GameCollection
        for(Game game : removedgames)
        {
            GameCollection.remove(game);
        }
    }
}