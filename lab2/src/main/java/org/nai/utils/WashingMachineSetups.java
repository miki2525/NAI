package org.nai.utils;

import fuzzy4j.flc.ControllerBuilder;
import fuzzy4j.flc.FLC;
import fuzzy4j.flc.Term;
import fuzzy4j.flc.Variable;
import org.nai.utils.enums.Dirtiness;
import org.nai.utils.enums.WashingTime;
import org.nai.utils.enums.TypeOfDirt;
import org.nai.utils.enums.Weight;

import java.util.HashMap;
import java.util.Map;

/**
 * Terms Setup Loader for inputs/outputs
 *
 * @author Mikołaj Kalata
 * @author Adam Lichy
 */
public class WashingMachineSetups {

    private final static Map<Dirtiness, Term> dirtinessOfClothesTerms = loadDiritinessTerms();
    private final static Map<Weight, Term> weightTerms = loadWeightTerms();
    private final static Map<TypeOfDirt, Term> typeOfDirtTerms = loadTypeOfDirtTerms();
    private final static Map<WashingTime, Term> washingTimeTerms = loadWashingTimeTerms();
    private final Variable dirtinessOfClothesInput;
    private final Variable weightOfClothesInput;
    private final Variable typeOfDirtInput;
    private final Variable washingTimeOutput;
    private final FLC washingMachineFLC;

    /**
     * Constructor initializing Washing Machine Configuration
     */
    public WashingMachineSetups() {
        this.dirtinessOfClothesInput = loadDirtinessOfClothesInput();
        this.weightOfClothesInput = loadWeightOfClothesInput();
        this.typeOfDirtInput = loadTypeOfDirtOnClothesInput();
        this.washingTimeOutput = loadWashingTimeOutput();
        this.washingMachineFLC = assembleControllerAddingRules();
    }

    /**
     * Getter for dirtiness of clothes input
     *
     * @return Variable dirtiness of clothes
     */
    public Variable getDirtinessOfClothesInput() {
        return dirtinessOfClothesInput;
    }

    /**
     * Getter for weight of clothes input
     *
     * @return Variable weight of clothes
     */
    public Variable getWeightOfClothesInput() {
        return weightOfClothesInput;
    }

    /**
     * Getter for type of dirt input
     *
     * @return Variable type of dirt
     */
    public Variable getTypeOfDirtInput() {
        return typeOfDirtInput;
    }

    /**
     * Getter for time washing output
     *
     * @return Variable time washing
     */
    public Variable getWashingTimeOutput() {
        return washingTimeOutput;
    }

    /**
     * Getter for Fuzzy Logic Controller
     *
     * @return FLC
     */
    public FLC getWashingMachineFLC() {
        return washingMachineFLC;
    }

    /**
     * Initialize dirtiness of clothes input
     *
     * @return Variable dirtiness of clothes
     */
    private Variable loadDirtinessOfClothesInput() {
        return Variable.input(WashingMachineConstants.DIRTINESS,
                dirtinessOfClothesTerms.get(Dirtiness.SLIGHT),
                dirtinessOfClothesTerms.get(Dirtiness.NORMAL),
                dirtinessOfClothesTerms.get(Dirtiness.VERY));
    }

    /**
     * Initialize type of dirt on clothes input
     *
     * @return Variable type of dirt on clothes
     */
    private Variable loadTypeOfDirtOnClothesInput() {
        return Variable.input(
                WashingMachineConstants.TYPE,
                typeOfDirtTerms.get(TypeOfDirt.EASY),
                typeOfDirtTerms.get(TypeOfDirt.NORMAL),
                typeOfDirtTerms.get(TypeOfDirt.DIFICULT)
        );
    }

    /**
     * Initialize weight of clothes input
     *
     * @return Variable weight of clothes
     */
    private Variable loadWeightOfClothesInput() {
        return Variable.input(WashingMachineConstants.WEIGHT,
                weightTerms.get(Weight.LIGHT),
                weightTerms.get(Weight.NORMAL),
                weightTerms.get(Weight.HEAVY));
    }

    /**
     * Initialize washin time output
     *
     * @return Variable washing time
     */
    private Variable loadWashingTimeOutput() {
        return Variable.output(WashingMachineConstants.WASHING_TIME,
                        washingTimeTerms.get(WashingTime.SHORT),
                        washingTimeTerms.get(WashingTime.MEDIUM),
                        washingTimeTerms.get(WashingTime.LONG))
                .start(15.0)
                .end(240.0);
    }

