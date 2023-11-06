package lol.praenyth.plugins.protectthepresident;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import lol.praenyth.plugins.protectthepresident.commands.Commands;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class ProtectThePresident extends JavaPlugin {

    public static JavaPlugin instance;

    public static List<String> presidents;
    public static List<String> bodyguards;
    public static List<String> hunters;

    @Override
    public void onLoad() {
        instance = this;

        getLogger().info("Enabling CommandAPI...");
        CommandAPI.onLoad(new CommandAPIBukkitConfig(instance).verboseOutput(true));
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
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
    }
}
