package SyntaxParser;

import java.util.ArrayList;

class Stack {

	private ArrayList<Token> stack;
	
	Stack(){
		stack = new ArrayList<Token>();
		stack.add(new Token(Token.END,"$"));
	}
	
	void push(Token t){
		stack.add(t);
	}
	
	void pop(){
		stack.remove(stack.size() - 1);
	}
	
	Token get(){
		return stack.get(stack.size() - 1);
	}
	
//	private void print(){
//		for(int i=stack.size()-1;i>=0;i--){
//			System.out.println(stack.get(i));
//		}
//		System.out.println();
//	}
	
}
