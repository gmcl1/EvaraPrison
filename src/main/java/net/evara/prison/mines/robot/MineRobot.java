package net.evara.prison.mines.robot;

import lombok.Getter;
import lombok.Setter;
import net.evara.prison.mines.PrivateMine;

import java.util.UUID;

@Getter
@Setter
public class MineRobot {

    private final PrivateMine mine;
    private final UUID id;
    private final RobotType type;
    private final UUID attachedBy;
    private final long attachedAt;
    private long beaconsGenerated;

    public MineRobot(PrivateMine mine, RobotType type, UUID attachedBy) {
        this.mine = mine;
        this.id = UUID.randomUUID();
        this.type = type;
        this.attachedBy = attachedBy;
        this.attachedAt = System.currentTimeMillis();
    }

    public void generateBeacons(){
        this.beaconsGenerated += this.type.getProductionAmount();
    }




}
