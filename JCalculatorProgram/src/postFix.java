// This class implements the Shunting Yard Algorithm
// given in the pdf provided with this assignment
// java.util is imported to use String Tokenizer class
// Some of the code for the postFix interpreter(postEval method) was taken from the ECSE Assignment 4 
// pdf in myCourses

import java.util.*;

public class postFix {
	
	/**
	 * The following method takes a String and returns a Queue
	 * @param arg is a String, broken into tokens
	 * @return The tokens are returned in a Queue
	 */
	public Queue parse(String arg) {
		Queue q = new Queue(); // creates an empty queue
		StringTokenizer str = new StringTokenizer(arg, "()%^*/+- ", true); // Tokenizer breaks infix string into tokens
		while(str.hasMoreTokens()) { // while loop runs as long as there are tokens
			String token = str.nextToken();
			if (!token.equals(" ")) { // this check is made because the space is a default delimiter
				q.enqueue(token); // token is added to the "infix" queue
			}
		}
		return q; // the "infix" queue is returned
	}
	
	/**
	 * The following method takes a Queue in infix form and returns a Queue in postFix form
	 * @param Qin This is a queue containing tokens in an infix form
	 * @return The method returns a Queue with tokens in postFix form
	 */
	public Queue In2Post (Queue Qin) {
		
		// creates an empty results queue and an empty operators stack
		// error queue is returned if there are mismatched parentheses
		Queue results = new Queue();
		Stack operators = new Stack();
		Queue error = new Queue();
		error.enqueue("Error!");
		error.enqueue("Mismatched");
		error.enqueue("Parentheses.");
		
		// while loop runs as long as infix queue is not empty
		while (true) {
			String numOp = Qin.dequeue();
			if (numOp==null) break; // loop breaks if queue is empty
			if (isNumber(numOp)) {
				results.enqueue(numOp); // if string is a number, add it to the rear of the results queue
			} else if (isOperator(numOp)) {
				while(operators.top!=null && precedence(operators.top.data)>=precedence(numOp)) {
					String s = operators.pop(); // if string is an operator, and the string at the top of the operator stack has greater precedence,
					results.enqueue(s); // pop operators from the operator stack and onto the results queue
				}
				operators.push(numOp); // push the operator to the top of the stack if its of lower precedence than the operator currently at the top of the stack
			} else if (numOp.equals("(")) {
				operators.push(numOp); // push the string to the top of the operator stack if it is a left parenthesis
			} else if (numOp.equals(")")) {
				while(!operators.top.data.equals("(")) {
					String popOp = operators.pop(); // as long as the operator on the top of the stack is not a left parenthesis,
					results.enqueue(popOp); // pop the operator from the top of the stack onto the results queue
					if (operators.top==null) return error; // if no left parentheses in stack return error queue because there are mismatched parentheses
				}
				if((operators.top.data).equals("(")) {
					operators.pop(); // once the operator at the top of the queue is a left parenthesis, remove it from the stack
				}
			}
		}
		
		while(operators.top!=null) {
			if(operators.top.data.equals("(")) return error; // if there are parentheses on stack return error queue
			String remainOp = operators.pop(); // pop the remaining operators from the operator stack  onto the results queue
			results.enqueue(remainOp);
		}
	
		return results; // return the results queue
	}
	
	/**
	 * This method is a postFix operator, and calculates an answer given an expression in postFix form
	 * @param PostFix takes Queue in postFix as input
	 * @return the method the final answer as a double
	 */
	public double PostEval (Queue PostFix) {
		Stack intermediate = new Stack(); // create stack to hold intermediate and final answers
		
		while(PostFix.front!=null) { // while loop runs as long as queue is non empty
			Double result = 0.0; // set initial value of answer to zero
			String token = PostFix.dequeue();
			Double A,B;
			if(isNumber(token)) {
				intermediate.push(token); // push token onto stack if it is an operand
			} else { // else if the token is an operator, pop 2 operands off the intermediate/operand stack
				
				A = Double.parseDouble(intermediate.pop());
				B = Double.parseDouble(intermediate.pop());
				
				switch (token) { // depending on what kind of operator the token is, the operation is different
				
					case "+" :
						result = B + A; // if operator is +, add the operands
						break;
					case "-" :
						result = B - A; // if operator is -, subtract the operands
						break;
					case "*" :
						result = B * A; // if operator is *, multiply the operands
						break;
					case "/" :
						result = B/A; // if operator is /, divide the operands
						break;
					case "^" :
						result = Math.pow(B, A); // if operator is exponent, perform power operation
						break;
				}
				intermediate.push(Double.toString(result)); // push the intermediate result onto the stack
			}
		}
		
		double result = Double.parseDouble(intermediate.pop()); // the last remaining item on the stack is 
		return result; // the final answer, pop it and return it
	}
	
	/**
	 * This private method is a helper to determine the precedence of an operator
	 */
	private int precedence(String value) {
		int i=0;
		if(value.equals("^")) {
			i=4; // exponent operator has the highest precedence of 4
		}
		else if (value.equals("%")||value.equals("/")||value.equals("*")) {
			i=3; // modulo, division, and multiplication operators have next highest precedence, each has precedence 3
		}
		else if (value.equals("+")||value.equals("-")) {
			i=2; // addition, subtraction operators have lowest precedence, each has precedence 2
		}
		return i; // return the precedence of the operator as an integer
	}
	
	/**
	 * This method returns whether or not a string is one of the 6 operators
	 * @param op This is a string being check
	 * @return a boolean value which returns true if the string is an operator
	 */
	private boolean isOperator(String op) {
		return (op.equals("^"))||op.equals("%")||op.equals("/")||op.equals("*")||op.equals("+")||op.equals("-");
	}
	
	/**
	 * This method returns whether or not a string represents a number
	 * @param num This is a string being checked
	 * @return a boolean value which returns true if the String is a number
	 */
	private boolean isNumber(String num) {
		int sum = 0; // set sum counter equal to 0, this sum variable will calculate how many characters in the string are numbers
		for (int i=0; i<num.length(); i++) { // for loop loops through the length of the string
			char ch = num.charAt(i);
			if (Character.isDigit(ch)||ch=='.') {
				sum++; // sum counter increments by one if the character at each position in the string is a number or decimal
			} 
		}
		return sum==num.length(); // the string is a number if the sum is equivalent to the length of the string
	}
}
