package bloodrunserver.models;

import bloodrunserver.server.Client;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Player {

    private String username;
    private Client client;
    private final Transform transform = new Transform();
    private boolean connected = false;

    public Player(String username) {
        this.username = username;
    }

    public Player(String username, Transform transform) {
        this.username = username;
        this.transform.setLocation(transform.getLocation());
        this.transform.setRotation(transform.getRotation());
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

        return jsonMessage;
    }

    public static Player fromJson(String jsonstring) {
        Object jsonvalue = JSONValue.parse(jsonstring);
        JSONObject object = (JSONObject) jsonvalue;

        String susername = object.get("username").toString();
        String stransform = object.get("transform").toString();

        Transform transform = Transform.fromJson(stransform);

        return new Player(susername,transform);
    }
}
