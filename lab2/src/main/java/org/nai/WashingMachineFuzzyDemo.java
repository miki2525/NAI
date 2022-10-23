package org.nai;

import fuzzy4j.flc.Variable;
import org.nai.utils.WashingMachineConstants;
import org.nai.utils.WashingMachineSetups;
import org.nai.utils.enums.Dirtiness;
import org.nai.utils.enums.WashingTime;
import org.nai.utils.enums.Weight;

/**
 * Main Class starting app
 * @author Mikołaj Kalata
 * @author Adam Lichy
 *
 */
public class WashingMachineFuzzyDemo {
    public static void main(String[] args) {

/** TODO description
 * DESCRIPTION HERE
 */
        //PREPARE INPUTS
        Variable dirtinessOfClothes = Variable.input(WashingMachineConstants.DIRTINESS,
                WashingMachineSetups.dirtinessOfClothesTerms.get(Dirtiness.SLIGHTY),
                WashingMachineSetups.dirtinessOfClothesTerms.get(Dirtiness.NORMAL),
                WashingMachineSetups.dirtinessOfClothesTerms.get(Dirtiness.VERY));

        Variable weightOfClothes = Variable.input(WashingMachineConstants.WEIGHT,
                WashingMachineSetups.weightTerms.get(Weight.LIGHT),
                WashingMachineSetups.weightTerms.get(Weight.NORMAL),
                WashingMachineSetups.weightTerms.get(Weight.HEAVY));

        //todo add typeOfDirt
        Variable typeOfDirt;
        
        //PREPARE OUTPUT
        Variable timeOfWashing = Variable.input(WashingMachineConstants.WASHING_TIME,
                WashingMachineSetups.washingTimeTerms.get(WashingTime.SHORT),
                WashingMachineSetups.washingTimeTerms.get(WashingTime.MEDIUM),
                WashingMachineSetups.washingTimeTerms.get(WashingTime.LONG));
    }

}
