package net.evara.prison.mines;

import lombok.Getter;
import lombok.Setter;
import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Item;
import net.evara.prison.mines.ui.PrivateMineUI;
import net.evara.prison.player.EvaraPlayer;
import net.evara.prison.player.PlayerManager;
import net.evara.prison.utils.ProgressBar;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class PrivateMineInfo {


    private final PrivateMine mine;
    private final long created; // Time this gang was created.
    private Material mineListMaterial; // Material to display in the mine list.
    private String mineListName; // Name to display in the mine list.
    private long allTimeMembers; // Number of members this gang has had since it was created.
    private long allTimeBlocksMined; // Number of blocks mined by all members of this gang since it was created.
    private double allTimeExperienceGained;
    private long level;
    private double experience;
    private long beacons;

    public PrivateMineInfo(PrivateMine mine) {
        this.mine = mine;
        this.created = System.currentTimeMillis();
        this.mineListMaterial = Material.IRON_BARS;
        this.mineListName = "&5&l" + mine.getOwnerName() + "'s Mine";
        this.allTimeMembers = 0;
        this.allTimeBlocksMined = 0;
        this.allTimeExperienceGained = 0.0D;
        this.level = 1;
        this.experience = 0.0D;
    }

    public void showProgress(Player player) {
        String progress = ProgressBar.builder()
                .current(experience)
                .max(getRequiredExperience())
                .symbol('■')
                .emptySymbol('□')
                .length(20)
                .prefix("[")
                .suffix("]")
                .build().getBarWithPercent();

        player.sendMessage(progress);
    }

    public double getRequiredExperience() {
        return Math.pow(level, 1.1) * 1000;
    }

    public String getPercentage() {
        return String.format("%.2f", (experience / getRequiredExperience()) * 100.0D);
    }

    public void addExperience(double amount) {
        this.experience += amount;
        this.allTimeExperienceGained += amount;
        if (experience >= getRequiredExperience()) {
            int totalLevels = 0;
            while (experience >= getRequiredExperience()) {
                experience -= getRequiredExperience();
                level++;
                totalLevels++;
                mine.getArea().expand();
            }
            mine.msg("&a&lLEVEL UP! &7You are now level &a" + level + "&7!");
            mine.msg("&7You gained &a" + totalLevels + " &7levels!");
        }
    }

    public void addBlocksMined(long amount) {
        this.allTimeBlocksMined += amount;
    }

    public void addAllTimeMembers() {
        this.allTimeMembers++;
    }

    public Item toMenuItem(PlayerManager playerManager, MineManager manager, Player player) {

        EvaraPlayer evaraPlayer = playerManager.getPlayer(player.getUniqueId());

        List<String> lore = new ArrayList<>();
        lore.add("&dLevel: &5" + level);
        lore.add("&dExperience: &d" + Math.floor(experience) + " &f/ &5" + Math.floor(getRequiredExperience()) + "&f(&d" + getPercentage() + "%&f)");
        lore.add("");
        lore.add("&dAll Time Blocks Mined: &5" + allTimeBlocksMined);
        lore.add("&dAll Time Experience Gained: &5" + allTimeExperienceGained);
        lore.add("&dAll Time Members: &5" + allTimeMembers);
        lore.add("");
        lore.add("&dClick to teleport to teleport to mine.");


        return ItemStackBuilder.of(this.mineListMaterial)
                .name(this.mineListName)
                .lore(
                        lore
                )
                .enchant(Enchantment.DIG_SPEED)
                .hideAttributes()
                .build(() ->
                        new PrivateMineUI(player,  playerManager, this.mine, manager).open()
                );
    }


}
