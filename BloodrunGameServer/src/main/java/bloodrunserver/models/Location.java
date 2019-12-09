package bloodrunserver.models;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Location {
    String x;
    String y;
    String z;

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }

    public Location(){
        x = "0";
        y = "4";
        z = "0";
    }

    public Location(String x, String y, String z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public JSONObject toJson() {
        JSONObject JsonMessage = new JSONObject();
        JsonMessage.put("x", this.x);
        JsonMessage.put("y", this.y);
        JsonMessage.put("z", this.z);

        return JsonMessage;
    }

    public static Location fromJson(String Jsonstring) {
        Object jsonvalue = JSONValue.parse(Jsonstring);
        JSONObject object = (JSONObject) jsonvalue;

        String x = object.get("x").toString();
        String y = object.get("y").toString();
        String z = object.get("z").toString();

        Location location = new Location(x, y, z);
        return location;
    }
}
