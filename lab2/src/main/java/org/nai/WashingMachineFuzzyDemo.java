package org.nai;

import fuzzy4j.flc.FLC;
import fuzzy4j.flc.InputInstance;
import fuzzy4j.flc.Variable;
import fuzzy4j.sets.FuzzyFunction;
import org.nai.utils.WashingMachineSetups;

import java.util.HashMap;
import java.util.Map;

/**
 * Main Class starting app
 * To run this project add fuzzy4j library to maven (you can import the library from repository: <a href="https://jitpack.io">...</a>)
 * Fuzzy4j source code: <a href="https://github.com/sorend/fuzzy4j">...</a>
 * Initialize WashineMachineSetups to load WashingMachineFuzzy configuration
 *
 * The application represents Washing Mashine which automaticaly selects best
 * washing time based on 3 inputs which are dirtinessOfClothes, weightOfClothes
 * and typeOfDirtOnClothes.
 * @author Mikołaj Kalata
 * @author Adam Lichy
 */
public class WashingMachineFuzzyDemo {
    public static void main(String[] args) {


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

        /**
         * valid range of values for inputs are configured in terms in WashingMachineSetups
         * @see WashingMachineSetups#loadDiritinessTerms()
         * @see WashingMachineSetups#loadWeightTerms()
         * @see WashingMachineSetups#loadTypeOfDirtTerms()
         */

        //INITIALIZE SAMPLE DATA AND DISPLAY RESULTS
        Map<Variable, Double> sampleInputs = new HashMap<>();
        sampleInputs.put(dirtinessOfClothes, 1.0);
        sampleInputs.put(weightOfClothes, 4.5);
        sampleInputs.put(typeOfDirt, 2.0);
        InputInstance instance = InputInstance.wrap(sampleInputs);
        Map<Variable, FuzzyFunction> result = flc.applyFuzzy(instance);
        Map<Variable, Double> crisp = flc.apply(instance);
        Double washingTimeCrisp = crisp.get(timeOfWashing);
        int washingTimeResult;
        try {
            washingTimeResult = Math.toIntExact(Math.round(washingTimeCrisp));
        } catch (NullPointerException e) {
            throw new RuntimeException("Bad inputs");
        }
        System.out.println("Sample inputs = " + sampleInputs);
        System.out.println("Fuzzy = " + result);
        System.out.println("Washing Time = " + washingTimeResult + " minutes");
    }
}
