package lol.praenyth.plugins.protectthepresident.runnables;

import lol.praenyth.plugins.protectthepresident.enums.GamePeriods;
import lol.praenyth.plugins.protectthepresident.ProtectThePresident;
import lol.praenyth.plugins.protectthepresident.api.Clock;
import lol.praenyth.plugins.protectthepresident.commands.Commands;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static lol.praenyth.plugins.protectthepresident.commands.Commands.BAD;

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

            if (countDown) {
                currentTime--;
                if (currentTime <= 0) {
                    this.cancel();
                }
            }

            if (!countDown) {
                currentTime++;
                if (currentTime >= 0) {
                    this.cancel();
                }
            }

            ProtectThePresident.instance.getLogger().info(String.valueOf(currentTime));

            bossBar.name(
                    Clock.secondsToClock(currentTime).color(TextColor.fromHexString(Commands.SUBPAR)))
                    .progress((float) currentTime / initialTime
            );
        }
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        if (!countDown) {
            ProtectThePresident.finalTime = currentTime;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.hideBossBar(bossBar);
        }

        // When the clock ends, the game ends.
        ProtectThePresident.GAME.cancel();

        super.cancel();
    }
}
