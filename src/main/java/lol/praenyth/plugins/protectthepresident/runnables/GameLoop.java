package lol.praenyth.plugins.protectthepresident.runnables;

import lol.praenyth.plugins.protectthepresident.ProtectThePresident;
import org.bukkit.scheduler.BukkitRunnable;

public class GameLoop extends BukkitRunnable {

    public void start() {
        this.runTaskTimer(ProtectThePresident.instance, 0, 20);
    }

    @Override
    public void run() {

    }
}
