package lol.praenyth.plugins.protectthepresident;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import lol.praenyth.plugins.protectthepresident.commands.Commands;
import lol.praenyth.plugins.protectthepresident.runnables.GameLoop;
import lol.praenyth.plugins.protectthepresident.runnables.Timer;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public final class ProtectThePresident extends JavaPlugin {

    public static JavaPlugin instance;

    public static BukkitRunnable GAME;
    public static BukkitRunnable CLOCK;

    public static GamePeriods CURRENT_POINT = GamePeriods.LOBBY;

    public static List<String> presidents;
    public static List<String> bodyguards;
    public static List<String> hunters;

    @Override
    public void onLoad() {
        instance = this;

        getLogger().info("Enabling CommandAPI...");
        CommandAPI.onLoad(new CommandAPIBukkitConfig(instance).verboseOutput(false).silentLogs(true));
    }

    @Override
    public void onEnable() {
        CommandAPI.onEnable();

        getLogger().info("Initializing roles!");
        presidents = new ArrayList<>();
        bodyguards = new ArrayList<>();
        hunters = new ArrayList<>();
        getLogger().info("Roles initialized!");

        getLogger().info("Registering commands!");
        Commands.registerCommands();
        getLogger().info("Commands registered!");

        CLOCK = new Timer();
        GAME = new GameLoop();
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
    }
}
