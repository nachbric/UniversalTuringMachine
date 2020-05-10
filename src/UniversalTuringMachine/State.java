package UniversalTuringMachine;

import java.util.ArrayList;
import java.util.List;
/**
 * generic state class
 *
 * @param <T>
 */
public class State<T> {
    private final List<State<T>> nextStates;
    private final List<T> bandStates;
    private final List<T> bandChanges;
    private final List<Integer> dirChanges;
    private boolean endState;
    private final int stateNum;

    public State(int n) {
        nextStates = new ArrayList<>();
        bandStates = new ArrayList<>();
        bandChanges = new ArrayList<>();
        dirChanges = new ArrayList<>();
        stateNum = n;
    }

    public void setEndState(boolean endState) {
        this.endState = endState;
    }

    public boolean getEndState() {
        return endState;
    }

    /**
     * each entry is a new transition
     *
     * @param nS
     * @param bandState
     * @param bandChange
     * @param dirChange
     */
    public void addTransition(State<T> nS, T bandState, T bandChange, int dirChange) {
        nextStates.add(nS);
        bandStates.add(bandState);
        bandChanges.add(bandChange);
        dirChanges.add(dirChange);
    }

    /**
     * gets the next state from current state and band
     *
     * @param band
     * @return returns the next state
     */
    public State<T> nextState(Band<T> band) {
        for (int i = 0; i < nextStates.size(); i++) {
            if (bandStates.get(i) == band.getBandState()){
                band.change(dirChanges.get(i), bandChanges.get(i));
                return nextStates.get(i);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "State: q" + stateNum;
    }
}
