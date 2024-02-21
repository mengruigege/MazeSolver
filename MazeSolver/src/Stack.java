import java.util.ArrayList;
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
 * A last-in-first-out (LIFO) stack of generic items.
 *
 * @param <T> the type of item to store in the stack
 */
public class Stack<T>
{
    private ArrayList<T> list;
    /**
     * Initializes an empty stack.
     */
    public Stack()
    {
        list = new ArrayList<>();
    }

    /**
     * Adds an item to the stack.
     *
     * @param newItem the item to add
     */
    public void push(T newItem)
    {
        list.add(newItem);
    }

    /**
     * Removes and returns the item on the stack that was most recently added.
     *
     * @return the item on the stack that was most recently added
     */
    public T pop()
    {
    	if (isEmpty()) {
	        throw new IllegalStateException("Cannot pop from an empty stack.");
	    }
	    return list.remove(list.size() - 1);
    }

    /**
     * Returns the item most recently added to the stack.
     *
     * @return the item most recently added to the stack
     */
    public T peek()
    {
    	if (isEmpty()) {
            throw new IllegalStateException("Cannot peek on an empty stack.");
        }
        return list.get(list.size() - 1);
    }

    /**
     * Returns whether the stack is empty.
     *
     * @return whether the stack is empty
     */
    public boolean isEmpty()
    {
    	return list.isEmpty();
    }

    /**
     * Returns the number of items in the stack.
     *
     * @return the number of items in the stack
     */
    public int size()
    {
    	return list.size();
    }
}
