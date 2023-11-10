package lol.praenyth.plugins.protectthepresident.enums;

public enum Roles {
    PRESIDENTS("&aP"),
    BODYGUARDS("&eB"),
    HUNTERS("&cH"),
    SPECTATORS("&7");

    private final String name;

    Roles(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
