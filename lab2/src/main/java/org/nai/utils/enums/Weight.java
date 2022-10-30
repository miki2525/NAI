package org.nai.utils.enums;

/**
 * Enum represents names for specific terms
 *
 * @author Mikołaj Kalata
 * @author Adam Lichy
 */
public enum Weight {

    LIGHT("light"),
    NORMAL("normal"),
    HEAVY("heavy");

    private final String weight;

    Weight(String weight) {
        this.weight = weight;
    }

    public String getWeight() {
        return weight;
    }
}
