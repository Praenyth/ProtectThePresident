package lol.praenyth.plugins.protectthepresident.runnables;

import lol.praenyth.plugins.protectthepresident.GamePeriods;
import lol.praenyth.plugins.protectthepresident.ProtectThePresident;
import lol.praenyth.plugins.protectthepresident.api.Clock;
import lol.praenyth.plugins.protectthepresident.commands.Commands;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer extends BukkitRunnable {

    public boolean countDown = true;
    private int initialTime;
    public int currentTime;

    static BossBar bossBar = BossBar.bossBar(
            Component.text(""),
            1f,
            BossBar.Color.WHITE,
            BossBar.Overlay.PROGRESS
    );

    public void start(int currentTime) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.showBossBar(bossBar);
        }

        this.currentTime = currentTime;
        this.initialTime = currentTime;

        this.runTaskTimer(ProtectThePresident.instance, 0, 20);
    }

    @Override
    public void run() {
        if (!ProtectThePresident.CURRENT_POINT.equals(GamePeriods.LOBBY)) {

            if (currentTime >= 0) {

                for (Player player : Bukkit.getOnlinePlayers()) {

                    player.hideBossBar(bossBar);

                }
                this.cancel();

            }

            if (countDown) {
                currentTime--;
            }

            if (!countDown) {
                currentTime++;
            }

            bossBar.name(
                    Clock.secondsToClock(currentTime).color(TextColor.fromHexString(Commands.SUBPAR)))
                    .progress((float) currentTime / initialTime
            );
        }
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        super.cancel();
    }
}
