package bloodrunserver.models;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Projectile {

    private Transform transform;

    public Projectile(Transform transform) {
        this.transform = transform;
    }

    public JSONObject toJson() {
        JSONObject JsonMessage = new JSONObject();

        JsonMessage.put("transform", this.transform.toJson().toJSONString());

        return JsonMessage;
    }

    public static Projectile fromJson(String Jsonstring) {
        Object jsonvalue = JSONValue.parse(Jsonstring);
        JSONObject object = (JSONObject) jsonvalue;

        String stransform = object.get("transform").toString();

        Transform transform = Transform.fromJson(stransform);

        Projectile projectile = new Projectile(transform);
        return projectile;
    }
}
