package lol.praenyth.plugins.protectthepresident.commands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.*;
import lol.praenyth.plugins.protectthepresident.ProtectThePresident;
import lol.praenyth.plugins.protectthepresident.runnables.GameLoop;
import lol.praenyth.plugins.protectthepresident.runnables.Timer;
import net.kyori.adventure.chat.ChatType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Commands {

    // default color codes
    public static String GREAT = "#66ff8f";
    public static String SUBPAR = "#ffe066";
    public static String BAD = "#ff6966";

    public static void registerCommands() {

        new CommandAPICommand("protectthepresident")
                // making sure no random player can just start the game
                .withPermission("protectthepresident.gamemaster")

                .withSubcommand(new CommandAPICommand("start")
                        .withArguments(new IntegerArgument("initalSeconds"))
                        .executes((sender, args) -> {
                            ((GameLoop) ProtectThePresident.GAME).start();
                            ((Timer) ProtectThePresident.CLOCK).start((Integer) args.get("initalSeconds"));

                            for (Player pl : Bukkit.getOnlinePlayers()) {
                                pl.showTitle(Title.title(
                                        Component.text(
                                                "The game has started!"
                                        ).color(TextColor.fromHexString(GREAT)),
                                        Component.text("")
                                ));

                                if (ProtectThePresident.presidents.contains(pl.getName())) {

                                    pl.addPotionEffect(new PotionEffect(
                                            PotionEffectType.GLOWING,
                                            PotionEffect.INFINITE_DURATION,
                                            0,
                                            true,
                                            false,
                                            true
                                    ));

                                }
                            }

                        })
                )

                // roles
                .withSubcommand(new CommandAPICommand("roles")
                        // List out players and their roles
                        .withSubcommand(new CommandAPICommand("list")
                                .executes((sender, args) -> {
                                    sender.sendMessage(Component.text("President(s):").color(TextColor.fromHexString(GREAT)));
                                    for (String name : ProtectThePresident.presidents) {
                                        sender.sendMessage(Component.text("- " + name));
                                    }
                                    sender.sendMessage(Component.text("Bodyguard(s):").color(TextColor.fromHexString(SUBPAR)));
                                    for (String name : ProtectThePresident.bodyguards) {
                                        sender.sendMessage(Component.text("- " + name));
                                    }
                                    sender.sendMessage(Component.text("Hunter(s):").color(TextColor.fromHexString(BAD)));
                                    for (String name : ProtectThePresident.hunters) {
                                        sender.sendMessage(Component.text("- " + name));
                                    }
                                })

                        )

                        // Presidents
                        .withSubcommand(new CommandAPICommand("presidents")
                                .withSubcommand(new CommandAPICommand("add")
                                        .withArguments(new PlayerArgument("presidentPlayer"))
                                        .executes((sender, args) -> {
                                            Player newPresident = (Player) args.get("presidentPlayer");

                                            if (ProtectThePresident.presidents.contains(newPresident.getName())) {
                                                sender.sendMessage(
                                                        Component.text(
                                                                newPresident.getName() + " is already a president!!"
                                                        ).color(TextColor.fromHexString(BAD))
                                                );
                                                return;
                                            }

                                            if (ProtectThePresident.bodyguards.contains(newPresident.getName())) {
                                                sender.sendMessage(
                                                        Component.text(
                                                                newPresident.getName() + " cannot be a president as they are a bodyguard!"
                                                        ).color(TextColor.fromHexString(BAD))
                                                );
                                                return;
                                            }

                                            if (ProtectThePresident.hunters.contains(newPresident.getName())) {
                                                sender.sendMessage(
                                                        Component.text(
                                                                newPresident.getName() + " cannot be a president as they are a hunter!"
                                                        ).color(TextColor.fromHexString(BAD))
                                                );
                                                return;
                                            }

                                            ProtectThePresident.presidents.add(newPresident.getName());
                                            sender.sendMessage(
                                                    Component.text(
                                                            newPresident.getName() + " has been added to the list of Presidents!"
                                                    ).color(TextColor.fromHexString(GREAT))
                                            );

                                        }
                                        )
                                )

                                .withSubcommand(new CommandAPICommand("remove")
                                        .withArguments(new PlayerArgument("impeachedPlayer"))
                                        .executes((sender, args) -> {
                                            Player oldPresident = (Player) args.get("impeachedPlayer");

                                            if (!ProtectThePresident.presidents.contains(oldPresident.getName())) {
                                                sender.sendMessage(
                                                        Component.text(
                                                                oldPresident.getName() + " cannot be removed as a president because they aren't one!"
                                                        ).color(TextColor.fromHexString(BAD))
                                                );
                                                return;
                                            }

                                            ProtectThePresident.presidents.remove(oldPresident.getName());
                                            sender.sendMessage(
                                                    Component.text(
                                                            oldPresident.getName() + " has been removed from the list of Presidents!"
                                                    ).color(TextColor.fromHexString(SUBPAR))
                                            );

                                        }
                                        )
                                )
                        )

                        // Bodyguards
                        .withSubcommand(new CommandAPICommand("bodyguards")
                                .withSubcommand(new CommandAPICommand("add")
                                        .withArguments(new PlayerArgument("bodyguardPlayer"))
                                        .executes((sender, args) -> {
                                            Player newBodyguard = (Player) args.get("bodyguardPlayer");

                                            if (ProtectThePresident.presidents.contains(newBodyguard.getName())) {
                                                sender.sendMessage(
                                                        Component.text(
                                                                newBodyguard.getName() + " cannot be a bodyguard as they are a president!"
                                                        ).color(TextColor.fromHexString(BAD))
                                                );
                                                return;
                                            }

                                            if (ProtectThePresident.bodyguards.contains(newBodyguard.getName())) {
                                                sender.sendMessage(
                                                        Component.text(
                                                                newBodyguard.getName() + " is already a bodyguard!"
                                                        ).color(TextColor.fromHexString(BAD))
                                                );
                                                return;
                                            }

                                            if (ProtectThePresident.hunters.contains(newBodyguard.getName())) {
                                                sender.sendMessage(
                                                        Component.text(
                                                                newBodyguard.getName() + " cannot be a bodyguard as they are a hunter!"
                                                        ).color(TextColor.fromHexString(BAD))
                                                );
                                                return;
                                            }

                                            ProtectThePresident.bodyguards.add(newBodyguard.getName());
                                            sender.sendMessage(
                                                    Component.text(
                                                            newBodyguard.getName() + " has been added to the list of Bodyguards!"
                                                    ).color(TextColor.fromHexString(GREAT))
                                            );
                                        })
                                )
                                .withSubcommand(new CommandAPICommand("remove")
                                        .withArguments(new PlayerArgument("firedPlayer"))
                                        .executes((sender, args) -> {
                                                    Player firedBodyguard = (Player) args.get("firedPlayer");

                                                    if (!ProtectThePresident.bodyguards.contains(firedBodyguard.getName())) {
                                                        sender.sendMessage(
                                                                Component.text(
                                                                        firedBodyguard.getName() + " cannot be removed as a bodyguard because they aren't one!"
                                                                ).color(TextColor.fromHexString(BAD))
                                                        );
                                                        return;
                                                    }

                                                    ProtectThePresident.bodyguards.remove(firedBodyguard.getName());
                                                    sender.sendMessage(
                                                            Component.text(
                                                                    firedBodyguard.getName() + " has been removed from the list of Bodyguards!"
                                                            ).color(TextColor.fromHexString(SUBPAR))
                                                    );
                                                }
                                        )
                                )
                        )

                        // Hunters
                        .withSubcommand(new CommandAPICommand("hunters")
                                .withSubcommand(new CommandAPICommand("add")
                                        .withArguments(new PlayerArgument("hunterPlayer"))
                                        .executes((sender, args) -> {

                                            Player newHunter = (Player) args.get("hunterPlayer");

                                            if (ProtectThePresident.presidents.contains(newHunter.getName())) {
                                                sender.sendMessage(
                                                        Component.text(
                                                                newHunter.getName() + " cannot be a hunter as they are a president!"
                                                        ).color(TextColor.fromHexString(BAD))
                                                );
                                                return;
                                            }

                                            if (ProtectThePresident.hunters.contains(newHunter.getName())) {
                                                sender.sendMessage(
                                                        Component.text(
                                                                newHunter.getName() + " is already a hunter!"
                                                        ).color(TextColor.fromHexString(BAD))
                                                );
                                                return;
                                            }

                                            if (ProtectThePresident.bodyguards.contains(newHunter.getName())) {
                                                sender.sendMessage(
                                                        Component.text(
                                                                newHunter.getName() + " cannot be a hunter as they are a bodyguard!"
                                                        ).color(TextColor.fromHexString(BAD))
                                                );
                                                return;
                                            }

                                            ProtectThePresident.hunters.add(newHunter.getName());
                                            sender.sendMessage(
                                                    Component.text(
                                                            newHunter.getName() + " has been added to the list of Hunters!"
                                                    ).color(TextColor.fromHexString(GREAT))
                                            );
                                        }
                                        )
                                )
                                .withSubcommand(new CommandAPICommand("remove")
                                    .withArguments(new PlayerArgument("firedHunter"))
                                    .executes((sender, args) -> {
                                        Player firedHunter = (Player) args.get("firedHunter");

                                        if (!ProtectThePresident.hunters.contains(firedHunter.getName())) {
                                            sender.sendMessage(
                                                    Component.text(
                                                            firedHunter.getName() + " cannot be removed as a hunter because they aren't one!"
                                                    ).color(TextColor.fromHexString(BAD))
                                            );
                                            return;
                                        }

                                        ProtectThePresident.hunters.remove(firedHunter.getName());
                                        sender.sendMessage(
                                                Component.text(
                                                        firedHunter.getName() + " has been removed from the list of Hunters!"
                                                ).color(TextColor.fromHexString(SUBPAR))
                                        );
                                    }
                                    )
                                )
                        )
                )

                .withSubcommand(new CommandAPICommand("settings")
                        .withSubcommand(new CommandAPICommand("mode")
                                .withArguments(new MultiLiteralArgument("game_mode", "endless","timed"))
                                .executes((sender, args) -> {
                                    if (args.get("game_mode").equals("endless")) {

                                        ((Timer) ProtectThePresident.CLOCK).countDown = false;
                                        sender.sendMessage(
                                                Component.text(
                                                        "The game mode has been switched to endless!"
                                                ).color(TextColor.fromHexString(GREAT))
                                        );

                                    } else {

                                        ((Timer) ProtectThePresident.CLOCK).countDown = true;
                                        sender.sendMessage(
                                                Component.text(
                                                        "The game mode has been switched to timed!"
                                                ).color(TextColor.fromHexString(GREAT))
                                        );

                                    }
                                })
                        )
                )
                .register();

    }

}
