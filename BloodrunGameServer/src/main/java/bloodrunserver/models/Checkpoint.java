package bloodrunserver.models;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Checkpoint {

    private Transform transform;
    private Scale scale;

    public Checkpoint() {
        transform = null;
        scale = null;
    }

    public Transform getTransform() {
        return transform;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public Scale getScale() {
        return scale;
    }

    public void setScale(Scale scale) {
        this.scale = scale;
    }

    public Checkpoint(Transform transform, Scale scale) {
        this.transform = transform;
        this.scale = scale;
    }

    public JSONObject toJson() {
        JSONObject jsonMessage = new JSONObject();

        jsonMessage.put("transform", this.transform.toJson());
        jsonMessage.put("scale", this.scale.toJson());

        return jsonMessage;
    }

    public static Checkpoint fromJson(String jsonstring) {
        Object jsonvalue = JSONValue.parse(jsonstring);
        JSONObject object = (JSONObject) jsonvalue;

        String stransform = object.get("transform").toString();
        String sscale = object.get("scale").toString();

        Transform transform = Transform.fromJson(stransform);
        Scale scale = Scale.fromJson(sscale);

        return new Checkpoint(transform,scale);
    }
}
