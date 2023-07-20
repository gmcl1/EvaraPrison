package net.evara.prison;

import lombok.Getter;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import net.evara.prison.mines.MineCommand;
import net.evara.prison.mines.MineManager;
import net.evara.prison.player.PlayerManager;
import net.evara.prison.player.enchants.EnchantCommand;

@Getter
public final class PrisonCore extends ExtendedJavaPlugin {

    private static PrisonCore instance;
    private MineManager manager;
    private PlayerManager playerManager;

    @Override
    public void enable() {
        instance = this;
        this.playerManager = new PlayerManager(this);
        this.manager = new MineManager(this);
        new MineCommand(this).register();
        new EnchantCommand(this).register();
    }

    @Override
    public void disable() {
    }

    public static PrisonCore getInstance() {
        return instance;
    }
}
