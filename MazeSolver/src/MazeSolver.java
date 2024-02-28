import java.util.ArrayList;
/**
 * <pre>
 * Name: Rui Meng
 * Mrs. Kankelborg
 * Period 1
 * Project 4 MazeSolver
 * Last Revised on: 2/26/2024 
 * </pre>
 */

/**
 * Solves mazes. Please refer to the specification for instructions on how to solve mazes.
 */
public class MazeSolver
{
	/**
	 * Node class helps with solution path, each Node holds information of its position and a
	 * reference to the Node before it in the path and use a chain of Nodes to represent the
	 * full path.
	 */
    class Node {
    	
    	private Cell cell;//Cell object representing current position
    	private Node previousNode;// Reference to the previous Node in the path.
    	
    	/**
         * Constructor for Node when creating the first step in the path, where there is no previous Node.
         * 
         * @param cell The current position in the maze.
         * @param previous Reference to the previous Node in the path.
         */
    	public Node(Cell cell, Node previous) {
    		this.cell = cell;
    		this.previousNode = previous;
    	}
    	
    	/**
         * Return the Cell object representing this Node's position in the maze.
         * 
         * @return The Cell object associated with this Node.
         */
    	public Cell getCell() {
    		return cell;
    	}
    	
    	/**
         * Return the previous Node in the path.
         * 
         * @return The previous Node in the path.
         */
    	public Node getPreviousNode() {
    		return previousNode;
    	}	
    }

    // Directions array for BFS.
    private int[] DIR = new int[] {-1, 0, 1, 0, -1};
    private Direction[] DRS = new Direction[] {Direction.LEFT, Direction.UP,
    		Direction.RIGHT, Direction.DOWN};
    
    /**
     * Provides a solution for a given maze, if possible. A solution is a path from the start cell
     * to the finish cell (inclusive). If there is no solution to the maze then returns the static
     * instance {@link Path#NO_PATH}. If the maze is perfect then there must be only one solution.
     *
     * @param maze the maze to solve
     * @return a solution for the maze or {@link Path#NO_PATH} if there is no solution
     */
    
    public Path solve(Maze maze)
    {
    	// Initialize the queue with the starting node.
        Queue<Node> queue = new Queue<>();
        queue.enqueue(new Node(maze.getStart(), null));
        
        // Continue searching until the queue is empty.
        while(!queue.isEmpty()) {
        	Node node = queue.dequeue();// Remove the head of the queue.
        	Cell cell = node.getCell();// Get the current cell of this node.

            // Mark the cell as visited to avoid revisiting it.
        	maze.visit(cell.getX(), cell.getY());
        	
        	// If the current cell is the end cell, construct the path from end to start.
        	if (cell.equals(maze.getEnd())) {
        		return constructPath(node);
        	}
        	
        	// Explore neighboring cells in all four directions.
        	for (int i = 0; i < 4; i++) {
        		int x = cell.getX() + DIR[i];// Calculate the x-coordinate of the neighbor.               
        		int y = cell.getY() + DIR[i + 1];// Calculate the y-coordinate of the neighbor.
        		
        		// Check if the neighbor is within the bounds of the maze, hasn't been visited,
        		// and is open.
                if (0 <= x && x < maze.size() && 0 <= y && y < maze.size() 
        				&& !maze.isVisited(x, y) && maze.isOpen(cell.getX(), cell.getY(),
        						DRS[i]))
                	// If valid, add the neighbor as a new Node to the queue.
        			queue.enqueue(new Node(new Cell(x, y), node));
        	}

        }
        
        return Path.NO_PATH;
    }

	/**
	 * Constructs the path from the end of the maze back to the start by following the chain of previous Nodes.
	 * The path is built in reverse order by starting with the end node and prepending each cell to the path
	 * until the start node is reached.
	 *
	 * @param node The end node of the path.
	 * @return The constructed path from start to end.
	 */
    private Path constructPath(Node node) {
    	Path result = new Path();
    	while (node != null) {
    		// Add the current cell to the front of the path.
            result.addFirst(node.getCell());
            
            // Move to the previous node in the path.
            node = node.getPreviousNode();
    	}
    	
    	return result;
    }
    
    /**
     * Creates, solves, and draws a sample maze. Try solving mazes with different sizes!
     *
     * @param args unused
     */
    public static void main(String[] args)
    {
        // First, generate a new maze.
        int size = 10; // Setting above 200 is not recommended!
        MazeGenerator generator = new MazeGenerator();
        Maze maze = generator.generate(size);
        maze.freeze();

        // Next, solve it!
        MazeSolver solver = new MazeSolver();
        maze.resetVisited();
        Path solutionPath = solver.solve(maze);
        maze.setSolution(solutionPath);

        // This is so we can see which cells were explored and in what order.
        maze.setDrawVisited(true);

        maze.draw();
    }
}
