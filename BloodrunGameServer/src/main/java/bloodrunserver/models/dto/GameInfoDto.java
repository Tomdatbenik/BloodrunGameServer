package bloodrunserver.models.dto;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GameInfoDto {

    private String ip;

    public String getIp() {
        return ip;
    }

    public GameInfoDto() {
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.ip = "25.31.117.245";
    }
}
