package bloodrunserver.models;

import bloodrunserver.Application;
import bloodrunserver.SoutLogger;
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
    public final List<Checkpoint> checkpoints = new ArrayList<>();

    public final Finish finish = new Finish();

    public Dungeon() {
        getDungeonFromResourcesJson();
    }

    private void getDungeonFromResourcesJson()
    {
        JSONParser parser = new JSONParser();
        //Create new instance to get class type, this instance isn't used afterwards.
        URL resource = Application.class.getClassLoader().getResource("dungeon.json");

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

            Object oCheckpoints = JSONValue.parse(jsonObject.get("checkpoints").toString());
            for(Object object : (JSONArray) oCheckpoints)
            {
                Transform transform = Transform.fromJson(((JSONObject)object).get("transform").toString());
                Scale scale = Scale.fromJson(((JSONObject)object).get("scale").toString());
                Checkpoint checkpoint = new Checkpoint();

                checkpoint.setTransform(transform);
                checkpoint.setScale(scale);

                checkpoints.add(checkpoint);
            }

            Object ofinish = JSONValue.parse(jsonObject.get("finish").toString());

            Transform transform = Transform.fromJson(((JSONObject)ofinish).get("transform").toString());
            Scale scale = Scale.fromJson(((JSONObject)ofinish).get("scale").toString());
            finish.setTransform(transform);
            finish.setScale((scale));

        } catch (IOException e) {
            SoutLogger.log(e.getMessage());
        } catch (ParseException e) {
            SoutLogger.log(e.getMessage());
        }
    }
}
