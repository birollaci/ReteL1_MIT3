package hu.bme.mit.yakindu.analysis.workhere;

import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.yakindu.sct.model.sgraph.State;

import hu.bme.mit.yakindu.analysis.RuntimeService;
 import hu.bme.mit.yakindu.analysis.TimerService;
import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;



public class RunStatechart {
	
	public static void main(String[] args) throws IOException {
		ExampleStatemachine s = new ExampleStatemachine();
		s.setTimer(new TimerService());
		RuntimeService.getInstance().registerStatemachine(s, 200);
		s.init();
		s.enter();
		s.runCycle();
		print(s);
		s.raiseStart();
		s.runCycle();
		System.in.read();
		s.raiseWhite();
		s.runCycle();
		print(s);
		// 3.5
		alkalmazas(s);
		System.exit(0);
	}

	public static void print(IExampleStatemachine s) {
		System.out.println("W = " + s.getSCInterface().getWhiteTime());
		System.out.println("B = " + s.getSCInterface().getBlackTime());
	}
	
	// 3.5
	public static void alkalmazas(ExampleStatemachine s) {
		Scanner sc= new Scanner(System.in);
		String str = "";
		while(!str.equals("exit")) {
			Boolean ellenor = false;
			System.out.print("Enter a string: ");  
			str = sc.nextLine();
			if(str.equals("start")) {
				ellenor = true;
				s.raiseStart();
				s.runCycle();
			}
			if(str.equals("white")) {
				ellenor = true;
				s.raiseWhite();
				s.runCycle();
			}
			if(str.equals("black")) {
				ellenor = true;
				s.raiseBlack();
				s.runCycle();
			}
			
//			if(ellenor) {
//				System.out.println(str + " elindult");
//			}
			
			print(s);
			
		}
	}
}
