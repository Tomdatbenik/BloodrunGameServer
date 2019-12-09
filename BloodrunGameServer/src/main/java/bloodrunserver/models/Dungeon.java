package bloodrunserver.models;

import bloodrunserver.Application;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Dungeon {

    public final List<Spawnpoint> spawnpoints = new ArrayList<Spawnpoint>();
    public final List<Trap> trapList = new ArrayList<Trap>();

    public Dungeon() {
        getDungeonFromResourcesJson();
    }

    private void getDungeonFromResourcesJson()
    {
        JSONParser parser = new JSONParser();
        //Create new instance to get class type, this instance isn't used afterwards.
        Application main = new Application();
        URL resource = main.getClass().getClassLoader().getResource("dungeon.json");

        Object input = null;

        try {
            input = parser.parse(new FileReader(resource.getPath()));

            JSONObject jsonObject = (JSONObject) input;
            Object otraps = JSONValue.parse(jsonObject.get("traps").toString());
            for(Object object : (JSONArray) otraps)
            {
                Trap trap = Trap.fromJson(((JSONObject) object).toJSONString());
                trapList.add(trap);
            }

            Object oSpawnpoints = JSONValue.parse(jsonObject.get("spawnpoints").toString());
            for(Object object : (JSONArray) oSpawnpoints)
            {
                Location location = Location.fromJson(((JSONObject)object).get("location").toString());
                spawnpoints.add(new Spawnpoint(location));
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
    }
}
