import java.util.ArrayList;
import java.util.Random;

/**
 * <pre>
 * Name: Rui Meng
 * Mrs. Kankelborg
 * Period 1
 * Project 4 MazeSolver
 * Last Revised on: 2/20/2024 
 * </pre>
 */

/**
 * Creates new mazes. Please refer to the spec for instructions on how to generate mazes.
 */
public class MazeGenerator
{
	private final Random rand = new Random();
	
    /**
     * Randomly generates a perfect maze of {@param size}.
     *
     * @param size the size of the maze to generate
     * @return the generated maze
     */
    public Maze generate(int size)
    {
    	 // Initialize a new maze and an array to track visited cells.
        Maze maze = new Maze(size);
        boolean[][] visited = new boolean[size][size];

        // Stack used for backtracking during maze generation.
        Stack<Cell> stack = new Stack<>();

        // Start maze generation from the cell (0,0).
        visited[0][0] = true;
        stack.push(new Cell(0, 0));

        // Continue until there are no more cells to visit.
        while (!stack.isEmpty()) {
            Cell current = stack.pop();
            ArrayList<Cell> neighbors = getUnvisitedNeighbors(current, visited, maze);

            if (!neighbors.isEmpty()) {
                // Choose a random unvisited neighbor and remove the wall between the current cell and the chosen neighbor.
                Cell neighbor = neighbors.get(rand.nextInt(neighbors.size()));
                removeWallBetween(maze, current, neighbor);
                visited[neighbor.getX()][neighbor.getY()] = true;
                
                // Push the current cell back onto the stack before pushing the neighbor, to allow backtracking.
                stack.push(current);
                stack.push(neighbor);
            }
        }
        
        // Choose random start and end points for the maze
        int startX = rand.nextInt(size);
        int startY = rand.nextInt(size);
        int endX = rand.nextInt(size);
        int endY = rand.nextInt(size);

        // Continue generating start and end points until they differ
        while (startX == endX && startY == endY) {
            startX = rand.nextInt(size);
            startY = rand.nextInt(size);
            endX = rand.nextInt(size);
            endY = rand.nextInt(size);
        }
        
        maze.setStart(startX, startY);
        maze.setEnd(endX, endY);

        return maze;
    }

    /**
     * Removes the wall between two adjacent cells.
     * 
     * @param maze The maze from which to remove the wall.
     * @param current The current cell.
     * @param next The adjacent cell to which the wall will be removed.
     */
    private void removeWallBetween(Maze maze, Cell current, Cell next)
    {
    	// Determine the relative position of next to current and remove the corresponding wall.
        if (current.getX() == next.getX()) {
            if (current.getY() < next.getY()) {
                maze.removeWall(current.getX(), current.getY(), Direction.UP);
            } else {
                maze.removeWall(current.getX(), current.getY(), Direction.DOWN);
            }
        } else if (current.getY() == next.getY()) {
            if (current.getX() < next.getX()) {
                maze.removeWall(current.getX(), current.getY(), Direction.RIGHT);
            } else {
                maze.removeWall(current.getX(), current.getY(), Direction.LEFT);
            }
        }
    }

    /**
     * Retrieves a list of all unvisited neighbors of a given cell.
     * 
     * @param cell The cell whose neighbors are to be checked.
     * @param visited A 2D boolean array indicating which cells have been visited.
     * @param maze The maze in which the cell exists.
     * @return A list of unvisited neighbor cells.
     */
    private ArrayList<Cell> getUnvisitedNeighbors(Cell cell, boolean[][] visited, Maze maze)
    {
        ArrayList<Cell> neighbors = new ArrayList<>();
        int x = cell.getX();
        int y = cell.getY();

        // Check each possible direction (up, down, left, right) for unvisited neighbors.
        if (x > 0 && !visited[x - 1][y]) neighbors.add(new Cell(x - 1, y));
        if (x < visited.length - 1 && !visited[x + 1][y]) neighbors.add(new Cell(x + 1, y));
        
        if (y > 0 && !visited[x][y - 1]) neighbors.add(new Cell(x, y - 1));
        if (y < visited[0].length - 1 && !visited[x][y + 1]) neighbors.add(new Cell(x, y + 1));

        return neighbors;
    }
    
    /**
     * Creates and draws a sample maze. Try generating mazes with different sizes!
     *
     * @param args unused
     */
    public static void main(String[] args)
    {
    	StdRandom.setSeed(34);
        int size = 10; // Setting above 200 is not recommended!
        MazeGenerator generator = new MazeGenerator();
        Maze maze = generator.generate(size);
        maze.draw();
    }
}
