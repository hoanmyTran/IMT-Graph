package GraphAlgorithms;

import java.util.*;
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
		cpt = 0;
		visite = new int[n];
		debut = new int[n];
		fin = new int[n];
	}

	public static void dfs(DirectedNode start, Set<DirectedNode> a) {
		a.add(start);
		visite[start.getLabel()] = 1;
		debut[start.getLabel()] = cpt;
		cpt++;
		for (DirectedNode t : start.getSuccs().keySet())
			if(!a.contains(t)) {
				dfs(t, a);
			}
		fin[start.getLabel()] = cpt;
			cpt++;
		visite[start.getLabel()]= 2;
	}


	public static void bfs(DirectedNode start, Set<DirectedNode> a) {
		LinkedList<DirectedNode> queue = new LinkedList<>();
		queue.add(start);
		a.add(start);
		while (!queue.isEmpty()){
			DirectedNode current = queue.pop();
			debut[current.getLabel()]=cpt;
			current.getSuccs().keySet().forEach(elem->{
				if(!a.contains(elem)){
					queue.push(elem);
					a.add(elem);
				}
			});
			cpt++;
		}
		for (int i = 0 ; i<debut.length;i++){
			if(debut[i]!=0||i==start.getLabel())
			fin[i]=1+2*cpt-(debut[i]);
		}
	}


	public static void main(String[] args) {
		int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, true, 100001);
		GraphTools.afficherMatrix(Matrix);
		DirectedGraph<DirectedNode> al = new DirectedGraph<>(Matrix);
		System.out.println(al);
		GraphToolsList.initialize(al.getNbNodes());
		GraphToolsList.dfs(al.getNodeOfList(new DirectedNode(0)), new HashSet<>());
		System.out.println(Arrays.toString(debut));
		System.out.println(Arrays.toString(fin));
		GraphToolsList.initialize(al.getNbNodes());
		GraphToolsList.bfs(al.getNodeOfList(new DirectedNode(0)), new HashSet<>());
		System.out.println(Arrays.toString(debut));
		System.out.println(Arrays.toString(fin));
	}
}
