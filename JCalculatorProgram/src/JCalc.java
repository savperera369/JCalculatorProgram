// Infix to PostFix Converter Program
// along with a PostFix Interpreter
// Created by Saviru Perera - 260966369
// The code for the println statement
// at the start was taken from ECSE 202 Assignment 4 pdf

import acm.program.*;

public class JCalc extends ConsoleProgram {
	
	postFix a = new postFix(); // creates instance of postFix class
	
	public void run() {
		
		// Instructs user how to terminate program
		println("Infix to PostFix conversion, enter expression of blank line to exit.");
		
		// while loop runs until blank line entered
		while(true) {
			String infix = readLine("\nEnter an infix expression: "); // prompts user for input
			if (infix.equals("")) {
				println("Program Terminated"); 
				break; // program terminated if blank line entered
			}
			
			Queue pFix = a.In2Post(a.parse(infix)); // variable pFix of type Queue holds postFix expression in queue
			String display = pFix.toString(); // converts postFix queue to a string
			Queue ans = a.In2Post(a.parse(infix)); // variable a is identical to pFix, need this because the toString method empties the pFix queue
			// therefore ans maintains a copy of the postFix queue which can be evaluated
			double answer = a.PostEval(ans); // calculate answer from a postFix queue
			
			println("" + infix + " ==> " + display + "\n"); // displays pFix queue in a string
			println("" + display + " evaluates to " + answer); // displays answer to console
			
		}
		
	}
}
