package bloodrunserver.icewollowutils.models;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Message {

    String sender;
    String content;
    MessageType type;
    boolean Handled = false;

    //region Getters and Setters
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public boolean isHandled() {
        return Handled;
    }

    public void setHandled(boolean handled) {
        Handled = handled;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
    //endregion

    //Base constructor sets basic value.
    public Message() {
        content = "";
        type = MessageType.CONNECT;
    }

    //Sets sender content and messagetype.
    public Message(String sender, String content, MessageType type) {
        this.sender = sender;
        this.content = content;
        this.type = type;
    }

    //Debugging mode ENABLED!!!!
    @Override
    public String toString() {
        return "Sender: " + this.getSender() +", Content: " + this.content + ", Type: " + type.toString() + ".";
    }

    //Creates JsonObject from message.
    public JSONObject toJson() {
        JSONObject JsonMessage = new JSONObject();
        JsonMessage.put("sender", this.getSender());
        JsonMessage.put("type", this.getType().getValue());
        JsonMessage.put("content", this.getContent());

        return JsonMessage;
    }

    //Creates message from JsonString
    public static Message fromJson(String Jsonstring) {
        Object jsonvalue = JSONValue.parse(Jsonstring);
        JSONObject object = (JSONObject) jsonvalue;

        String sender = object.get("sender").toString();
        String content = object.get("content").toString();
        MessageType messageType = MessageType.fromInteger(Integer.parseInt(object.get("type").toString()));

        Message message = new Message(sender, content, messageType);
        return message;
    }
}
