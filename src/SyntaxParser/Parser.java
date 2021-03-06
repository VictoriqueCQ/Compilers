package SyntaxParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
    private static ArrayList<String> output = new ArrayList<>();
    private static Queue queue;
    private static Stack stack;
    private static String[] generations = {
            "S->id=E;",
            "S->if(C){S}else{S}",
            "S->while(C){S}",
            "E->TE'",
            "E'->+TE'",
            "E'->ε",
            "T->FT'",
            "T'->*FT'",
            "T'->ε",
            "F->(E)",
            "F->num",
            "F->id",
            "C->DC'",
            "C'->||DC'",
            "C'->ε",
            "D->(C)",
            "D->id==num"
    };

    private static int[][] table = {
            {0, -1, -1, 1, -1, -1, -1, -1, -1, 2, -1, -1, -1, -1, -1, -1},//S
            {3, -1, -1, -1, 3, -1, -1, -1, -1, -1, -1, -1, 3, -1, -1, -1},//E
            {-1, -1, 5, -1, -1, 5, -1, -1, -1, -1, 4, -1, -1, -1, -1, 5},//E'
            {6, -1, -1, -1, 6, -1, -1, -1, -1, -1, -1, -1, 6, -1, -1, -1},//T
            {-1, -1, 8, -1, -1, 8, -1, -1, -1, -1, 8, 7, -1, -1, -1, 8},//T'
            {11, -1, -1, -1, 9, -1, -1, -1, -1, -1, -1, -1, 10, -1, -1, -1},//F
            {12, -1, -1, -1, 12, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},//C
            {-1, -1, -1, -1, -1, 14, -1, -1, -1, -1, -1, -1, -1, 13, -1, 14},//C'
            {16, -1, -1, -1, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}//D
    };

    private static void output() {
        try {
            File outputFile = new File("Lab2_Resources/output.txt");
            if (!outputFile.exists())
                outputFile.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, false));
            for (String s : output) {
                System.out.println(s);
                bw.write(s);
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parse() {
        Token t1,t2;
        while (queue.get().getCode() != Token.END) {
            t1 = stack.get();
            t2 = queue.get();
            if (t1.getCode() > 99) {
                //非终结符
                if (!generate(t1, t2.getCode())) {
                    System.err.println("error one");
                    return;
                }
            } else {
                //终结符
                if (t1.getCode() == t2.getCode()) {
                    //匹配成功
                    stack.pop();
                    queue.dequeue();
                } else {
                    //否则报错
                    System.err.println("error two");
                    return;
                }
            }
        }
    }

    private static boolean generate(Token nextTokens, int next) {
        try {
            int i = table[nextTokens.getCode() - 100][getHeadIndex(next)];
            if (i < 0) {
                System.err.println("error three");
                return false;
            }
            output.add(generations[i]);
            //添加到输出队列
            stack.pop();
            switch (i) {
                case 0:
                    stack.push(new Token(Token.SEMICOLON, ";"));
                    stack.push(new Token(Token.E, "E"));
                    stack.push(new Token(Token.EQUAL, "="));
                    stack.push(new Token(Token.ID, "id"));
                    break;
                case 1:
                    stack.push(new Token(Token.RIGHT_BRACE, "}"));
                    stack.push(new Token(Token.S, "S"));
                    stack.push(new Token(Token.LEFT_BRACE, "{"));
                    stack.push(new Token(Token.ELSE, "else"));
                    stack.push(new Token(Token.RIGHT_BRACE, "}"));
                    stack.push(new Token(Token.S, "S"));
                    stack.push(new Token(Token.LEFT_BRACE, "{"));
                    stack.push(new Token(Token.RIGHT_PARENTHESE, ")"));
                    stack.push(new Token(Token.C, "C"));
                    stack.push(new Token(Token.LEFT_PARENTHESE, "("));
                    stack.push(new Token(Token.IF, "if"));
                    break;
                case 2:
                    stack.push(new Token(Token.RIGHT_BRACE, "}"));
                    stack.push(new Token(Token.S, "S"));
                    stack.push(new Token(Token.LEFT_BRACE, "{"));
                    stack.push(new Token(Token.RIGHT_PARENTHESE, ")"));
                    stack.push(new Token(Token.C, "C"));
                    stack.push(new Token(Token.LEFT_PARENTHESE, "("));
                    stack.push(new Token(Token.WHILE, "while"));
                    break;
                case 3:
                    stack.push(new Token(Token.E1, "E'"));
                    stack.push(new Token(Token.T, "T"));
                    break;
                case 4:
                    stack.push(new Token(Token.E1, "E'"));
                    stack.push(new Token(Token.T, "T"));
                    stack.push(new Token(Token.ADD, "+"));
                    break;
                case 5:
                    break;
                case 6:
                    stack.push(new Token(Token.T1, "T'"));
                    stack.push(new Token(Token.F, "F"));
                    break;
                case 7:
                    stack.push(new Token(Token.T1, "T'"));
                    stack.push(new Token(Token.F, "F"));
                    stack.push(new Token(Token.MUL, "*"));
                    break;
                case 8:
                    break;
                case 9:
                    stack.push(new Token(Token.RIGHT_PARENTHESE, ")"));
                    stack.push(new Token(Token.E, "E"));
                    stack.push(new Token(Token.LEFT_PARENTHESE, "("));
                    break;
                case 10:
                    stack.push(new Token(Token.NUMBER, "num"));
                    break;
                case 11:
                    stack.push(new Token(Token.ID, "id"));
                    break;
                case 12:
                    stack.push(new Token(Token.C1, "C'"));
                    stack.push(new Token(Token.D, "D"));
                    break;
                case 13:
                    stack.push(new Token(Token.C1, "C'"));
                    stack.push(new Token(Token.D, "D"));
                    stack.push(new Token(Token.DOUBLE_OR, "||"));
                    break;
                case 14:
                    break;
                case 15:
                    stack.push(new Token(Token.RIGHT_PARENTHESE, ")"));
                    stack.push(new Token(Token.C, "C"));
                    stack.push(new Token(Token.LEFT_PARENTHESE, "("));
                    break;
                case 16:
                    stack.push(new Token(Token.NUMBER, "num"));
                    stack.push(new Token(Token.DOUBLE_EQUAL, "=="));
                    stack.push(new Token(Token.ID, "id"));
                    break;
                default:
                    System.err.println("error four");
                    return false;
            }
            return true;
        } catch (Exception e) {
            System.err.println("error five");
            return false;
        }
    }

    private static int getHeadIndex(int code) {
        switch (code) {
            case 56:
                return 0;
            case 30:
                return 1;
            case 53:
                return 2;
            case 12:
                return 3;
            case 45:
                return 4;
            case 46:
                return 5;
            case 49:
                return 6;
            case 50:
                return 7;
            case 13:
                return 8;
            case 15:
                return 9;
            case 22:
                return 10;
            case 26:
                return 11;
            case 57:
                return 12;
            case 35:
                return 13;
            case 31:
                return 14;
            case 0:
                return 15;
            default:
                return -1;
        }
    }

    public static void main(String[] args) {
        ArrayList<Token> tokens = Analyzer.getTokens();
        queue = new Queue(tokens);
        stack = new Stack();
        stack.push(new Token(Token.S, "S"));
        parse();
        output();
    }

}
