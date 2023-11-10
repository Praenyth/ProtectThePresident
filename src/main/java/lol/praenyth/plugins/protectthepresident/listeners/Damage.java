package lol.praenyth.plugins.protectthepresident.listeners;

import lol.praenyth.plugins.protectthepresident.ProtectThePresident;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Damage implements Listener {

    @EventHandler
    public void presidentPush(EntityDamageByEntityEvent event) {

        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {

            Player attacker = ((Player) event.getDamager());
            Player victim = ((Player) event.getEntity());

            // TODO: Check if the attacker is the president and president pushing is enabled

        }

    }

    @EventHandler
    public void presidentDeath(PlayerDeathEvent event) {

        if (ProtectThePresident.presidents.contains(event.getPlayer().getName())) {

            // TODO: check for any other presidents alive and end the game if all presidents have been killed

        }

    }

}
