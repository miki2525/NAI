package org.nai;

import fuzzy4j.flc.FLC;
import fuzzy4j.flc.InputInstance;
import fuzzy4j.flc.Variable;
import java.util.HashMap;
import java.util.Map;
import org.nai.utils.WashingMachineSetups;

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
    /**
     * Our application represents Washing Mashine which automaticaly selects best
     * washing time based on 3 inputs which are dirtinessOfClothes, weightOfClothes
     * and typeOfDirtOnClothes.
     */
    //INITIALIZE CONFIGURATION
    WashingMachineSetups config = new WashingMachineSetups();

    //INPUTS
    Variable dirtinessOfClothes = config.getDirtinessOfClothesInput();
    Variable weightOfClothes = config.getWeightOfClothesInput();
    Variable typeOfDirtOnClothes = config.getTypeOfDirtInput();

    //OUTPUT
    Variable timeOfWashing = config.getWashingTimeOutput();

    //FUZZY CONTROLLER
    FLC flc = config.getWashingMachineFLC();

    //INITIALIZE SAMPLE DATA AND DISPLAY RESULTS
    Map<Variable, Double> sampleInputs = new HashMap<>();
    // sampleInputs.put(dirtinessOfClothes, 0.25);
    // sampleInputs.put(weightOfClothes, 3.0);
    // sampleInputs.put(typeOfDirtOnClothes, 0.5);
    sampleInputs.put(dirtinessOfClothes, 0.75);
    sampleInputs.put(weightOfClothes, 9.0);
    sampleInputs.put(typeOfDirtOnClothes, 0.25);
    InputInstance instance = InputInstance.wrap(sampleInputs);
    System.out.println("fuzzy = " + flc.applyFuzzy(instance)); //fuzzy = {washingTime=⊕_max([⊗_min([△(0.000000, 15.000000, 30.000000), 1.0])])}
    Map<Variable, Double> crisp = flc.apply(instance);
    System.out.println(crisp.get(timeOfWashing)); //0.6666500000000001
  }
}
