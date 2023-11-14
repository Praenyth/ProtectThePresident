package lol.praenyth.plugins.protectthepresident.listeners;

import lol.praenyth.plugins.protectthepresident.ProtectThePresident;
import lol.praenyth.plugins.protectthepresident.api.Teams;
import lol.praenyth.plugins.protectthepresident.enums.Roles;
import lol.praenyth.plugins.protectthepresident.runnables.GameLoop;
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

        if (!((GameLoop) ProtectThePresident.GAME).isRunning()) {
            return;
        }

        if (Teams.getAllPlayersInTeam(Roles.PRESIDENTS).contains(event.getPlayer().getName())) {

            Player president = event.getPlayer();
            Teams.setRole(president, Roles.SPECTATORS);

            if (Teams.getAllPlayersInTeam(Roles.PRESIDENTS).isEmpty()) {

                // ending the game so that hunters win.
                ProtectThePresident.CLOCK.cancel();

            }

        }

    }

}