    /**
     * describes possible output values based on inputs
     *
     * @return FLC - fuzzy logic controller
     */
    private FLC assembleControllerAddingRules() {
        return ControllerBuilder.newBuilder()

                //// SHORT WASHING TIME
                .when()
                .var(weightOfClothesInput)
                .is(weightTerms.get(Weight.LIGHT))
                .and()
                .var(dirtinessOfClothesInput)
                .is(dirtinessOfClothesTerms.get(Dirtiness.SLIGHT))
                .and()
                .var(typeOfDirtInput)
                .isNot(typeOfDirtTerms.get(TypeOfDirt.DIFICULT))
                .then()
                .var(washingTimeOutput)
                .is(washingTimeTerms.get(WashingTime.SHORT))

                .when()
                .var(weightOfClothesInput)
                .isNot(weightTerms.get(Weight.HEAVY))
                .and()
                .var(dirtinessOfClothesInput)
                .is(dirtinessOfClothesTerms.get(Dirtiness.SLIGHT))
                .and()
                .var(typeOfDirtInput)
                .is(typeOfDirtTerms.get(TypeOfDirt.EASY))
                .then()
                .var(washingTimeOutput).is(washingTimeTerms.get(WashingTime.SHORT))

                .when()
                .var(weightOfClothesInput)
                .is(weightTerms.get(Weight.LIGHT))
                .and()
                .var(dirtinessOfClothesInput)
                .isNot(dirtinessOfClothesTerms.get(Dirtiness.VERY))
                .and()
                .var(typeOfDirtInput)
                .is(typeOfDirtTerms.get(TypeOfDirt.EASY))
                .then()
                .var(washingTimeOutput)
                .is(washingTimeTerms.get(WashingTime.SHORT))

                //// MEDIUM WASHING TIME
                .when()
                .var(weightOfClothesInput)
                .isNot(weightTerms.get(Weight.LIGHT))
                .and()
                .var(dirtinessOfClothesInput)
                .is(dirtinessOfClothesTerms.get(Dirtiness.SLIGHT))
                .and()
                .var(typeOfDirtInput)
                .isNot(typeOfDirtTerms.get(TypeOfDirt.EASY))
                .then()
                .var(washingTimeOutput)
                .is(washingTimeTerms.get(WashingTime.MEDIUM))

                .when()
                .var(weightOfClothesInput)
                .is(weightTerms.get(Weight.LIGHT))
                .and()
                .var(typeOfDirtInput)
                .isNot(typeOfDirtTerms.get(TypeOfDirt.EASY))
                .and()
                .var(dirtinessOfClothesInput)
                .isNot(dirtinessOfClothesTerms.get(Dirtiness.SLIGHT))
                .then()
                .var(washingTimeOutput)
                .is(washingTimeTerms.get(WashingTime.MEDIUM))

                .when()
                .var(weightOfClothesInput)
                .isNot(weightTerms.get(Weight.LIGHT))
                .and()
                .var(typeOfDirtInput)
                .is(typeOfDirtTerms.get(TypeOfDirt.EASY))
                .and()
                .var(dirtinessOfClothesInput)
                .isNot(dirtinessOfClothesTerms.get(Dirtiness.SLIGHT))
                .then()
                .var(washingTimeOutput)
                .is(washingTimeTerms.get(WashingTime.MEDIUM))

                .when()
                .var(weightOfClothesInput)
                .is(weightTerms.get(Weight.HEAVY))
                .and()
                .var(dirtinessOfClothesInput)
                .isNot(dirtinessOfClothesTerms.get(Dirtiness.VERY))
                .and()
                .var(typeOfDirtInput)
                .isNot(typeOfDirtTerms.get(TypeOfDirt.DIFICULT))
                .then()
                .var(washingTimeOutput)
                .is(washingTimeTerms.get(WashingTime.MEDIUM))

                .when()
                .var(weightOfClothesInput)
                .isNot(weightTerms.get(Weight.HEAVY))
                .and()
                .var(dirtinessOfClothesInput)
                .is(dirtinessOfClothesTerms.get(Dirtiness.VERY))
                .and()
                .var(typeOfDirtInput)
                .isNot(typeOfDirtTerms.get(TypeOfDirt.DIFICULT))
                .then()
                .var(washingTimeOutput)
                .is(washingTimeTerms.get(WashingTime.MEDIUM))

                .when()
                .var(weightOfClothesInput)
                .isNot(weightTerms.get(Weight.HEAVY))
                .and()
                .var(dirtinessOfClothesInput)
                .isNot(dirtinessOfClothesTerms.get(Dirtiness.VERY))
                .and()
                .var(typeOfDirtInput)
                .is(typeOfDirtTerms.get(TypeOfDirt.DIFICULT))
                .then()
                .var(washingTimeOutput)
                .is(washingTimeTerms.get(WashingTime.MEDIUM))

                //// LONG WASHING TIME
                .when()
                .var(weightOfClothesInput)
                .is(weightTerms.get(Weight.HEAVY))
                .and()
                .var(dirtinessOfClothesInput)
                .is(dirtinessOfClothesTerms.get(Dirtiness.VERY))
                .then()
                .var(washingTimeOutput)
                .is(washingTimeTerms.get(WashingTime.LONG))

                .when()
                .var(weightOfClothesInput)
                .is(weightTerms.get(Weight.HEAVY))
                .and()
                .var(typeOfDirtInput)
                .is(typeOfDirtTerms.get(TypeOfDirt.DIFICULT))
                .then()
                .var(washingTimeOutput)
                .is(washingTimeTerms.get(WashingTime.LONG))

                .when()
                .var(dirtinessOfClothesInput)
                .is(dirtinessOfClothesTerms.get(Dirtiness.VERY))
                .and()
                .var(typeOfDirtInput)
                .is(typeOfDirtTerms.get(TypeOfDirt.DIFICULT))
                .then()
                .var(washingTimeOutput)
                .is(washingTimeTerms.get(WashingTime.LONG))

                .create();
    }

