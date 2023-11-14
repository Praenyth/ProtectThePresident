package lol.praenyth.plugins.protectthepresident.listeners;

import lol.praenyth.plugins.protectthepresident.ProtectThePresident;
import lol.praenyth.plugins.protectthepresident.api.Teams;
import lol.praenyth.plugins.protectthepresident.enums.Roles;
import lol.praenyth.plugins.protectthepresident.runnables.GameLoop;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Connection implements Listener {

    @EventHandler
    public void setInitialTeam(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        Teams.setupCustomNameDisplay(player);

        Teams.setRole(player, Roles.SPECTATORS);

    }

    @EventHandler
    public void removePlayerFromTeam(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        Teams.removeRole(player);

        Teams.removeRole(player);

        if (((GameLoop) ProtectThePresident.GAME).isRunning()) {
            return;
        }

        if (Teams.getAllPlayersInTeam(Roles.PRESIDENTS).isEmpty()) {

            // ending the game so that hunters win.
            ProtectThePresident.CLOCK.cancel();

        }
    }

}
