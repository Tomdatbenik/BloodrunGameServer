package bloodrunserver.models;

public class Spawnpoint {
    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Spawnpoint(Location location) {
        this.location = location;
    }
}