    /**
     * load dirtiness terms
     *
     * @return termsMap for Dirtiness terms
     * unit: %
     */
    private static Map<Dirtiness, Term> loadDiritinessTerms() {
        Map<Dirtiness, Term> dirtinessTerms = new HashMap<>();
        dirtinessTerms.put(
                Dirtiness.SLIGHT,
                Term.term(Dirtiness.SLIGHT.getDirtiness(), 0.0, 0.25, 0.5));
        dirtinessTerms.put(
                Dirtiness.NORMAL,
                Term.term(Dirtiness.NORMAL.getDirtiness(), 0.25, 0.5, 0.75));
        dirtinessTerms.put(
                Dirtiness.VERY,
                Term.term(Dirtiness.VERY.getDirtiness(), 0.5, 0.75, 1.1));
        return dirtinessTerms;
    }

    /**
     * load weight terms
     *
     * @return termsMap for Weight terms
     * unit: kg
     */
    private static Map<Weight, Term> loadWeightTerms() {
        Map<Weight, Term> weightTerms = new HashMap<>();
        weightTerms.put(
                Weight.LIGHT,
                Term.term(Weight.LIGHT.getWeight(), 0.0, 3.0, 6.0));
        weightTerms.put(
                Weight.NORMAL,
                Term.term(Weight.NORMAL.getWeight(), 3.0, 6.0, 9.0));
        weightTerms.put(
                Weight.HEAVY,
                Term.term(Weight.HEAVY.getWeight(), 6.0, 9.0, 12.0));
        return weightTerms;
    }

    /**
     * load type of dirt terms
     *
     * @return termsMap for TypeOfDirt terms
     */
    private static Map<TypeOfDirt, Term> loadTypeOfDirtTerms() {
        Map<TypeOfDirt, Term> typeOfDirtTerms = new HashMap<>();
        typeOfDirtTerms.put(
                TypeOfDirt.EASY,
                Term.term(TypeOfDirt.EASY.getTypeOfDirt(), 0.0, 1.0, 2.0)
        );
        typeOfDirtTerms.put(
                TypeOfDirt.NORMAL,
                Term.term(TypeOfDirt.NORMAL.getTypeOfDirt(), 1.0, 2.0, 3.0)
        );
        typeOfDirtTerms.put(
                TypeOfDirt.DIFICULT,
                Term.term(TypeOfDirt.DIFICULT.getTypeOfDirt(), 2.0, 3.0, 4.0)
        );
        return typeOfDirtTerms;
    }

    /**
     * load washing time terms
     *
     * @return termsMap for WashingTime terms
     * unit: minute
     */
    private static Map<WashingTime, Term> loadWashingTimeTerms() {
        Map<WashingTime, Term> washingTimeTerms = new HashMap<>();
        washingTimeTerms.put(
                WashingTime.SHORT,
                Term.term(WashingTime.SHORT.getTime(), 0.0, 15.0, 30.0));
        washingTimeTerms.put(
                WashingTime.MEDIUM,
                Term.term(WashingTime.MEDIUM.getTime(), 30.0, 45.0, 60.0));
        washingTimeTerms.put(
                WashingTime.LONG,
                Term.term(WashingTime.LONG.getTime(), 60.0, 90.0, 240.0));
        return washingTimeTerms;
    }

}
