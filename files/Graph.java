import java.util.*;
import java.lang.Exception.*;

/**
 *  Implements a undirected Graph
 *
 *  @author Farida Sabry
 *  @version December 2nd, 2015
 */
public class Graph<V,E> {
	/** reference list of nodes */
	protected ArrayList<Node> nodes;

	/** reference list of edges */
	protected ArrayList<Edge> edges;

	/** Constructor initializes with empty node and edge list */
	public Graph() {
		nodes = new ArrayList<Node>();
		edges = new ArrayList<Edge>();
	}

	/** Adds a node */
	public Node addNode(V data) {
		Node node = new Node(data);
		nodes.add(node);
		return node;
	}

	/** Adds an edge */
	public Edge addEdge(E data, Node head, Node tail) {
		Edge edge = new Edge(data, head, tail);
		if(!edges.contains(edge) && head != tail) {
			edges.add(edge);
			head.addEdgeRef(edge);
			tail.addEdgeRef(edge);
		}
		return edge;
	}

	/** Accessor for Node */
	public Node getNode(int i) {
		return nodes.get(i);
	}

	/** Accessor for Edge */
	public Edge getEdge(int i) {
		return edges.get(i);
	}

	/** Accessor for Specific Edge */
	public Edge getEdgeRef(Node head, Node tail) throws NoSuchElementException {
		Edge reference = head.edgeTo(tail);
		if (reference == null) {
			throw new NoSuchElementException("No such edge exists");
		}
		return reference;
	}

	/** Accessor for number of nodes */
	public int numNodes() {
		return nodes.size();
	}

	/** Accessor for number of edges */
	public int numEdges() {
		return edges.size();
	}

	/** Removes a node */
	public void removeNode(Node node) {
		while (!node.edge_list.isEmpty()) {
			Edge e = node.edge_list.get(node.edge_list.size()-1);
			removeEdge(e);
		}
		nodes.remove(node);
	}

	/** Removes an edge */
	public void removeEdge(Edge edge) {
		edge.getHead().removeEdgeRef(edge);
		edge.getTail().removeEdgeRef(edge);
		edges.remove(edge);
	}

	/** Removes an edge */
	public void removeEdge(Node head, Node tail) {
		Edge edge = head.edgeTo(tail);
		removeEdge(edge);
	}

	/** Returns nodes that are endpoints of a list of edges */
	public HashSet<Node> endpoints(HashSet<Edge> edges) {
		HashSet<Node> nodes = new HashSet<Node>();
		for (Edge e: edges) {
			nodes.add(e.getHead());
			nodes.add(e.getTail());
		}
		return nodes;
	}

	/** Returns nodes not on a given list */
	public HashSet<Node> otherNodes(HashSet<Node> group) {
		HashSet<Node> extraNodes = new HashSet<Node>();
		for (Node n: nodes) {
			if(!group.contains(n)) {
				extraNodes.add(n);
			}
		}
		return extraNodes;
	}

	/** Breadth-first traversal of graph */
	public HashSet<Edge> BFT(Node start) {
		HashSet<Node> visited = new HashSet<Node>();
		HashSet<Edge> edge_set = new HashSet<Edge>(); 
 		Queue<Node> queue = new LinkedList<Node>();

 		queue.add(start);
 		visited.add(start);
		while (!queue.isEmpty()) {
			Node currentNode = queue.remove();
			visited.add(currentNode);
			for (Node node: currentNode.getNeighbors()) {
				if (!visited.contains(node) && !queue.contains(node)) {
					queue.add(node);
					edge_set.add(node.edgeTo(currentNode));
				}
			}
		}
		return edge_set;
 	}

	/** DFT recursive helper method */
	public HashSet<Edge> traversal(ArrayList<Node> visited, HashSet<Edge> edge_set, Node node) {
		if (visited.contains(node)) {
			return null;
		} else {
			visited.add(node);
			for (Node n: node.getNeighbors()) {
				if (!visited.contains(n)) {
					edge_set.add(node.edgeTo(n));
					traversal(visited, edge_set, n);
				}	
			}
			return edge_set;
		}
 	}

