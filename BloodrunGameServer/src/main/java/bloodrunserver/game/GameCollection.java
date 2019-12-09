package bloodrunserver.game;

import bloodrunserver.models.Game;

import java.util.ArrayList;
import java.util.List;

public class GameCollection {

    //All live games.
    private static final List<Game> games = new ArrayList<Game>();

    //Makes games public!!!!
    //returns the games list.
    public static List<Game> getGames() {
        synchronized (games)
        {
            return games;
        }
    }

    //Add game to list
    public static synchronized void add(Game game)
    {
        games.add(game);
    }

    //Remove game from list
    public static synchronized void remove(Game game)
    {
        games.remove(game);
    }

}
