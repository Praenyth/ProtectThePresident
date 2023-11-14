package lol.praenyth.plugins.protectthepresident.enums;

public enum Roles {
    PRESIDENTS(0x66ff8f),
    BODYGUARDS(0xffe066),
    HUNTERS(0xff6966),
    SPECTATORS(0xAAAAAA);

    private final int color;

    Roles(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}