	/** Depth-First traversal -- public interface */
	public HashSet<Edge> DFT(Node start) {
		ArrayList<Node> visited = new ArrayList<Node>();
		HashSet<Edge> edge_set = new HashSet<Edge>();
		edge_set = traversal(visited, edge_set, start);
		return edge_set;
	}

	/* Dijkstra's shortest-path algorithm to compute distances to nodes */
	public static<S extends DisplayNode,T extends DisplayEdge> ArrayList<Graph<S,T>.Edge> dijkstra(Graph<S,T> g, Graph<S,T>.Node start) {
	 	ArrayList<Graph<S,T>.Node> unvisited = new ArrayList<Graph<S,T>.Node>(g.nodes);
	 	ArrayList<Graph<S,T>.Edge> edge_list = new ArrayList<Graph<S,T>.Edge>();
	 	HashMap<Graph<S,T>.Node,Double> cost = new HashMap<Graph<S,T>.Node,Double>();
	 	HashMap<Graph<S,T>.Node,Graph<S,T>.Node> homePath = new HashMap<Graph<S,T>.Node,Graph<S,T>.Node>();
	 	Graph<S,T>.Node minNode = null;

	 	// initialize cost to infinity 
	 	for (Graph<S,T>.Node node: unvisited) {
	 		cost.put(node, Double.POSITIVE_INFINITY);
	 		homePath.put(node, null);
	 	}
	 	cost.put(start,0.0);

	 	// algorithm
	 	while (unvisited.size() > 0) {
	 		Double min = Double.POSITIVE_INFINITY;
	 		for (Graph<S,T>.Node node: unvisited) {
	 			if (cost.get(node) <= min) {
	 				min = cost.get(node);
	 				minNode = node;
	 			}
	 		}
			for (Graph<S,T>.Node neighbor: minNode.getNeighbors()) {
				Graph<S,T>.Edge edge = minNode.edgeTo(neighbor);
				Double newCost = cost.get(minNode) + (double) edge.getData().getWeight();
				if (newCost < cost.get(neighbor)) {
					cost.put(neighbor,newCost);
					homePath.put(neighbor,minNode);
					//edge_list.add(minNode.edgeTo(neighbor));
				}
			}
			unvisited.remove(minNode);
	 	}

	 	//add edges
	 	for (Map.Entry<Graph<S,T>.Node, Graph<S,T>.Node> entry : homePath.entrySet()) {
	 		if (entry.getValue() != null) {
	 			edge_list.add(entry.getKey().edgeTo(entry.getValue()));
	 		}
	 	}

	 	// print costs to all nodes
	 	for (Map.Entry<Graph<S,T>.Node, Double> entry : cost.entrySet()) {
            System.out.println(entry.getKey().getData().getLabel()+ ": " + entry.getValue());
        }
	 	return edge_list;
	}

          
	/** prints a representation of a graph */
	public void print() {
		for (Node n: nodes) {
			System.out.print(n.getData() + ":");
			ArrayList<E> edge_data = new ArrayList<E>();
			for (Edge e: n.edge_list) {
				edge_data.add(e.getData());
			}
			System.out.print(edge_data + "\n");
		}
	}

