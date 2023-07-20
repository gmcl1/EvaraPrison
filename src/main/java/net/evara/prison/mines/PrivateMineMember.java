package net.evara.prison.mines;

import lombok.Getter;
import lombok.Setter;
import net.evara.prison.utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Getter
@Setter
public class PrivateMineMember implements Comparable<PrivateMineMember> {

    private final PrivateMine mine;
    private final UUID uuid;
    private PrivateMineRank rank;
    private long blocksMined = 0;
    private double experienceEarnedForMine = 0.0D;

    public PrivateMineMember(PrivateMine mine, UUID uuid, boolean founder) {
        this.mine = mine;
        this.uuid = uuid;
        this.rank = founder ? PrivateMineRank.FOUNDER : PrivateMineRank.MEMBER;
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
