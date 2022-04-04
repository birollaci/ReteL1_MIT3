package hu.bme.mit.yakindu.analysis.workhere;

import java.util.Vector;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.Transition;
import org.yakindu.sct.model.sgraph.Vertex;

import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;

public class Main {
	@Test
	public void test() {
		main(new String[0]);
	}
	
	public static void main(String[] args) {
		ModelManager manager = new ModelManager();
		Model2GML model2gml = new Model2GML();
		
		// Loading model
		EObject root = manager.loadModel("model_input/example.sct");
		
		// Reading model
		Statechart s = (Statechart) root;
		TreeIterator<EObject> iterator = s.eAllContents();
		Vector<String> nameVector = new Vector<String>();
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof State) {
				State state = (State) content;
				System.out.println(state.getName());
				nameVector.add(state.getName());
			}
			
		}
		// 2.3
		System.out.println();
		System.out.println("2.3");
		iterator = s.eAllContents();
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof Transition) {
				Transition transition = (Transition) content;
				System.out.println(transition.getSource().getName() + " -> " + transition.getTarget().getName());
			}
		}
		// 2.4
		System.out.println();
		System.out.println("2.4");
		System.out.println("Csapdaallapotok: ");
		iterator = s.eAllContents();
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof State) {
				State state = (State) content;
				EList<Transition> outTransitions = state.getOutgoingTransitions();
				if(outTransitions.size() == 0) {
					System.out.println(state.getName());
				}
			}
		}
		
		// 2.5 + 2.6
		System.out.println();
		System.out.println("2.5 + 2.6");
		System.out.println("Uj nevek: ");
		iterator = s.eAllContents();
		int i = 0;
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof State) {
				State state = (State) content;
				if(state.getName() == null) { //state.getName().isEmpty()
					while(nameVector.contains("test"+i)) {
						i++;
					}
					String name = "test"+i;
					state.setName(name);
					System.out.println(name);
					i++;
				}
			}
		}
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
}
