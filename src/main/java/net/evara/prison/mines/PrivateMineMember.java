package net.evara.prison.mines;

import lombok.Getter;
import lombok.Setter;
import net.evara.prison.customitem.items.RobotItem;
import net.evara.prison.mines.robot.MineRobot;
import net.evara.prison.utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Getter
@Setter
public class PrivateMineMember implements Comparable<PrivateMineMember> {

    private static final int MAX_ROBOTS = 5;

    private final PrivateMine mine;
    private final UUID uuid;
    private PrivateMineRank rank;
    private long blocksMined = 0;
    private double experienceEarnedForMine = 0.0D;
    private Map<UUID, MineRobot> robots;

    public PrivateMineMember(PrivateMine mine, UUID uuid, boolean founder) {
        this.mine = mine;
        this.uuid = uuid;
        this.rank = founder ? PrivateMineRank.FOUNDER : PrivateMineRank.MEMBER;
        this.robots = new HashMap<>();
    }

    public void addRobot(MineRobot robot){
        if(this.robots.size() >= MAX_ROBOTS){
            return;
        }
        this.robots.put(robot.getId(), robot);
    }

    public void removeRobot(MineRobot robot){
        RobotItem robotItem = new RobotItem(robot.getType());
        getPlayer().getInventory().addItem(robotItem.build());
        this.robots.remove(robot.getId());
    }

    public void promote() {
        this.rank = PrivateMineRank.promote(rank);
    }

    public void addBlocksMined(long amount) {

        double baseExperience = 1.5D;
        PrivateMineInfo mineInfo = mine.getInfo();

        double expEarned = amount * baseExperience;

        blocksMined += amount;
        mineInfo.addBlocksMined(amount);
        mineInfo.addExperience(expEarned);
        addExperienceEarnedForMine(expEarned);

        mineInfo.showProgress(getPlayer());

    }

    public void addExperienceEarnedForMine(double amount) {
        experienceEarnedForMine += amount;
    }

    public void removeBlocksMined(long amount) {
        blocksMined -= amount;
    }

    // We will compare the blocks mined to determine the top 10 miners
    @Override
    public int compareTo(@NotNull PrivateMineMember o) {
        return Long.compare(blocksMined, o.blocksMined);
    }

    public void msg(String message) {
        Player player = getPlayer();
        if(player == null) {
            return;
        }

        player.sendMessage(Text.format("Private Mine » " + message));
    }

    public boolean is(PrivateMineRank rank) {
        return this.rank == rank;
    }

    public Player getPlayer(){
        return Bukkit.getPlayer(uuid);
    }

    public boolean isOnline() {
        return getPlayer() != null;
    }


}
