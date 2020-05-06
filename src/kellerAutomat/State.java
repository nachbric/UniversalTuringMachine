package kellerAutomat;

import java.util.ArrayList;
import java.util.List;
/**
 * @author R.N
 * 
 * generic state class
 * @param <T>
 */
public class State<T> {
    private List<State<T>> nextStates;
    private List<T> inputs;
    private List<T> stackStates;
    private List<T[]> stackChanges;
    private boolean endState;
    private int stateNum;

    private T EMPTY_STACK_SYMBOL;
    private T EMPTY_SYMBOL;
    
    /**
     * initializes all arraylists
     * @param n number of the state
     */
    public State(int n) {
        nextStates = new ArrayList<>();
        inputs = new ArrayList<>();
        stackStates = new ArrayList<>();
        stackChanges = new ArrayList<>();
        stateNum = n;
    }

    public void setEndState(boolean endState) {
        this.endState = endState;
    }

    public void setEmptyStackSymbol(T in) {
        EMPTY_STACK_SYMBOL = in;
    }

    public void setEmptySymbol(T in) {
        EMPTY_SYMBOL = in;
    }

    public boolean getEndState() {
        return endState;
    }
    
    /**
     * used for adding new transitions
     * @param nS instance of the next state
     * @param input the value of the input needed for succesfull transition
     * @param stackState the value of the stackstate needed for succesfull transition
     * @param stackChange the value of how the stacks changes if succesfull transition is possible
     */
    public void addTransition(State<T> nS, T input, T stackState, T[] stackChange) {
        nextStates.add(nS);
        inputs.add(input);
        stackStates.add(stackState);
        stackChanges.add(stackChange);
    }

    /**
     * calculates the next state with current given state
     * @param input the current input
     * @param stack the stack to manipulate
     * @return returns the next state. null if no state is reachable
     */
    public State<T> nextState(T input, Stack<T> stack) {
        for (int i = 0; i < nextStates.size(); i++) {
            T temp = stack.pop();
            if (temp.equals(EMPTY_STACK_SYMBOL)) {
                stack.push(EMPTY_STACK_SYMBOL);
            }
            if (inputs.get(i) == input) {
                if (stackStates.get(i) == temp) {
                    for (T t : stackChanges.get(i)) {
                        if (t != EMPTY_SYMBOL) {
                            stack.push(t);
                        }
                    }
                    return nextStates.get(i);
                } else {
                    stack.push(temp);
                }
            } else {
                stack.push(temp);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "State: q" + stateNum;
    }
}
