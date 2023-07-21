package net.evara.prison.mines.robot;

import me.lucko.helper.Events;
import me.lucko.helper.event.Subscription;
import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Gui;
import me.lucko.helper.utils.Players;
import net.evara.prison.PrisonCore;
import net.evara.prison.customitem.items.RobotItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class RobotCombineUI extends Gui {

    private ItemStack robotOne;
    private ItemStack robotTwo;

    public RobotCombineUI(Player player) {
        super(player, 3, "&dCombine Robots &8(" + player.getName() + ")");

        AtomicReference<List<Subscription>> subscription = new AtomicReference<>();

        List<Subscription> subscriptions = new ArrayList<>();

        subscriptions.add(Events.subscribe(InventoryClickEvent.class)
                .handler(e -> {
                    Player p = (Player) e.getWhoClicked();

                    if (e.getClickedInventory() == null) return;
                    boolean isPlayerInventory = e.getClickedInventory().equals(p.getInventory());

                    ItemStack clickedItem = e.getCurrentItem();
                    if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

                    if (isPlayerInventory) {

                        boolean firstRobotSet = robotOne != null;
                        boolean bothSet = robotOne != null && robotTwo != null;
                        if (bothSet) {
                            return;
                        }

                        if (!isRobot(clickedItem)) {
                            Players.msg(player, "&5&lROBOT &7• &7You can only combine &dRobots&7!");
                            return;
                        }

                        RobotType type = getType(clickedItem);

                        if(type.isTopTier()) {
                            Players.msg(player, "&5&lROBOT &7• &7You can't combine any higher than this!");
                            return;
                        }

                        ItemStack robotItem = clickedItem.clone();
                        robotItem.setAmount(1);

                        if (firstRobotSet) {
                            if (type != getType(robotOne)) {
                                Players.msg(player, "&5&lROBOT &7• &7You can't combine different robots!");
                                return;
                            }
                            robotTwo = robotItem;
                        } else {
                            robotOne = robotItem;
                        }

                        clickedItem.setAmount(clickedItem.getAmount() - 1);
                        p.updateInventory();
                        redraw();

                    }

                }));

        subscriptions.add(Events.subscribe(InventoryCloseEvent.class)
                .handler(e -> {
                    Player p = (Player) e.getPlayer();

                    if (!e.getInventory().equals(this.getHandle())) return;

                    subscriptions.forEach(sub -> {
                        sub.close();
                        sub.unregister();
                    });

                    subscriptions.clear();

                    if (robotOne == null && robotTwo == null) return;

                    if (robotOne != null) player.getInventory().addItem(robotOne);
                    if (robotTwo != null) player.getInventory().addItem(robotTwo);



                    Players.msg(player, "&5&lROBOT &7• &7You have cancelled the combination process.");

                }));

        subscription.set(subscriptions);

    }


    @Override
    public void redraw() {

        boolean firstRobotSet = robotOne != null;
        boolean secondRobotSet = robotTwo != null;

        if (firstRobotSet)
            setItem(11, ItemStackBuilder.of(robotOne).buildItem().build());
        else
            setItem(11, ItemStackBuilder.of(Material.RED_STAINED_GLASS_PANE).name("&cSelect A Robot").buildItem().build());

        if (secondRobotSet)
            setItem(15, ItemStackBuilder.of(robotTwo).buildItem().build());
        else
            setItem(15, ItemStackBuilder.of(Material.RED_STAINED_GLASS_PANE).name("&cSelect A Robot").buildItem().build());

        if (robotOne != null && robotTwo != null) {

            RobotType type = getType(robotOne);
            RobotType type2 = getType(robotTwo);
            RobotType nextTier = type.getNext();

            setItem(
                    13,
                    ItemStackBuilder.of(Material.NETHER_STAR)
                            .name("&dCombine Robots")
                            .lore(
                                    "&7Combine two robots of the same tier",
                                    "&7to create a higher tier robot.",
                                    "",
                                    "&5 Combining",
                                    "&d┃ " + type.getDisplayName() + " &f+ " + type2.getDisplayName(),
                                    "",
                                    "&5 Result",
                                    "&d┃ " + nextTier.getDisplayName(),
                                    ""
                            )
                            .build(() -> {
                                robotOne = null;
                                robotTwo = null;
                                RobotItem robotItem = new RobotItem(nextTier);
                                getPlayer().getInventory().addItem(robotItem.build());
                                Players.msg(getPlayer(), "&5&lROBOT &7• &7You have combined two robots to create a &d" + nextTier.getDisplayName() + "&7!");
                                redraw();
                            })
            );
        } else {
            setItem(13, ItemStackBuilder.of(Material.NETHER_STAR).name("&dWaiting For Robots...").buildItem().build());
        }

    }

    public RobotType getType(ItemStack itemStack) {
        return RobotType.valueOf(itemStack.getItemMeta().getPersistentDataContainer().get(
                new NamespacedKey(PrisonCore.getInstance(), "robot_tier"),
                PersistentDataType.STRING
        ));
    }

    public boolean isRobot(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR) return false;
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        return container.has(
                new NamespacedKey(PrisonCore.getInstance(), "robot_tier"),
                PersistentDataType.STRING
        );
    }

}
