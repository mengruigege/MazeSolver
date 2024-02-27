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
    class Node {
    	public Node(Cell cell) {
    		this.cell = cell;
    	}
    	
    	public Node(Cell cell, Cell previous) {
    		this.cell = cell;
    		this.previousNode = new Node(previous);
    	}
    	
    	public Cell getCell() {
    		return cell;
    	}
    	
    	public Node getPreviousNode() {
    		return previousNode;
    	}
    	
    	private Cell cell;
    	private Node previousNode;
    }
    
    /**
     * Provides a solution for a given maze, if possible. A solution is a path from the start cell
     * to the finish cell (inclusive). If there is no solution to the maze then returns the static
     * instance {@link Path#NO_PATH}. If the maze is perfect then there must be only one solution.
     *
     * @param maze the maze to solve
     * @return a solution for the maze or {@link Path#NO_PATH} if there is no solution
     */
    private int[] DIR = new int[] { -1, 0, 1, 0, -1 };
    private Direction[] DRS = new Direction[] { Direction.LEFT, Direction.UP,
    		Direction.RIGHT, Direction.DOWN};
    
    public Path solve(Maze maze)
    {
        Queue<Node> queue = new Queue<>();
        queue.enqueue(new Node(maze.getStart()));
        
        while(!queue.isEmpty()) {
        	Node node = queue.dequeue();
        	Cell cell = node.getCell();
        	maze.visit(cell.getX(), cell.getY());
        	
        	if (cell.equals(maze.getEnd())) {
        		return constructPath(node);
        	}
        	
        	for (int i = 0; i < 4; i++) {
        		int cx = cell.getX() + DIR[i];
        		int cy = cell.getY() + DIR[i + 1];
        		if (0 <= cx && cx < maze.size() && 0 <= cy && cy < maze.size() 
        				&& !maze.isVisited(cx, cy) && maze.isOpen(cell.getX(), cell.getY(),
        						DRS[i]))
        			queue.enqueue(new Node(new Cell(cx, cy), cell));
        	}

        }
        
        return Path.NO_PATH;
    }

    private Path constructPath(Node node) {
    	Path res = new Path();
    	while (node != null) {
    		res.addFirst(node.getCell());
    		node = node.getPreviousNode();
    	}
    	
    	return res;
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
