package bloodrunserver.models;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Trap {
    private int id;
    private Transform transform;
    private Scale scale;
    boolean activated;
    private TrapType type;

    public int getId() {
        return id;
    }

    public Trap(int id, Transform transform, Scale scale, boolean activated, TrapType type) {
        this.id = id;
        this.transform = transform;
        this.scale = scale;
        this.activated = activated;
        this.type = type;
    }

    public JSONObject toJson() {
        JSONObject JsonMessage = new JSONObject();

        JsonMessage.put("id", this.id);
        JsonMessage.put("transform", this.transform.toJson());
        JsonMessage.put("scale", this.scale.toJson());
        JsonMessage.put("activated", this.activated);
        JsonMessage.put("type", this.type.getValue());


        return JsonMessage;
    }

    public static Trap fromJson(String Jsonstring) {
        Object jsonvalue = JSONValue.parse(Jsonstring);
        JSONObject object = (JSONObject) jsonvalue;

        String sid = object.get("id").toString();
        String stransform = object.get("transform").toString();
        String sscale = object.get("scale").toString();
        String stype = object.get("type").toString();

        int id = Integer.parseInt(sid);
        Transform transform = Transform.fromJson(stransform);
        Scale scale = Scale.fromJson(sscale);
        Boolean activated = Boolean.getBoolean(object.get("activated").toString());
        TrapType type = TrapType.fromInteger(Integer.parseInt(stype));
        Trap trap = new Trap(id,transform,scale,activated,type);

        return trap;
    }

}
