package SyntaxParser;

import java.util.ArrayList;

class Queue {

	private ArrayList<Token> line;
	
	Queue(ArrayList<Token> list){
		this.line = list;
		this.line.add(new Token(Token.END,"$"));
	}
	
	Token get(){
		return line.get(0);
	}
	
	void dequeue(){
		line.remove(0);
	}
	
//	public void enqueue(Token token){
//		this.line.add(token);
//	}
	
}
