import java.util.ArrayList;
import java.util.Random;

/**
 * <pre>
 * Name: Rui Meng
 * Mrs. Kankelborg
 * Period 1
 * Project 4 MazeSolver
 * Last Revised on: 2/13/2024 
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
    	Maze maze = new Maze(size);
        boolean[][] visited = new boolean[size][size];
        Stack<Cell> stack = new Stack<>();

        // Start at a random cell
        int startX = rand.nextInt(size);
        int startY = rand.nextInt(size);
        visited[startX][startY] = true;
        stack.push(new Cell(startX, startY));

        while (!stack.isEmpty()) {
            Cell current = stack.peek();
            ArrayList<Cell> neighbors = getUnvisitedNeighbors(current, visited, maze);

            if (!neighbors.isEmpty()) {
                Cell neighbor = neighbors.get(rand.nextInt(neighbors.size()));
                removeWallBetween(maze, current, neighbor);
                visited[neighbor.getX()][neighbor.getY()] = true;
                stack.push(neighbor);
            } else {
                stack.pop();
            }
        }

        // Optionally set start and end points
        maze.setStart(0, 0);
        maze.setEnd(size - 1, size - 1);

        return maze;
    }

    private void removeWallBetween(Maze maze, Cell current, Cell next) {
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

    private ArrayList<Cell> getUnvisitedNeighbors(Cell cell, boolean[][] visited, Maze maze) {
        ArrayList<Cell> neighbors = new ArrayList<>();
        int x = cell.getX();
        int y = cell.getY();

        // Check each direction for unvisited neighbors
        if (x > 0 && !visited[x-1][y]) neighbors.add(new Cell(x-1, y));
        if (x < visited.length - 1 && !visited[x+1][y]) neighbors.add(new Cell(x+1, y));
        if (y > 0 && !visited[x][y-1]) neighbors.add(new Cell(x, y-1));
        if (y < visited[0].length - 1 && !visited[x][y+1]) neighbors.add(new Cell(x, y+1));

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
