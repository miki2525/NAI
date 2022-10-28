package org.nai;

import fuzzy4j.flc.FLC;
import fuzzy4j.flc.InputInstance;
import fuzzy4j.flc.Variable;
import org.nai.utils.WashingMachineConstants;
import org.nai.utils.WashingMachineSetups;
import org.nai.utils.enums.Dirtiness;
import org.nai.utils.enums.WashingTime;
import org.nai.utils.enums.Weight;

import java.util.HashMap;
import java.util.Map;

/**
 * Main Class starting app
 * To run this project add fuzzy4j library to maven (you can import the library from repository: <a href="https://jitpack.io">...</a>)
 * Fuzzy4j source code: <a href="https://github.com/sorend/fuzzy4j">...</a>
 *
 * @author Mikołaj Kalata
 * @author Adam Lichy
 */
public class WashingMachineFuzzyDemo {
    public static void main(String[] args) {

/** TODO description
 * DESCRIPTION HERE
 */
        //PREPARE CONTROLLER INPUTS
        Variable dirtinessOfClothes = Variable.input(WashingMachineConstants.DIRTINESS,
                WashingMachineSetups.dirtinessOfClothesTerms.get(Dirtiness.SLIGHTY),
                WashingMachineSetups.dirtinessOfClothesTerms.get(Dirtiness.NORMAL),
                WashingMachineSetups.dirtinessOfClothesTerms.get(Dirtiness.VERY));

        Variable weightOfClothes = Variable.input(WashingMachineConstants.WEIGHT,
                WashingMachineSetups.weightTerms.get(Weight.LIGHT),
                WashingMachineSetups.weightTerms.get(Weight.NORMAL),
                WashingMachineSetups.weightTerms.get(Weight.HEAVY));

        //TODO add typeOfDirt
        Variable typeOfDirt = null;

        //PREPARE CONTROLLER OUTPUT
        Variable timeOfWashing = Variable.output(WashingMachineConstants.WASHING_TIME,
                WashingMachineSetups.washingTimeTerms.get(WashingTime.SHORT),
                WashingMachineSetups.washingTimeTerms.get(WashingTime.MEDIUM),
                WashingMachineSetups.washingTimeTerms.get(WashingTime.LONG));

        //PREPARE FUZZY CONTROLLER
        FLC flc = WashingMachineSetups.assembleControllerAddingRules(dirtinessOfClothes, weightOfClothes, typeOfDirt, timeOfWashing);

        //INITIALIZE SAMPLE DATA AND DISPLAY RESULTS
        Map<Variable, Double> sampleInputs = new HashMap<>();
        sampleInputs.put(dirtinessOfClothes, 0.25);
        sampleInputs.put(weightOfClothes, 3.0);
        InputInstance instance = InputInstance.wrap(sampleInputs);
        System.out.println("fuzzy = " + flc.applyFuzzy(instance)); //fuzzy = {washingTime=⊕_max([⊗_min([△(0.000000, 15.000000, 30.000000), 1.0])])}
        Map<Variable, Double> crisp = flc.apply(instance);
        System.out.println(crisp.get(timeOfWashing)); //0.6666500000000001
    }
}
