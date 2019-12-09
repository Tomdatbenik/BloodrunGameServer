package bloodrunserver.models;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Rotation {

    String x;
    String y;
    String z;
    String w;

    public Rotation(){
        x = "0";
        y = "0";
        z = "0";
        w = "1";
    }

    public Rotation(String x, String y, String z, String w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public JSONObject toJson() {
        JSONObject jsonMessage = new JSONObject();
        jsonMessage.put("x", this.x);
        jsonMessage.put("y", this.y);
        jsonMessage.put("z", this.z);
        jsonMessage.put("w", this.w);

        return jsonMessage;
    }

    public static Rotation fromJson(String jsonstring) {
        Object jsonvalue = JSONValue.parse(jsonstring);
        JSONObject object = (JSONObject) jsonvalue;

        String x = object.get("x").toString();
        String y = object.get("y").toString();
        String z = object.get("z").toString();
        String w = object.get("w").toString();

        return new Rotation(x, y, z, w);
    }

}
