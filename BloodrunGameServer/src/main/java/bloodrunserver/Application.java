package bloodrunserver;

import bloodrunserver.server.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SpringBootApplication
public class Application {

    private static final Properties properties = new Properties();
    private static Server server;

    public static Properties getProperties() {
        return properties;
    }

    public static Server getServer() {
        return server;
    }

    //The main that starts everything
    public static void main(String[] args) {
        setUpProperties();
        startServer();

        //TODO Create websocket instead of this fart
        CreateTestLobby createTestLobby = new CreateTestLobby();
        createTestLobby.testLobyy();
        SpringApplication.run(Application.class, args);
    }

    //Create instance of server.
    //TODO create optional startup shutdown and reload function to server.
    public static void startServer()
    {
        if(server == null)
        {
            server = new Server();
            server.startServer();
        }
    }

    //Loads in properties file
    public static void setUpProperties()
    {
        //Create new instance to get class type, this instance isn't used afterwards.
        InputStream input = null;

        input = Application.class.getClassLoader().getResourceAsStream("config.properties");

        try {
            properties.load(input);
        } catch (IOException e) {
            SoutLogger.log(e.getMessage());
        }
    }
}
