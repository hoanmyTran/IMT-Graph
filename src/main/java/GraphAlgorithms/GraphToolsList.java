package GraphAlgorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import AdjacencyList.DirectedGraph;
import AdjacencyList.DirectedValuedGraph;
import AdjacencyList.UndirectedValuedGraph;
import Collection.Triple;
import Nodes.AbstractNode;
import Nodes.DirectedNode;
import Nodes.UndirectedNode;

public class GraphToolsList  extends GraphTools {

	private static int _DEBBUG =0;

	private static int[] visite;
	private static int[] debut;
	private static int[] fin;
	private static List<Integer> order_CC;
	private static int cpt=0;

	//--------------------------------------------------
	// 				Constructors
	//--------------------------------------------------

	public GraphToolsList(){
		super();
	}

	// ------------------------------------------
	// 				Accessors
	// ------------------------------------------



	// ------------------------------------------
	// 				Methods
	// ------------------------------------------


	public static void initialize(int n) {
		visite = new int[n];
		debut = new int[n];
		fin = new int[n];
	}

	public static void visitNode(DirectedNode n, Set<DirectedNode> a) {
		a.add(n);
		visite[n.getLabel()] = 1;
		cpt++;
		debut[n.getLabel()] = cpt;
		for (DirectedNode t : n.getSuccs().keySet())
			if(!a.contains(t)) {
				visitNode(t, a);
			}
		fin[n.getLabel()] = cpt;
		visite[n.getLabel()]= 2;
	}

	public static void dfs(DirectedNode start, Set<DirectedNode> a) {
		for(DirectedNode t : start.getSuccs().keySet()) {
			if(visite[t.getLabel()] == 0) {
				visitNode(t, a);
			}
		}
		for(int i=0; i < visite.length; i++) {
			System.out.println(visite[i]);
		}
	}

	// A completer


	public static void main(String[] args) {
		int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, true, 100001);
		GraphTools.afficherMatrix(Matrix);
		DirectedGraph<DirectedNode> al = new DirectedGraph<>(Matrix);
		System.out.println(al);
		GraphToolsList.initialize(al.getNbNodes());
		GraphToolsList.dfs(al.getNodeOfList(new DirectedNode(0)), new HashSet<>());
		int[] array = GraphToolsList.visite;
		// A completer
	}
}
