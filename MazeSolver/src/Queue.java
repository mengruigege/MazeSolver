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
 * A first-in-first-out (FIFO) queue of generic items.
 *
 * @param <T> the type of item to store in the queue
 */
public class Queue<T>
{
    private static class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
        }
    }

	private Node<T> head; // front of the queue
    private Node<T> tail; // end of the queue
    private int size = 0;

    /**
     * Initializes an empty queue.
     */
    public Queue()
    {
        head = null;
        tail = null;
    }

    /**
     * Adds an item to the queue.
     *
     * @param newItem the item to add
     */
    public void enqueue(T newItem)
    {
    	Node<T> newNode = new Node<>(newItem);
	    if (tail != null) {
	        tail.next = newNode;
	    }
	    tail = newNode;
	    if (head == null) {
	        head = newNode;
	    }
	    size++;
    }

    /**
     * Removes and returns the item on the queue that was least recently added.
     *
     * @return the item on the queue that was least recently added
     */
    public T dequeue()
    {
    	if (head == null) {
    		throw new IllegalStateException("Cannot pop an empty queue.");
    	}
        T data = head.data;
        head = head.next;
        if (head == null) {
            tail = null; // queue is now empty
        }
        size--;
        return data;
    }

    /**
     * Returns the item least recently added to the queue.
     *
     * @return the item least recently added to the queue
     */
    public T peek()
    {
        if (head == null) {
        	throw new IllegalStateException("Cannot peek an empty queue.");
        }
        return head.data;
	}

    /**
     * Returns whether the queue is empty.
     *
     * @return whether the queue is empty
     */
    public boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * Returns the number of items in the queue.
     *
     * @return the number of items in the queue
     */
    public int size()
    {
    	return size;
    }
}
