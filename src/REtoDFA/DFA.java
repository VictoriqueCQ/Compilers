package REtoDFA;

import java.util.LinkedList;

public class DFA {
    private LinkedList<State> dfa;

    public DFA() {
        this.setDFA(new LinkedList<State>());
        this.getDFA().clear();
    }

    public LinkedList<State> getDFA() {
        return dfa;
    }

    public void setDFA(LinkedList<State> nfa) {
        this.dfa = nfa;
    }
}
