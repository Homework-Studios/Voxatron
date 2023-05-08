package actor;

import util.UUIDUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class Actor {

    public String uuid;

    public Actor() {
        uuid = UUIDUtil.generateUUID();
    }

    public abstract void update();
}
