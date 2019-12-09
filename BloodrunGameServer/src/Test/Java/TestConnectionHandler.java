import bloodrunserver.Application;
import org.junit.BeforeClass;

public class TestConnectionHandler {

    @BeforeClass
    private void Setup()
    {
        Application.setUpProperties();
        Application.startServer();
    }

    
}
