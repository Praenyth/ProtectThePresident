package lol.praenyth.plugins.protectthepresident.listeners;

import lol.praenyth.plugins.protectthepresident.api.Teams;
import lol.praenyth.plugins.protectthepresident.enums.Roles;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Connection implements Listener {

    @EventHandler
    public void setInitialTeam(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        Teams.setupCustomNameDisplay(player);

        Teams.setRole(player, Roles.SPECTATORS);

    }

    @EventHandler
    public void removePlayerFromTeam(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        Teams.removeRole(player);

        // TODO: Check for if the hunters win when the game loop stuff is finished

    }

}
