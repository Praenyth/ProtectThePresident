package lol.praenyth.plugins.protectthepresident.api;

import lol.praenyth.plugins.protectthepresident.enums.Roles;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Set;

public class Teams {

    public static Scoreboard SCOREBOARD;

    /**
     * Sets up the scoreboard for the name display
     * @param player The player to set up the scoreboard for
     */
    public static void setupCustomNameDisplay(Player player) {

        if (SCOREBOARD == null) {
            SCOREBOARD = Bukkit.getScoreboardManager().getNewScoreboard();

            for (Roles role : Roles.values()) {
                Team team = SCOREBOARD.registerNewTeam(role.name());
                team.color(NamedTextColor.nearestTo(TextColor.color(role.getColor())));
            }
        }
        player.setScoreboard(SCOREBOARD);

    }

    /**
     * Gets the team from the player
     * @param scoreHolder The player to get the scoreboard from
     * @param player The player to get the team of
     * @return The team of the player
     */
    public static Team getTeamFromPlayer(Player scoreHolder, Player player) {
        return scoreHolder.getScoreboard().getEntryTeam(player.getName());
    }

    /**
     * Gets the team from the player
     * @param player The player to get the team of
     * @return The team of the player
     */
    public static Team getTeamFromPlayer(Player player) {
        return SCOREBOARD.getEntryTeam(player.getName());
    }

    /**
     * Gets the team from the name
     * @param scoreHolder The player to get the scoreboard from
     * @param name The name of the team
     * @return The team of the player
     */
    public static Team getTeamFromName(Player scoreHolder, String name) {
        return scoreHolder.getScoreboard().getTeam(name);
    }

    /**
     * Gets the team from the name
     * @param role The team
     * @return The team of the player
     */
    public static Team getTeamFromName(Roles role) {
        return SCOREBOARD.getTeam(role.name());
    }

    /**
     * Gets every player in a team
     * @param role The team
     * @return The team of the player
     */
    public static Set<String> getAllPlayersInTeam(Roles role) {
        return getTeamFromName(role).getEntries();
    }

    /**
     * Gets the player's role
     * @param player The player to get the role of
     * @return The player's role
     */
    public static Roles getRole(Player player) {
        for (Roles role : Roles.values()) {
            if (getTeamFromPlayer(player, player).getName().equalsIgnoreCase(role.name())) {
                return role;
            }
        }
        return null;
    }

    /**
     * Sets the player's role
     * @param player The player to set the role for
     * @param role The role to set the player to
     */
    public static void setRole(Player player, Roles role) {

        for (Player p : Bukkit.getOnlinePlayers()) {
            getTeamFromName(p, role.name()).addEntry(player.getName());
        }

    }

    /**
     * Removes the player's role
     * @param player The player to remove the role from
     */
    public static void removeRole(Player player) {

        for (Player p : Bukkit.getOnlinePlayers()) {
            getTeamFromPlayer(p, player).removeEntry(player.getName());
        }

    }

}
