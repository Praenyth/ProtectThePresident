package lol.praenyth.plugins.protectthepresident.listeners;

import lol.praenyth.plugins.protectthepresident.ProtectThePresident;
import lol.praenyth.plugins.protectthepresident.api.Teams;
import lol.praenyth.plugins.protectthepresident.enums.GamePeriods;
import lol.praenyth.plugins.protectthepresident.enums.Roles;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class Interaction implements Listener {

    @EventHandler
    public void preventGlowClear(PlayerItemConsumeEvent event) {

        if (event.getItem().getType().equals(Material.MILK_BUCKET)) {

            if (Teams.getAllPlayersInTeam(Roles.PRESIDENTS).contains(event.getPlayer().getName())) {

                event.getPlayer().addPotionEffect(new PotionEffect(
                        PotionEffectType.GLOWING,
                        PotionEffect.INFINITE_DURATION,
                        0,
                        true,
                        false,
                        true
                ));

            }

        }

    }

    @EventHandler
    public void trackRandomPlayer(PlayerInteractEvent event) {

        if (!ProtectThePresident.CURRENT_POINT.equals(GamePeriods.INGAME)) return;

        if (!event.hasItem()) return;

        if (!event.getAction().isRightClick()) return;

        if (!event.getItem().getType().equals(Material.COMPASS)) return;

        int item = -1;
        while (item < 0) {
            item = new Random().nextInt(Teams.getAllPlayersInTeam(Roles.PRESIDENTS).size());
        }

        String rndom = Teams.getAllPlayersInTeam(Roles.PRESIDENTS).stream().skip(item).findFirst().get();

        CompassMeta compassMeta = (CompassMeta) event.getItem().getItemMeta();
        event.getPlayer().setCompassTarget(
                Bukkit.getPlayer(
                        rndom
                ).getLocation()
        );

        compassMeta.setDisplayName(rndom);

        event.getItem().setItemMeta(compassMeta);

    }

}
