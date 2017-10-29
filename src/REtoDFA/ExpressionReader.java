package REtoDFA;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ExpressionReader {
    private static Scanner s;

    private static String regular;

    //所有的表达式
    private static ArrayList<String> expressions = new ArrayList<String>();

    //NFA
    private static NFA nfa;

    //DFA
    private static DFA dfa;

    public static void main(String[] args) {
        //读取RE.txt
        try {
            s = new Scanner(new File("RE.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //读取正则表达式
        regular = s.next();

        //读取所有表达式
        while (s.hasNext()) {
            expressions.add(s.next());
        }

        //使用NFA和汤普森算法生成DFA
        setNFA(RegularExpression.generateNFA(regular));

        //使用NFA和之前的DFA自己构造生成
        setDFA(RegularExpression.generateDFA(getNFA()));
        for (int i = 0; i < getDFA().getDFA().size(); i++) {
            System.out.println(getDFA().getDFA().get(i).getStateId() + "   "
                    + getDFA().getDFA().get(i).getNextState().get('a').get(0).getStateId());
        }
    }

    public static NFA getNFA() {
        return nfa;
    }

    public static void setNFA(NFA nfa) {
        ExpressionReader.nfa = nfa;
    }

    public static DFA getDFA() {
        return dfa;
    }

    public static void setDFA(DFA dfa) {
        ExpressionReader.dfa = dfa;
    }
}
