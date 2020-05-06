package kellerAutomat;

import java.util.ArrayList;
import java.util.List;

public class KellerAutomat<T> {
    private Stack<T> keller;
    private List<State<T>> allStates;
    private State<T> currentState;

    /**
     * initializes allstates arraylist and the stack
     */
    public void setup() {
        allStates = new ArrayList<>();
        keller = new Stack<>();
    }
    
    public void setCurrentState(State<T> s) {
        currentState = s;
    }
    
    public List<State<T>> getAllStates(){
        return allStates;
    }
    
    public void addToKeller(T in) {
        keller.push(in);
    }
    
    /**
     * loops through the input array and calculates the next state
     * @param input
     * @return returns true when the last state is an endstate false if not or at one time no new state was reached
     */
    public boolean calculate(T[] input) {
        for (int i = 0; i < input.length+1; i++) {
            if (currentState == null) {
                return false;
            } else {
                currentState = currentState.nextState((i < input.length ? input[i] : null), keller);
            
            }
        }
        return currentState.getEndState();
    }

    public void setAllStates(List<State<T>> allS) {
        allStates = allS;
    }

}
