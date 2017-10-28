package REtoDFA;

import java.util.LinkedList;

public class NFA {
    private LinkedList<State> nfa;

    public NFA() {
        this.setNFA(new LinkedList<State>());
        this.getNFA().clear();
    }

    public LinkedList<State> getNFA() {
        return nfa;
    }

    public void setNFA(LinkedList<State> nfa) {
        this.nfa = nfa;
    }
}
