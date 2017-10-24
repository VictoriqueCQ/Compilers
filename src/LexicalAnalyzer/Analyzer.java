package LexicalAnalyzer;

import java.io.*;
import java.util.ArrayList;

public class Analyzer {
    //存储输入的字符串
    private static char input[] = new char[1000];
    //输出的内容
    private static ArrayList<Token> outPut = new ArrayList<>();
    //单词符号
    private static char word[];
    //常数
    private static int number;
    //单词种别
    private static int code;

    //
    private static int p;
    //
    private static String inputFile;

    //以下是可识别的单词符号串
    private static String[] reservedWord = {
            "class",
            "public",
            "protected",
            "private",
            "void",
            "static",
            "int",
            "char",
            "float",
            "double",
            "string",
            "if",
            "else",
            "do",
            "while",
            "try",
            "catch",
            "switch",
            "case",
            "for"
    };

    private static void getInput() throws IOException {
        inputFile = "input.txt";
        BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream(new File(inputFile))));
        String line = null;
        char[] temp = null;
        p = 0;
        while ((line = br2.readLine()) != null) {
            temp = line.toCharArray();
            for (int i = 0; i < temp.length; i++) {
                if (temp[i] == ' ' || temp[i] == '\t') {
                    continue;
                }
                input[p++] = temp[i];
            }
            input[p++] = '\n';
        }
        input[p] = '#';
        br2.close();
    }

    private static void setOutPut() throws IOException {
        String[] splits = inputFile.split("\\.");
        File outPutFile = new File(splits[0] + "1.txt");
        if (!outPutFile.exists()) {
            outPutFile.createNewFile();
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(outPutFile, false));
        for (Token t : outPut) {
            System.out.println(t.toString());
            bw.write(t.toString());
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }

    private static void scanner() {
        //当前读的字符
        char ch;
        int sp;
        word = new char[20];
        ch = input[p++];
        //可能是保留字或变量名（保留字优先）
        if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
            sp = 0;
            while ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
                word[sp++] = ch;
                ch = input[p++];
                word[sp] = '\0';
                for (int i = 0; i < reservedWord.length; i++) {
                    if (chTos(word).equals(reservedWord[i])) {
                        code = i + 1;
                        p--;
                        return;
                    }
                }
            }
            word[sp++] = '\0';
            //放回多读的
            p--;
            code = 56;
        } else if (ch >= '0' && ch <= '9') {
            // 可能是正常数
            number = 0;
            while (ch >= '0' && ch <= '9') {
                number = number * 10 + ch - '0';
                ch = input[p++];
            }
            p--;
            code = 57;
            if (number < 0) {
                // 正数超过最大值变成负数，报错
                code = -2;
            }
        } else {
            // 其他字符
            sp = 0;
            word[sp++] = ch;
            switch (ch) {
                case '+':
                    ch = input[p++];
                    if (ch == '=') {
                        //符号为+=
                        code = 23;
                        word[sp++] = ch;
                    } else {
                        //符号为+
                        code = 22;
                        p--;
                    }
                    break;
                case '-':
                    ch = input[p++];
                    // 可能是负常数
                    if (ch >= '0' && ch <= '9') {
                        number = 0;
                        while (ch >= '0' && ch <= '9') {
                            number = number * 10 + ch - '0';
                            ch = input[p++];
                        }
                        p--;
                        code = 57;
                        if (number < 0) {
                            code = -2;
                        }
                        // 变成负数
                        number *= -1;
                    } else if (ch == '=') {
                        //符号为-=
                        code = 25;
                        word[sp++] = ch;
                    } else {
                        //符号为-
                        code = 24;
                        p--;
                    }
                    break;
                case '*':
                    ch = input[p++];
                    if (ch == '=') {
                        //符号为*=
                        code = 27;
                        word[sp++] = ch;
                    } else if (ch == '/') {
                        //符号为*/
                        code = 44;
                        word[sp++] = ch;
                    } else {
                        //符号为*
                        code = 26;
                        p--;
                    }
                    break;
                case '/':
                    ch = input[p++];
                    if (ch == '=') {
                        //符号为/=
                        code = 29;
                        word[sp++] = ch;
                    } else if (ch == '/') {
                        //符号为//
                        code = 42;
                        word[sp++] = ch;
                    } else if (ch == '*') {
                        //符号为/*
                        code = 26;
                        word[sp++] = ch;
                    } else {
                        //符号为/
                        code = 28;
                        p--;
                    }
                    break;
                case '=':
                    ch = input[p++];
                    if (ch == '=') {
                        //符号为==
                        code = 31;
                        word[sp++] = ch;
                    } else {
                        //符号为=
                        code = 30;
                        p--;
                    }
                    break;
                case '<':
                    ch = input[p++];
                    if (ch == '=') {
                        //符号为<=
                        code = 39;
                        word[sp++] = ch;
                    } else {
                        //符号为<
                        code = 38;
                        p--;
                    }
                    break;
                case '>':
                    ch = input[p++];
                    if (ch == '=') { // >=
                        code = 41;
                        word[sp++] = ch;
                    } else { // >
                        code = 40;
                        p--;
                    }
                    break;
                case '&':
                    ch = input[p++];
                    if (ch == '&') { // &&
                        code = 33;
                        word[sp++] = ch;
                    } else { // &
                        code = 32;
                        p--;
                    }
                    break;
                case '|':
                    ch = input[p++];
                    if (ch == '|') {
                        //符号为||
                        code = 35;
                        word[sp++] = ch;
                    } else {
                        //符号为|
                        code = 34;
                        p--;
                    }
                    break;
                case '!':
                    ch = input[p++];
                    if (ch == '=') {
                        //符号为!=
                        code = 37;
                        word[sp++] = ch;
                    } else {
                        //符号为!
                        code = 36;
                        p--;
                    }
                    break;
                case '(':
                    code = 45;
                    break;
                case ')':
                    code = 46;
                    break;
                case '[':
                    code = 47;
                    break;
                case ']':
                    code = 48;
                    break;
                case '{':
                    code = 49;
                    break;
                case '}':
                    code = 50;
                    break;
                case ',':
                    code = 51;
                    break;
                case ':':
                    code = 52;
                    break;
                case ';':
                    code = 53;
                    break;
                case '\'':
                    code = 54;
                    break;
                case '\"':
                    code = 55;
                    break;
                case '\n':
                    code = -1;
                    break;
                default:
                    code = -3;
                    break;
            }
        }
    }

    private static String chTos(char[] c) {
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            if (c[i] != '0') {
                len++;
            }
        }
        return String.valueOf(c).substring(0, len);
    }

    public static void main(String[] args) {
        int row;
        try {
            getInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        p = 0;
        row = 1;
        while (input[p] != '#') {
            scanner();
            switch (code) {
                case 57:
                    outPut.add(new Token(code, number + ""));
                    break;
                case -1:
                    row++;
                    break;
                case -2:
                    outPut.add(new Token("Integer overflow at row " + row));
                    break;
                case -3:
                    outPut.add(new Token("Undefined character at row " + row));
                    break;
                default:
                    outPut.add(new Token(code, chTos(word)));
                    break;
            }
            try {
                setOutPut();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
