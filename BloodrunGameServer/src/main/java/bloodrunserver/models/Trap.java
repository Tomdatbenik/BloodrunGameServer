package bloodrunserver.models;

import bloodrunserver.Application;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Trap {
    private int id;
    private Transform transform;
    private Scale scale;
    boolean activated;
    private TrapType type;

    private int activationRate;

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
        JSONObject jsonMessage = new JSONObject();

        jsonMessage.put("id", this.id);
        jsonMessage.put("transform", this.transform.toJson());
        jsonMessage.put("scale", this.scale.toJson());
        jsonMessage.put("activated", this.activated);
        jsonMessage.put("type", this.type.getValue());


        return jsonMessage;
    }

    public static Trap fromJson(String jsonstring) {
        Object jsonvalue = JSONValue.parse(jsonstring);
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

        return new Trap(id,transform,scale,activated,type);
    }

    public void activateTrap()
    {
        switch (this.type)
        {
            case SPIKE_TRAP:
                activateSpikeTrap(TrapType.SPIKE_TRAP);
                break;
            case OFFSET_SPIKE_TRAP:
                activateSpikeTrap(TrapType.OFFSET_SPIKE_TRAP);
                break;
            case DARTER:
                int rate = Integer.parseInt(Application.getProperties().getProperty("trap.Darter.ShootRate"));

                if(activationRate == rate)
                {
                    activated = true;
                }

                if(activated && activationRate > rate)
                {
                    activationRate = 0;
                    activated = false;
                }

                activationRate++;
                break;
            case ROTATE_TRAP:
                float speed = Float.parseFloat(Application.getProperties().getProperty("trap.RotatTrap.RotateSpeed"));

                Rotation rotation = this.transform.getRotation();

                Float y = Float.parseFloat(rotation.y);

                y += speed;

                if(y >= 360)
                {
                    y = (float)0;
                }

                rotation.y = y.toString();

                this.transform.setRotation(rotation);
                break;
            case REVERSE_ROTATE_TRAP:
                float reverseSpeed = Float.parseFloat(Application.getProperties().getProperty("trap.RotatTrap.RotateSpeed"));

                Rotation reverseRotation = this.transform.getRotation();

                Float x = Float.parseFloat(reverseRotation.y);

                x -= reverseSpeed;

                if(x == 0)
                {
                    x = (float)360;
                }

                reverseRotation.x = x.toString();

                this.transform.setRotation(reverseRotation);
                break;
        }

    }


    private void activateSpikeTrap(TrapType trap)
    {
        int time = Integer.parseInt(Application.getProperties().getProperty("trap.SpikeTrap.ActivateTime"));

        if(activated && trap == TrapType.SPIKE_TRAP || trap == TrapType.OFFSET_SPIKE_TRAP && !activated)
        {
            if(activationRate > time)
            {
                activationRate = 0;
                if(trap == TrapType.SPIKE_TRAP)
                {
                    activated = false;
                }
                else
                {
                    activated = true;
                }

            }
        }
        else
        {
            if(activationRate > time)
            {
                activationRate = 0;
                if(trap == TrapType.SPIKE_TRAP)
                {
                    activated = true;
                }
                else
                {
                    activated = false;
                }
            }
        }

        activationRate++;
    }
}
