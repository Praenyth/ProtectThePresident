package lol.praenyth.plugins.protectthepresident.api;

import net.kyori.adventure.text.Component;

import java.util.concurrent.TimeUnit;


public class Clock {

    public static Component secondsToClock(int initialSeconds) {
        int hours = (int) TimeUnit.SECONDS.toHours(initialSeconds);
        int minutes = (int) (TimeUnit.SECONDS.toMinutes(initialSeconds) - TimeUnit.HOURS.toMinutes(hours));
        int seconds = (int) (TimeUnit.SECONDS.toSeconds(initialSeconds) - TimeUnit.MINUTES.toSeconds(minutes));
        if (seconds < 0) {
            seconds = 0;
        }
        if (hours == 0) {
            return Component.text(String.format("%02d:%02d:%02d", hours, minutes, seconds));
        } else {
            return Component.text(String.format("%02d:%02d", minutes, seconds));
        }
    }

}
