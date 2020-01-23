package bloodrunserver.models;

import bloodrunserver.Application;

public enum TrapType {
    SPIKE_TRAP(Integer.parseInt(Application.getProperties().getProperty("trap.type.SpikeTrap"))),
    DARTER(Integer.parseInt(Application.getProperties().getProperty("trap.type.Darter"))),
    ALWAYS_ACTIVE_TRAP(Integer.parseInt(Application.getProperties().getProperty("trap.type.AlwaysActiveTrap"))),
    ROTATING_DARTER(Integer.parseInt(Application.getProperties().getProperty("trap.type.RotatingDarter"))),
    ROTATE_TRAP(Integer.parseInt(Application.getProperties().getProperty("trap.type.RotateTrap"))),
    REVERSE_ROTATE_TRAP(Integer.parseInt(Application.getProperties().getProperty("trap.type.ReverseRotateTrap"))),
    OFFSET_SPIKE_TRAP(Integer.parseInt(Application.getProperties().getProperty("trap.type.OffsetSpikeTrap")));

    private final int value;
    TrapType(int value) { this.value = value; }

    public int getValue() {
        return value;
    }

    public static TrapType fromInteger(int x) {

        if(x == Integer.parseInt(Application.getProperties().getProperty("trap.type.SpikeTrap")))
        {
            return SPIKE_TRAP;
        }
        else if(x == Integer.parseInt(Application.getProperties().getProperty("trap.type.Darter")))
        {
            return DARTER;
        }
        else if(x == Integer.parseInt(Application.getProperties().getProperty("trap.type.AlwaysActiveTrap")))
        {
            return ALWAYS_ACTIVE_TRAP;
        }
        else if(x == Integer.parseInt(Application.getProperties().getProperty("trap.type.RotatingDarter")))
        {
            return ROTATING_DARTER;
        }
        else if(x == Integer.parseInt(Application.getProperties().getProperty("trap.type.RotateTrap")))
        {
            return ROTATE_TRAP;
        }
        else if(x == Integer.parseInt(Application.getProperties().getProperty("trap.type.ReverseRotateTrap")))
        {
            return REVERSE_ROTATE_TRAP;
        }
        else if(x == Integer.parseInt(Application.getProperties().getProperty("trap.type.OffsetSpikeTrap")))
        {
            return OFFSET_SPIKE_TRAP;
        }

        return null;
    }
}
