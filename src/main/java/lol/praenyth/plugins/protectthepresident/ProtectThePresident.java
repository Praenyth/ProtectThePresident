package lol.praenyth.plugins.protectthepresident;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import lol.praenyth.plugins.protectthepresident.commands.Commands;
import lol.praenyth.plugins.protectthepresident.enums.GamePeriods;
import lol.praenyth.plugins.protectthepresident.listeners.Connection;
import lol.praenyth.plugins.protectthepresident.listeners.Damage;
import lol.praenyth.plugins.protectthepresident.listeners.Interaction;
import lol.praenyth.plugins.protectthepresident.runnables.GameLoop;
import lol.praenyth.plugins.protectthepresident.runnables.Timer;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public final class ProtectThePresident extends JavaPlugin {

    // Plugin Instance
    public static JavaPlugin instance;

    // Game Mechanic Variables
    public static BukkitRunnable GAME;
    public static BukkitRunnable CLOCK;

    public static int finalTime = 0;

    public static GamePeriods CURRENT_POINT = GamePeriods.LOBBY;

    @Override
    public void onLoad() {
        instance = this;

        getLogger().info("Enabling CommandAPI...");
        CommandAPI.onLoad(new CommandAPIBukkitConfig(instance).verboseOutput(false).silentLogs(true));
    }

    @Override
    public void onEnable() {
        CommandAPI.onEnable();

        getLogger().info("Registering commands!");
        Commands.registerCommands();
        getLogger().info("Commands registered!");

        CLOCK = new Timer();
        GAME = new GameLoop();

        getLogger().info("Initializing listeners!");
        getServer().getPluginManager().registerEvents(new Connection(), instance);
        getServer().getPluginManager().registerEvents(new Damage(), instance);
        getServer().getPluginManager().registerEvents(new Interaction(), instance);
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
    }
}
