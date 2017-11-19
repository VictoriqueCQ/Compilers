package SyntaxParser;

public class Token {

    static final int CLASS = 1;
    static final int PUBLIC = 2;
    static final int PROTECTED = 3;
    static final int PRIVATE = 4;
    static final int VOID = 5;
    static final int STATIC = 6;
    static final int INT = 7;
    static final int CHAR = 8;
    static final int FLOAT = 9;
    static final int DOUBLE = 10;
    static final int STRING = 11;
    static final int IF = 12;
    static final int ELSE = 13;
    static final int DO = 14;
    static final int WHILE = 15;
    static final int TRY = 16;
    static final int CATCH = 17;
    static final int SWITCH = 18;
    static final int CASE = 19;
    static final int FOR = 20;
    static final int ADD = 22;
    static final int ADD_EQUAL = 23;
    static final int MIN = 24;
    static final int MIN_EQUAL = 25;
    static final int MUL = 26;
    static final int MUL_EQUAL = 27;
    static final int DIV = 28;
    static final int DIV_EQUAL = 29;
    static final int EQUAL = 30;
    static final int DOUBLE_EQUAL = 31;
    static final int AND = 32;
    static final int DOUBLE_AND = 33;
    static final int OR = 34;
    static final int DOUBLE_OR = 35;
    static final int NOT = 36;
    static final int NOT_EQUAL = 37;
    static final int LESS = 38;
    static final int LESS_EQUAL = 39;
    static final int GREATER = 40;
    static final int GREATER_EQUAL = 41;
    static final int DOUBLE_SLASH = 42;
    static final int SLASH_STAR = 43;
    static final int STAR_SLASH = 44;
    static final int LEFT_PARENTHESE = 45;
    static final int RIGHT_PARENTHESE = 46;
    static final int LEFT_BRACKET = 47;
    static final int RIGHT_BRACKET = 48;
    static final int LEFT_BRACE = 49;
    static final int RIGHT_BRACE = 50;
    static final int COMMA = 51;
    static final int COLON = 52;
    static final int SEMICOLON = 53;
    static final int SINGLE_QUOTE = 54;
    static final int DOUBLE_QUOTE = 55;
    static final int ID = 56;
    static final int NUMBER = 57;
    static final int END = 0;
    static final int ERROR = -1;

    static final int S = 100;
    static final int E = 101;
    static final int E1 = 102;
    static final int T = 103;
    static final int T1 = 104;
    static final int F = 105;
    static final int C = 106;
    static final int C1 = 107;
    static final int D = 108;

    private int code;
    private String str;
    private String error;

    Token(int c, String s) {
        this.code = c;
        this.str = s;
        this.error = null;
    }

    Token(String error) {
        this.error = error;
        this.code = ERROR;
    }

    public String toString() {
        if (this.error != null)
            return "Error:" + this.error;
        return "<" + this.code + "," + this.str + ">";
    }

//	String getStr(){
//		return str;
//	}

    int getCode() {
        return code;
    }

}
