import java.util.*;
/**
 *  Tests the Graph class
 *
 *  @author Farida Sabry
 *  @version December 4th, 2015
 */
public class TestGraph {

	/** run test */
	public static void main(String[] args) throws Exception {
		Graph<Integer, Integer> graph = new Graph<Integer, Integer>();
		graph.addNode(8);
		graph.addNode(3);
		graph.addNode(2);
		graph.addNode(7);
		graph.addEdge(1, graph.getNode(0), graph.getNode(2));
		graph.addEdge(10, graph.getNode(2), graph.getNode(1));
		graph.addEdge(12, graph.getNode(3), graph.getNode(1));
		graph.addEdge(9, graph.getNode(3), graph.getNode(2));
		graph.addEdge(9, graph.getNode(3), graph.getNode(2));
		graph.addEdge(5, graph.getNode(1), graph.getNode(1));
		graph.removeNode(graph.getNode(0));
		graph.removeEdge(graph.getEdge(0));
		//graph.getEdgeRef(graph.getNode(0), graph.getNode(1));
		graph.print();
		System.out.println("Nodes=" + graph.numNodes() + ", Edges=" + graph.numEdges());
		graph.check();

		HashSet<Graph<Integer,Integer>.Edge> edges = new HashSet<Graph<Integer,Integer>.Edge>();
		edges.add(graph.getEdge(0)); 

		HashSet<Graph<Integer,Integer>.Node> nodes = graph.endpoints(edges);
		HashSet<Graph<Integer,Integer>.Node> otherNodes = graph.otherNodes(nodes);
		System.out.print("Endpoints: ");
		String delim = "";
		for (Graph.Node n: nodes) {
			System.out.print(delim + n.getData());
			delim = ",";
		}
		System.out.println();

		System.out.print("otherNodes: ");
		delim = "";
		for (Graph.Node n: otherNodes) {
			System.out.print(delim + n.getData());
			delim = ",";
		}
		System.out.println();

		graph.BFT(graph.getNode(0));
		graph.check();

	}
}