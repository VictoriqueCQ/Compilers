package REtoDFA;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ExpressionReader{
    private static Scanner s;

    private static String regular;

    // Reads all the expressions in this arrayList
    private static ArrayList<String> expressions = new ArrayList<String>();

    // stores the NFA
    private static NFA nfa;

    // stores the DFA
    private static DFA dfa;

    public static void main(String[] args){
        // Create a Scanner object
        try {
            s = new Scanner(new File("RE.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Read the regular expression
        regular = s.next();

        // Read all the expressions to apply the regular expression
        while (s.hasNext()) {
            expressions.add(s.next());
        }

        // Generate NFA using thompson algorithms with the Regular Expression
        setNFA(RegularExpression.generateNFA(regular));

        // Generate DFA using the previous NFA and the Subset Construction
        // Algorithm
        setDFA(RegularExpression.generateDFA(getNFA()));
        for (int i = 0; i < getDFA().getDFA().size(); i++) {
            System.out.println(getDFA().getDFA().get(i).getStateId() + "   "
                    + getDFA().getDFA().get(i).getNextState().get('a').get(0).getStateId());
        }
    }

    public static NFA getNFA(){
        return nfa;
    }

    public static void setNFA(NFA nfa){
        ExpressionReader.nfa = nfa;
    }

    public static DFA getDFA(){
        return dfa;
    }

    public static void setDFA(DFA dfa){
        ExpressionReader.dfa = dfa;
    }
}
