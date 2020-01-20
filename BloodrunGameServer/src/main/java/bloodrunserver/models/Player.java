package bloodrunserver.models;

import bloodrunserver.server.Client;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Player {

    private String username;
    private Client client;
    private final Transform transform = new Transform();
    private boolean connected = false;
    private boolean pushing = false;

    private boolean running;
    private float vertical;

    public Player(String username) {
        this.username = username;
    }

    public Player(String username, Transform transform) {
        this.username = username;
        this.transform.setLocation(transform.getLocation());
        this.transform.setRotation(transform.getRotation());
    }

    public Player(String username,Transform transform, boolean pushing) {
        this.username = username;
        this.pushing = pushing;
        this.transform.setLocation(transform.getLocation());
        this.transform.setRotation(transform.getRotation());
    }

    public Player(String susername, Transform transform, Boolean pushing, Boolean running, float vertical) {
        this.username = susername;
        this.pushing = pushing;
        this.transform.setLocation(transform.getLocation());
        this.transform.setRotation(transform.getRotation());
        this.pushing = pushing;
        this.running = running;
        this.vertical = vertical;
    }

    public Transform getTransform() {
        synchronized (transform)
        {
            return transform;
        }
    }

    public void setTransform(Transform transform) {
        synchronized (this.transform)
        {
            this.transform.setLocation(transform.getLocation());
            this.transform.setRotation(transform.getRotation());
        }
    }

    public boolean isPushing() {
        return pushing;
    }

    public void setPushing(boolean pushing) {
        this.pushing = pushing;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public float getVertical() {
        return vertical;
    }

    public void setVertical(float vertical) {
        this.vertical = vertical;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public JSONObject toJson() {
        JSONObject jsonMessage = new JSONObject();
        jsonMessage.put("username", this.username);
        jsonMessage.put("transform", this.transform.toJson());
        jsonMessage.put("connected", this.connected);
        jsonMessage.put("pushing", this.pushing);
        jsonMessage.put("running", this.running);
        jsonMessage.put("vertical", this.vertical);

        return jsonMessage;
    }

    public static Player fromJson(String jsonstring) {
        Object jsonvalue = JSONValue.parse(jsonstring);
        JSONObject object = (JSONObject) jsonvalue;

        String susername = object.get("username").toString();
        String stransform = object.get("transform").toString();
        Boolean pushing = Boolean.parseBoolean(object.get("pushing").toString());
        Boolean running = Boolean.parseBoolean(object.get("running").toString());
        float vertical = Float.parseFloat(object.get("vertical").toString());

        Transform transform = Transform.fromJson(stransform);

        return new Player(susername,transform, pushing, running, vertical);
    }
}