	/** checks consistency of the graph */
	public void check() throws Exception {
		boolean checker = true;
		String error = "";
		for (Edge e: edges) {
			Node head = e.getHead();
			Node tail = e.getTail();
			// head/tail node lists the edge on theirs edge list ?
			if (!head.isEdge(e) || !tail.isEdge(e)) {
				checker = false;
				error = "Head or tail node doesn't list edge in its edge list";
			}
			// head/tail appear in master node list ?
			else if (!nodes.contains(head) || !nodes.contains(tail)) {
				checker = false;
				error = "Head or tail doesn't appear in master node list";
			}
		}

		for (Node n: nodes) {
			for (Edge e: n.edge_list) {
				// edge in master edge list ?
				if (!edges.contains(e)) {
					checker = false;
					error = "Edge doesn't appear in master edge list";				
				}
				// edge's head/tail links back to node ?
				else if (!e.getHead().equals(n) && !e.getTail().equals(n)) {
					checker = false;
					error = "Neither the edge's head nor tail link back to node";
				}
			}
		}
		if (!checker) {
			throw new Exception("Graph is inconsistent: " + error);
		}
	}

	/** Class Node represents an Node */
	public class Node {
		/** The value at this node */
		private V data;

		/** list of edges that are connected to this node */
		private ArrayList<Edge> edge_list;

		/** Constructor creates a disconnected node */
		public Node(V data) {
			this.data = data;
			edge_list = new ArrayList<Edge>();
		}

		/** Accessor for data */
		public V getData() {
			return data;
		}

		/** Manipulator for data */
		public void setData(V data) {
			this.data = data;
		}

		/** Adds an edge to the edge list */
		protected void addEdgeRef(Edge edge) {
			edge_list.add(edge);
		}

		/** Removes an edge from the edge list */
		protected void removeEdgeRef(Edge edge) {
			edge_list.remove(edge);
		}

		/**  Returns the edge to a specified node, or null if there is none */
		public Edge edgeTo(Node neighbor) {
			Edge edge = null;
			for (Edge e: edge_list) {
				if(neighbor.edge_list.contains(e)) {
					edge = e;
				}
			}
			return edge;
		}

		/** Returns a list of neighbors */
		public ArrayList<Node> getNeighbors() {
			ArrayList<Node> neighbors = new ArrayList<Node>();
			for (Edge e: edge_list)	{
				neighbors.add(e.oppositeTo(this));
			}
			return neighbors;
		}

		/** Returns true if there's an edge to that node */
		public boolean isNeighbor(Node node) {
			boolean result = false;
			for (Edge e: edge_list) {
				if (e.oppositeTo(node) == this) {
					result = true;
				}
			}
			//return getNeighbors().contains(node);
			return result;
		}

		/** checks if a node has edge(s) */
		private boolean isConnected() {
			return (edge_list.size() != 0);
		}

		/** checks if given edge is in the list of edges */
		private boolean isEdge(Edge edge) {
			return (edge_list.contains(edge));
		}
	}

	/** Class Edge represents an Edge */
	public class Edge {
		/** The weight given to the edge */
		private E data;

		/** Head Node */
		private Node head;

		/** Tail Node */
		private Node tail;

		/** Constructor for Edge */
		public Edge(E data, Node head, Node tail) {
			this.data = data;
			this.head = head;
			this.tail = tail;
		}

		/** Accessor for Data */
		public E getData() {
			return data;
		}

		/** Accessor for Head Node */
		public Node getHead() {
			return head;
		}

		/** Accessor for Tail Node */
		public Node getTail() {
			return tail;
		}

		/** Manipulator for Data */
		public void setData(E data) {
			this.data = data;
		}

		/** Accessor for opposite Node */
		public Node oppositeTo(Node node) {
			Node opposite;
			if(node.equals(head)) {
				opposite = tail;
			} else {
				opposite = head;
			}
			return opposite;
		}

		/** Redefined hashcode to match redefined equals */
		public int hashCode() {
			return head.hashCode() + tail.hashCode();
		}

		/* Two edges are equal if they connect the same endpoints */
		public boolean equals(Object obj) {
			boolean result = false;
			if (obj != null && obj.getClass() == getClass()) {
				@SuppressWarnings("unchecked")
				Edge e = (Edge) obj;
				if ((head == e.head && tail == e.tail) || (head == e.tail && tail == e.head)) {
					result = true;
				}
			} 
			return result;
		}
	}
}