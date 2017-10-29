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

    //用于NFA的构造器
    public State(int Id) {
        this.setStateId(Id);
        this.setNextState(new HashMap <Character, ArrayList<State>> ());
        this.setAcceptState(false);
    }

    //用于DFA的构造器
    public State(Set<State> States, int Id) {
        this.setStates(States);
        this.setStateId(Id);
        this.setNextState(new HashMap <Character, ArrayList<State>> ());

        //是否有终结符
        for (State p : States) {
            if (p.isAcceptState()) {
                this.setAcceptState(true);
                break;
            }
        }
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

    //基于符号获得所有过渡状态
    public ArrayList<State> getAllTransitions(char c) {
        if (this.nextState.get(c) == null) {
            return new ArrayList<State>();
        } else {
            return this.nextState.get(c);
        }
    }

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
