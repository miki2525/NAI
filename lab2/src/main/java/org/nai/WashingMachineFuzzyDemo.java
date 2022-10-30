package org.nai;

import fuzzy4j.flc.FLC;
import fuzzy4j.flc.InputInstance;
import fuzzy4j.flc.Variable;
import org.nai.utils.WashingMachineSetups;

import java.util.HashMap;
import java.util.Map;

/**
 * Main Class starting app
 * To run this project add fuzzy4j library to maven (you can import the library from repository: <a href="https://jitpack.io">...</a>)
 * Fuzzy4j source code: <a href="https://github.com/sorend/fuzzy4j">...</a>
 * Initialize WashineMachineSetups to load WashingMachineFuzzy configuration
 *
 * @author Mikołaj Kalata
 * @author Adam Lichy
 */
public class WashingMachineFuzzyDemo {
    public static void main(String[] args) {

/** TODO description
 * DESCRIPTION HERE
 */
        //INITIALIZE CONFIGURATION
        WashingMachineSetups config = new WashingMachineSetups();

        //INPUTS
        Variable dirtinessOfClothes = config.getDirtinessOfClothesInput();
        Variable weightOfClothes = config.getWeightOfClothesInput();
        Variable typeOfDirt = config.getTypeOfDirtInput();

        //OUTPUT
        Variable timeOfWashing = config.getWashingTimeOutput();

        //FUZZY CONTROLLER
        FLC flc = config.getWashingMachineFLC();

        //INITIALIZE SAMPLE DATA AND DISPLAY RESULTS
        Map<Variable, Double> sampleInputs = new HashMap<>();
        //TODO add typeOfDirt to sampleInputs
        sampleInputs.put(dirtinessOfClothes, 0.25);
        sampleInputs.put(weightOfClothes, 3.0);
        InputInstance instance = InputInstance.wrap(sampleInputs);
        //TODO provide screenshots with 2 different inputs data suite
        System.out.println("fuzzy = " + flc.applyFuzzy(instance)); //fuzzy = {washingTime=⊕_max([⊗_min([△(0.000000, 15.000000, 30.000000), 1.0])])}
        Map<Variable, Double> crisp = flc.apply(instance);
        System.out.println(crisp.get(timeOfWashing)); //0.6666500000000001
    }
}
