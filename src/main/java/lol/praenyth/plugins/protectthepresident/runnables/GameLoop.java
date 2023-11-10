package lol.praenyth.plugins.protectthepresident.runnables;

import lol.praenyth.plugins.protectthepresident.ProtectThePresident;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.Audiences;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameLoop extends BukkitRunnable {

    static boolean started = false;
    static BossBar bossBar = BossBar.bossBar(
            Component.text(),
            1f,
            BossBar.Color.WHITE,
            BossBar.Overlay.PROGRESS
    );

    public void start() {
        started = true;

        this.runTaskTimer(ProtectThePresident.instance, 0, 20);
    }

    @Override
    public void run() {



    }
}
