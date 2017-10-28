package REtoDFA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class State {

    private int StateId;

    private Set<State> States;

    private boolean AcceptState;

    private Map<Character, ArrayList<State>> nextState;

    // This constructor is used for NFA
    public State(int Id) {

    }

    // This constructor is used for DFA
    public State(Set<State> States, int Id) {

    }

    // Add transition between states and insert into the arrayList
    // or create the arrayList based on key
    public void addTransition(State next, char key) {
        ArrayList<State> list = this.nextState.get(key);

        if (list == null) {
            list = new ArrayList<State>();
            this.nextState.put(key, list);
        }

        list.add(next);
    }

    // Get all transition states based on symbol
    public ArrayList<State> getAllTransitions(char c) {
        if (this.nextState.get(c) == null) {
            return new ArrayList<State>();
        } else {
            return this.nextState.get(c);
        }
    }

    // Autogenerated Getters and Setters
    public Map<Character, ArrayList<State>> getNextState() {
        return nextState;
    }

    public void setNextState(HashMap<Character, ArrayList<State>> hashMap) {
        this.nextState = hashMap;
    }

    public int getStateId() {
        return StateId;
    }

    public void setStateId(int StateId) {
        this.StateId = StateId;
    }

    public boolean isAcceptState() {
        return AcceptState;
    }

    public void setAcceptState(boolean AcceptState) {
        this.AcceptState = AcceptState;
    }

    public Set<State> getStates() {
        return States;
    }

    public void setStates(Set<State> States) {
        this.States = States;
    }
}
