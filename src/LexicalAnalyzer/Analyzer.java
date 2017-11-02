package LexicalAnalyzer;

import java.io.*;
import java.util.ArrayList;

public class Analyzer {
    //存储输入的字符数组
    private static char input[] = new char[1000];
    //存储输出Token的arraylist
    private static ArrayList<Token> output = new ArrayList<>();
    //单词符号
    private static char word[];
    //常数
    private static int num;
    //单词种别码
    private static int code;
    //指针
    private static int p;

    private static String inputFile;

    // 以下是可识别的保留字
    private static String[] reservedWords = {
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
            "for",
            "return"
    };

//    private static String[] operators = {
//            "+",
//            "+=",
//            "-",
//            "-=",
//            "*",
//            "*=",
//            "/",
//            "/=",
//            "=",
//            "==",
//            "&",
//            "|",
//            "&&",
//            "||",
//            "!",
//            "!=",
//            "<",
//            "<=",
//            ">",
//            ">="
//    };
//    private static String[] notes = {
//            "//",
//            "/*",
//            "*/"
//    };
//    private static String[] others = {
//            "(",
//            ")",
//            "[",
//            "]",
//            "{",
//            "}",
//            ";",
//            ",",
//            ":",
//            "'",
//            "\""
//    };

    private static void getInput() throws IOException {
        inputFile = "input1.txt";
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(new File(inputFile))));
        String line;
        char[] temp;
        p = 0;
        while ((line = br.readLine()) != null) {
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
        br.close();
    }

    private static void output() throws IOException {
        File outputFile = new File("output.txt");
        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, false));
        for (Token t : output) {
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

        if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
            // 可能是保留字或变量名（保留字优先级高于变量名）
            sp = 0;
            while ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z')
                    || (ch >= 'A' && ch <= 'Z')) {
                word[sp++] = ch;
                ch = input[p++];
                word[sp] = '\0';
                for (int i = 0; i < reservedWords.length; i++) {
                    if (typeConversion(word).equals(reservedWords[i])) {
                        code = i + 1;
                        p--;
                        return;
                    }
                }
            }
            word[sp++] = '\0';
            p--;
            //放回多读的
            code = 56;

        } else if (ch >= '0' && ch <= '9') {
            //可能是正常数
            num = 0;
            while (ch >= '0' && ch <= '9') {
                num = num * 10 + ch - '0';
                ch = input[p++];
            }
            p--;
            code = 57;
            if (num < 0)
                code = -2;
            //正数超过最大值变成负数，报错
        } else {
            //其他字符
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
                    if (ch >= '0' && ch <= '9') {
                        //可能是负常数
                        num = 0;
                        while (ch >= '0' && ch <= '9') {
                            num = num * 10 + ch - '0';
                            ch = input[p++];
                        }
                        p--;
                        code = 57;
                        if (num < 0)
                            code = -2;
                        num *= -1;
                        //变成负数
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
                    if (ch == '=') {
                        //符号为>=
                        code = 41;
                        word[sp++] = ch;
                    } else {
                        //符号为>
                        code = 40;
                        p--;
                    }
                    break;
                case '&':
                    ch = input[p++];
                    if (ch == '&') {
                        //符号为&&
                        code = 33;
                        word[sp++] = ch;
                    } else {
                        //符号为&
                        code = 32;
                        p--;
                    }
                    break;
                case '|':
                    ch = input[p++];
                    if (ch == '|') {
                        //符号为||
                        p--;
                        code = 35;
                        word[sp++] = ch;
                    } else {
                        //符号为|
                        code = 34;
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
                case '%':
                    code = 58;
                    break;
                default:
                    code = -3;
                    break;
            }
        }
    }

    private static String typeConversion(char[] c) {
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            if (c[i] != '\0')
                len++;
        }
        return String.valueOf(c).substring(0, len);
    }

    public static void main(String[] args) {
        try {
            getInput();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        p = 0;
        int row = 1;
        while (input[p] != '#') {
            scanner();
            switch (code) {
                case 57:
                    //常数
                    output.add(new Token(code, num + ""));
                    break;
                case -1:
                    //换行
                    row++;
                    break;
                case -2:
                    //整型变量溢出
                    output.add(new Token("integer overflow at row " + row));
                    break;
                case -3:
                    //未定义字符
                    output.add(new Token("undefined character at row " + row));
                    break;
                default:
                    //一般单词符号
                    output.add(new Token(code, typeConversion(word)));
                    break;
            }
        }
        try {
            output();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
