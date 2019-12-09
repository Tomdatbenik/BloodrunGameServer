package bloodrunserver.models;

import java.util.ArrayList;
import java.util.List;

public class Dungeons {

    public final List<Dungeon> dungeonList = new ArrayList<Dungeon>();

    public Dungeons()
    {
        dungeonList.add(new Dungeon());
    }
}
