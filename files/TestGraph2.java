import java.util.*;
/**
 *  Tests the Graph class
 *
 *  @author Farida Sabry
 *  @version December 4th, 2015
 */
public class TestGraph2 {

	/** run test */
	public static void main(String[] args) throws Exception {
		Graph<Character, Integer> graph = new Graph<Character, Integer>();
		graph.addNode('A');
		graph.addNode('B');
		graph.addNode('C');
		graph.addNode('D');
		graph.addNode('E');
		graph.addNode('F');
		graph.addNode('G');
		graph.addNode('H');
		graph.addEdge(5, graph.getNode(0), graph.getNode(1));
		graph.addEdge(9, graph.getNode(0), graph.getNode(2));
		graph.addEdge(1, graph.getNode(1), graph.getNode(3));
		graph.addEdge(1, graph.getNode(3), graph.getNode(2));
		graph.addEdge(3, graph.getNode(1), graph.getNode(4));
		graph.addEdge(2, graph.getNode(4), graph.getNode(5));
		graph.addEdge(2, graph.getNode(2), graph.getNode(5));
		graph.addEdge(4, graph.getNode(4), graph.getNode(6));
		graph.addEdge(4, graph.getNode(5), graph.getNode(6));

		graph.print();
		graph.check();

		HashSet<Graph<Character,Integer>.Edge> edges = graph.BFT(graph.getNode(0));
		edges = graph.DFT(graph.getNode(0));
		
	}
} //end of TestGraph2