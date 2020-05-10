package UniversalTuringMachine;

import java.util.ArrayList;
import java.util.List;

/**
 * generic turing machine with one band
 * @param <T>
 */
public class TuringMachine<T> {
    private Band<T> band;
    private List<State<T>> allStates;
    private State<T> currentState;
    private int counter = 0;
    public void setup(T emptyBandSym, T leaveSymAlone) {
        allStates = new ArrayList<>();
        band = new Band<>(emptyBandSym, leaveSymAlone);
        band.initBand();
        counter = 0;
    }
    
    public void setCurrentState(State<T> s) {
        currentState = s;
    }

    public State<T> getCurrentState(){
        return currentState;
    }
    public List<State<T>> getAllStates(){
        return allStates;
    }

    public void bandInput(int dir, T change){
        band.change(dir, change);
    }

    /**
     * one step forward
     *
     * @return returns true if the current state is an endstate
     */
    public boolean singleStep(){
        if (currentState == null) {
            return false;
        } else {
            currentState = currentState.nextState(band);
            counter ++;
        }
        System.out.println("Step Nr. "+counter);
        System.out.println("Current " + currentState);
        System.out.println("Current Band: " + band.toPartialBand());
        System.out.println("----------------------------------------");
        return currentState.getEndState();
    }

    public Band<T> getBand(){
        return band;
    }

    /**
     * goes through all steps at once
     *
     * @return returns true if an endstate occured but keeps going if none is found
     */
    public boolean runAll(){
        while (!currentState.getEndState()){
            singleStep();
        }
        return currentState.getEndState();
    }

    public void setAllStates(List<State<T>> allS) {
        allStates = allS;
    }
}
