package bloodrunserver.models;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class    Transform {
    //TODO Create properties ip and port
    //TODO Create JSON Encoder and Decoder

    private Location location;
    private Rotation rotation;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Rotation getRotation() {
        return rotation;
    }

    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }

    public Transform() {
        this.location = new Location();
        this.rotation =  new Rotation();
    }

    public Transform(Location location, Rotation rotation) {
        this.location = location;
        this.rotation = rotation;
    }

    public JSONObject toJson() {
        JSONObject JsonMessage = new JSONObject();
        JsonMessage.put("location", this.location.toJson());
        JsonMessage.put("rotation", this.rotation.toJson());

        return JsonMessage;
    }

    public static Transform fromJson(String Jsonstring) {
        Object jsonvalue = JSONValue.parse(Jsonstring);
        JSONObject object = (JSONObject) jsonvalue;

        String slocation = object.get("location").toString();
        String srotation = object.get("rotation").toString();

        Location location = Location.fromJson(slocation);
        Rotation rotation = Rotation.fromJson(srotation);

        Transform transform = new Transform(location,rotation);
        return transform;
    }
}
