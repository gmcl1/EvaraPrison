package net.evara.prison.mines;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
@Setter
public class PrivateMine {

    private final UUID mineOwner;
    private final PrivateMineMembers members;
    private final PrivateMineArea area;
    private final PrivateMineInfo info;

    public PrivateMine(World mineWorld, UUID mineOwner, int lastMinePosition) {
        this.mineOwner = mineOwner;
        this.members = new PrivateMineMembers(this);
        this.area = new PrivateMineArea(this, mineWorld, lastMinePosition);
        this.info = new PrivateMineInfo(this);
    }

    public void msg(String msg){
        this.members.messageAll(msg);
    }

    public void enter(Player player) {
        area.enterMine(player);
    }

    public boolean isOwnerPlayer() {
        return Bukkit.getOfflinePlayer(this.mineOwner).hasPlayedBefore();
    }

    public String getOwnerName(){
        if(!isOwnerPlayer()) return "Test Player";
        return Bukkit.getOfflinePlayer(this.mineOwner).getName();
    }


}
