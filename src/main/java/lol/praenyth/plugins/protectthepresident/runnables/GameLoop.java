package lol.praenyth.plugins.protectthepresident.runnables;

import lol.praenyth.plugins.protectthepresident.ProtectThePresident;
import lol.praenyth.plugins.protectthepresident.api.Teams;
import lol.praenyth.plugins.protectthepresident.commands.Commands;
import lol.praenyth.plugins.protectthepresident.enums.GamePeriods;
import lol.praenyth.plugins.protectthepresident.enums.Roles;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.Audiences;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import static lol.praenyth.plugins.protectthepresident.commands.Commands.BAD;
import static lol.praenyth.plugins.protectthepresident.commands.Commands.GREAT;

public class GameLoop extends BukkitRunnable {

    public void start(int initalTime) {
        ProtectThePresident.CURRENT_POINT = GamePeriods.INGAME;

        for (Player pl : Bukkit.getOnlinePlayers()) {
            pl.showTitle(Title.title(
                    Component.text(
                            "The game has started!"
                    ).color(TextColor.fromHexString(GREAT)),
                    Component.text("")
            ));

            if (Teams.getAllPlayersInTeam(Roles.PRESIDENTS).contains(pl.getName())) {

                pl.addPotionEffect(new PotionEffect(
                        PotionEffectType.GLOWING,
                        PotionEffect.INFINITE_DURATION,
                        0,
                        true,
                        false,
                        true
                ));

            }

            if (Teams.getAllPlayersInTeam(Roles.HUNTERS).contains(pl.getName())) {

                String pres = Teams.getAllPlayersInTeam(Roles.PRESIDENTS).iterator().next();

                ItemStack compass = new ItemStack(Material.COMPASS);
                CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();

                pl.setCompassTarget(Bukkit.getPlayer(pres).getLocation());
                compassMeta.setLodestoneTracked(false);
                compassMeta.setDisplayName(pres);

                compass.setItemMeta(compassMeta);

                pl.getInventory().addItem(
                        compass
                );

            }
        }

        ((Timer) ProtectThePresident.CLOCK).start(initalTime);

        this.runTaskTimer(ProtectThePresident.instance, 0, 20);
    }

    @Override
    public void run() {

    }

    @Override
    public synchronized void cancel() throws IllegalStateException {

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!Teams.getAllPlayersInTeam(Roles.PRESIDENTS).isEmpty()) {
                player.showTitle(Title.title(
                        Component.text(
                                "Presidents win!"
                        ).color(TextColor.fromHexString(GREAT)),
                        Component.text("")
                ));
            }

            if (Teams.getAllPlayersInTeam(Roles.PRESIDENTS).isEmpty()) {
                player.showTitle(Title.title(
                        Component.text(
                                "Hunters win!"
                        ).color(TextColor.fromHexString(BAD)),
                        Component.text("")
                ));
            }

            player.playSound(Sound.sound().type(Key.key("minecraft","entity.generic.explode")).build());

            if (player.hasPermission("protectthepresident.gamemaster")) {

                player.sendMessage(
                        Component.text("Please stop the server and delete the world to start another game.")
                                .style(Style.style().decorate(TextDecoration.ITALIC).color(TextColor.color(0xAAAAAA)).build())
                );

            }

        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            Teams.setRole(player, Roles.SPECTATORS);
        }

        ProtectThePresident.CURRENT_POINT = GamePeriods.LOBBY;

        super.cancel();
    }

    public boolean isRunning() {

        return ProtectThePresident.CURRENT_POINT == GamePeriods.GRACE || ProtectThePresident.CURRENT_POINT == GamePeriods.INGAME;

    }
}
