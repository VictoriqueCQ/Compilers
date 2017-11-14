package SyntaxParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

class Analyzer {

	private static char input[] = new char[500];// 存储输入的字符数组
	private static ArrayList<Token> output = new ArrayList<>();//输出
	private static char word[];// 单词符号
	private static int num;// 常数
	private static int code;// 单词种别码
	private static int pointer;
	// 以下是可识别的单词符号
	private static String[] reservedWords = {
			"class",
			"public",
			"new",
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
			"return",};


	private static void getInput() throws IOException {
		String inputFile = "Lab2_Resources/input.txt";
		BufferedReader br2 = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(inputFile))));
		String line;
		char[] tmp;
		pointer = 0;
		while ((line = br2.readLine()) != null) {
			tmp = line.toCharArray();
			for (int i = 0; i < tmp.length; i++) {
				if (tmp[i] == ' '|| tmp[i]=='\t')
					continue;
				input[pointer++] = tmp[i];
			}
			input[pointer++] = '\n';
		}
		input[pointer] = '#';
		br2.close();
	}
	
	private static void scanner() {
		// 当前读的字符
		char ch;
		int localPointer;
		word = new char[20];
		ch = input[pointer++];

		if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
			// 可能是保留字或变量名（保留字优先）
			localPointer = 0;
			while ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z')
					|| (ch >= 'A' && ch <= 'Z')) {
				word[localPointer++] = ch;
				ch = input[pointer++];
				word[localPointer] = '\0';
				for (int i = 0; i < reservedWords.length; i++) {
					if (ch2s(word).equals(reservedWords[i])) {
						code = i + 1;
						pointer--;
						return;
					}
				}
			}
			word[localPointer++] = '\0';
			pointer--;
			// 放回多读的
			code = 56;

		} else if (ch >= '0' && ch <= '9') {
			// 可能是正常数
			num = 0;
			while (ch >= '0' && ch <= '9') {
				num = num * 10 + ch - '0';
				ch = input[pointer++];
			}
			pointer--;
			code = 57;
			if (num < 0)
				code = -2;
			// 正数超过最大值变成负数，报错
		} else {
			// 其他字符
			localPointer = 0;
			word[localPointer++] = ch;
			switch (ch) {
			case '+':
				ch = input[pointer++];
				if (ch == '=') {
					//符号为+=
					code = 23;
					word[localPointer++] = ch;
				} else {
					//符号为+
					code = 22;
					pointer--;
				}
				break;
			case '-':
				ch = input[pointer++];
				if (ch >= '0' && ch <= '9') {
					//可能是负常数
					num = 0;
					while (ch >= '0' && ch <= '9') {
						num = num * 10 + ch - '0';
						ch = input[pointer++];
					}
					pointer--;
					code = 57;
					if (num < 0)
						code = -2;
					num *= -1;
					// 变成负数
				} else if (ch == '=') {
					//符号为-=
					code = 25;
					word[localPointer++] = ch;
				} else {
					//符号为-
					code = 24;
					pointer--;
				}
				break;
			case '*':
				ch = input[pointer++];
				if (ch == '=') {
					//符号为*=
					code = 27;
					word[localPointer++] = ch;
				} else if (ch == '/') {
					//符号为*/
					code = 44;
					word[localPointer++] = ch;
				} else {
					//符号为*
					code = 26;
					pointer--;
				}
				break;
			case '/':
				ch = input[pointer++];
				if (ch == '=') {
					//符号为/=
					code = 29;
					word[localPointer++] = ch;
				} else if (ch == '/') {
					//符号为//
					code = 42;
					word[localPointer++] = ch;
				} else if (ch == '*') {
					//符号为/*
					code = 26;
					word[localPointer++] = ch;
				} else {
					//符号为/
					code = 28;
					pointer--;
				}
				break;
			case '=':
				ch = input[pointer++];
				if (ch == '=') {
					//符号为==
					code = 31;
					word[localPointer++] = ch;
				} else {
					//符号为=
					code = 30;
					pointer--;
				}
				break;
			case '<':
				ch = input[pointer++];
				if (ch == '=') {
					//符号为<=
					code = 39;
					word[localPointer++] = ch;
				} else {
					//符号为<
					code = 38;
					pointer--;
				}
				break;
			case '>':
				ch = input[pointer++];
				if (ch == '=') {
					//符号为>=
					code = 41;
					word[localPointer++] = ch;
				} else {
					//符号为>
					code = 40;
					pointer--;
				}
				break;
			case '&':
				ch = input[pointer++];
				if (ch == '&') {
					//符号为&&
					code = 33;
					word[localPointer++] = ch;
				} else {
					//符号为&
					code = 32;
					pointer--;
				}
				break;
			case '|':
				ch = input[pointer++];
				if (ch == '|') {
					//符号为||
					code = 35;
					word[localPointer++] = ch;
				} else {
					//符号为|
					code = 34;
					pointer--;
				}
				break;
			case '!':
				ch = input[pointer++];
				if (ch == '=') {
					//符号为!=
					code = 37;
					word[localPointer++] = ch;
				} else {
					//符号为!
					code = 36;
					pointer--;
				}
				break;

			case '(':code = 45; break;
			case ')':code = 46; break;
			case '[':code = 47; break;
			case ']':code = 48; break;
			case '{':code = 49; break;
			case '}':code = 50; break;
			case ',':code = 51; break;
			case ':':code = 52; break;
			case ';':code = 53; break;
			case '\'':code = 54; break;
			case '\"':code = 55; break;
			case '\n':code = -1; break;
			default:code = -3; break;
			}
		}
	}

	private static String ch2s(char[] c) {
		int len = 0;
		for (int i = 0; i < c.length; i++) {
			if (c[i] != '\0')
				len++;
		}
		return String.valueOf(c).substring(0, len);
	}

	static ArrayList<Token> getTokens(){
		try {
			getInput();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		pointer = 0;
		int row = 1;
		do {
			scanner();
			switch (code) {
			case 57:
				//常数
				output.add(new Token(code, num+""));
				break;
			case -1:
				//换行
				row++;
				break;
			case -2:
				//整型过大
				output.add(new Token("integer overflow at row "+row));
				break;
			case -3:
				//未定义字符
				output.add(new Token("undefined character at row "+row));
				break;
			default:
				//一般单词符号
				output.add(new Token(code,ch2s(word)));
				break;
			}
		} while (input[pointer] != '#');
		return output;
	}

}
