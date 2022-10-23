package org.nai.utils.enums;

/**
 * Enum represents names for specific terms
 * @author Mikołaj Kalata
 * @author Adam Lichy
 *
 */
public enum WashingTime {
    SHORT("short"),
    MEDIUM("medium"),
    LONG("long");

    private final String time;

    WashingTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
