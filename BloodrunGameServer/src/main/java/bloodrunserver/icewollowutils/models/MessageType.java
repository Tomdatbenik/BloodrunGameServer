package bloodrunserver.icewollowutils.models;


import bloodrunserver.Application;

//A enum that is complicated. Good job Java!
public enum MessageType {
    PING(Integer.parseInt(Application.getProperties().getProperty("message.Type.Ping"))),
    CONNECT(Integer.parseInt(Application.getProperties().getProperty("message.Type.Connect"))),
    DISCONNECT(Integer.parseInt(Application.getProperties().getProperty("message.Type.Disconnect"))),
    MOVE(Integer.parseInt(Application.getProperties().getProperty("message.Type.Move"))),
    UDP_REQUEST(Integer.parseInt(Application.getProperties().getProperty("message.Type.UDPRequest"))),
    GAME(Integer.parseInt(Application.getProperties().getProperty("message.Type.Game"))),
    CREATE_LOBBY(Integer.parseInt(Application.getProperties().getProperty("message.Type.CreateLobby"))),
    CHAT(Integer.parseInt(Application.getProperties().getProperty("message.Type.Chat"))),
    PUSHING(Integer.parseInt(Application.getProperties().getProperty("message.Type.Pushing"))),
    FINISH(Integer.parseInt(Application.getProperties().getProperty("message.Type.Finish")));

    private final int value;

    MessageType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MessageType fromInteger(int x) {

        if (x == Integer.parseInt(Application.getProperties().getProperty("message.Type.Ping"))) {
            return PING;
        } else if (x == Integer.parseInt(Application.getProperties().getProperty("message.Type.Connect"))) {
            return CONNECT;
        } else if (x == Integer.parseInt(Application.getProperties().getProperty("message.Type.Disconnect"))) {
            return DISCONNECT;
        } else if (x == Integer.parseInt(Application.getProperties().getProperty("message.Type.Move"))) {
            return MOVE;
        } else if (x == Integer.parseInt(Application.getProperties().getProperty("message.Type.UDPRequest"))) {
            return UDP_REQUEST;
        } else if (x == Integer.parseInt(Application.getProperties().getProperty("message.Type.Game"))) {
            return GAME;
        } else if (x == Integer.parseInt(Application.getProperties().getProperty("message.Type.CreateLobby"))) {
            return CREATE_LOBBY;
        } else if (x == Integer.parseInt(Application.getProperties().getProperty("message.Type.Chat"))) {
            return CHAT;
        } else if (x == Integer.parseInt(Application.getProperties().getProperty("message.Type.Pushing"))) {
            return PUSHING;
        } else if (x == Integer.parseInt(Application.getProperties().getProperty("message.Type.Finish"))) {
            return FINISH;
        }


        return null;
    }
}
