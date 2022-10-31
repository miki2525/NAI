package org.nai.utils.enums;

/**
 * Enum represents names for specific terms
 *
 * @author Mikołaj Kalata
 * @author Adam Lichy
 */
public enum Dirtiness {
    SLIGHT("slight"),
    NORMAL("normal"),
    VERY("very");

    private final String dirtiness;

    Dirtiness(String dirtiness) {
        this.dirtiness = dirtiness;
    }

    public String getDirtiness() {
        return dirtiness;
    }
}
