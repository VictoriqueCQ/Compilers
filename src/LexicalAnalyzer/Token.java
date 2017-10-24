package LexicalAnalyzer;

public class Token {
    private int code;
    private String str;
    private String error;

    public Token(int code, String str) {
        this.code = code;
        this.str = str;
        this.error = null;
    }

    public Token(String error) {
        this.error = error;
    }

    public String toString() {
        if (this.error != null) {
            return "Error:" + this.error;
        }
        return "<" + this.code + "," + this.str + ">";
    }
}
