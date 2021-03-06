package AdjacencyList;

import java.util.ArrayList;

import Abstraction.AbstractListGraph;
import Abstraction.IDirectedGraph;
import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;

public class DirectedGraph<A extends DirectedNode> extends AbstractListGraph<A> implements IDirectedGraph<A> {

	private static int _DEBBUG = 0;

	// --------------------------------------------------
	// Constructors
	// --------------------------------------------------

	public DirectedGraph() {
		super();
		this.nodes = new ArrayList<A>();
	}

	public DirectedGraph(int[][] matrix) {
		this.order = matrix.length;
		this.nodes = new ArrayList<A>();
		for (int i = 0; i < this.order; i++) {
			this.nodes.add(i, this.makeNode(i));
		}
		for (A n : this.getNodes()) {
			for (int j = 0; j < matrix[n.getLabel()].length; j++) {
				A nn = this.getNodes().get(j);
				if (matrix[n.getLabel()][j] != 0) {
					n.getSuccs().put(nn, 0);
					nn.getPreds().put(n, 0);
					this.m++;
				}
			}
		}
	}

	public DirectedGraph(DirectedGraph<A> g) {
		super();
		this.nodes = new ArrayList<>();
		this.order = g.getNbNodes();
		this.m = g.getNbArcs();
		for (A n : g.getNodes()) {
			this.nodes.add(makeNode(n.getLabel()));
		}
		for (A n : g.getNodes()) {
			A nn = this.getNodes().get(n.getLabel());
			for (DirectedNode sn : n.getSuccs().keySet()) {
				DirectedNode snn = this.getNodes().get(sn.getLabel());
				nn.getSuccs().put(snn, 0);
				snn.getPreds().put(nn, 0);
			}
		}

	}

	// ------------------------------------------
	// Accessors
	// ------------------------------------------

	@Override
	public int getNbArcs() {
		return this.m;
	}

	@Override
	public boolean isArc(A from, A to) {
		return getNodeOfList(from).getSuccs().containsKey(getNodeOfList(to));
	}

	@Override
	public void removeArc(A from, A to) {
		if (isArc(from, to)) {
			getNodeOfList(from).getSuccs().remove(getNodeOfList(to));
		}
	}

	@Override
	public void addArc(A from, A to) {
		if (!isArc(from, to)) {
			getNodeOfList(from).getSuccs().put(to, 0);
		}
	}

	// --------------------------------------------------
	// Methods
	// --------------------------------------------------

	/**
	 * Method to generify node creation
	 * 
	 * @param label of a node
	 * @return a node typed by A extends DirectedNode
	 */
	@Override
	public A makeNode(int label) {
		return (A) new DirectedNode(label);
	}

	/**
	 * @return the corresponding nodes in the list this.nodes
	 */
	public A getNodeOfList(A src) {
		return this.getNodes().get(src.getLabel());
	}

	/**
	 * @return the adjacency matrix representation int[][] of the graph
	 */
	@Override
	public int[][] toAdjacencyMatrix() {
		int[][] matrix = new int[this.order][this.order];
		for (int i = 0; i < this.order; i++) {
			for (DirectedNode j : this.nodes.get(i).getSuccs().keySet()) {
				int IndSucc = j.getLabel();
				matrix[i][IndSucc] = 1;
			}
		}
		return matrix;
	}

	@Override
	public IDirectedGraph computeInverse() {
		DirectedGraph<A> g = new DirectedGraph<>(this);
		for (A n : this.nodes) {
			for (DirectedNode sn : n.getSuccs().keySet()) {
				A snToA = makeNode(sn.getLabel());
				g.addArc(g.getNodeOfList(snToA), g.getNodeOfList(n));
				g.removeArc(g.getNodeOfList(n), g.getNodeOfList(snToA));
			}
		}
		return g;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (DirectedNode n : this.nodes) {
			s.append("successors of ").append(n).append(" : ");
			for (DirectedNode sn : n.getSuccs().keySet()) {
				s.append(sn).append(" ");
			}
			s.append("\n");
		}
		s.append("\n");
		return s.toString();
	}

	public static void main(String[] args) {
		int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
		GraphTools.afficherMatrix(Matrix);
		DirectedGraph al = new DirectedGraph(Matrix);
		System.out.println(al);
        al.removeArc(new DirectedNode(0), new DirectedNode(3));
        System.out.println(al);
        al.addArc(new DirectedNode(0), new DirectedNode(3));
        System.out.println(al);
        System.out.println(al.computeInverse());
	}
}
