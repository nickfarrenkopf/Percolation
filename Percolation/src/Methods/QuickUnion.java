package Methods;

/**
 * Creates object to simulate quick union between tree nodes.
 */
public class QuickUnion {

	// Variables
	private int[] id;
	private int[] treeSize;
	
	/**
	 * Initializes quick union object to size n
	 * @param n Number of objects in quick union (i.e. number of cells in percolation grid)
	 */
	public QuickUnion(int n)
	{
		// Checks if valid input
		if (n <= 0)
			throw new IllegalArgumentException("Invalid input for n.");
		
		// Initialize variables
		id = new int[n];
		treeSize = new int[n];
		for (int i=0; i<n; i++)
		{
			id[i] = i;
			treeSize[i] = i;
		}
	}
	
	/** Provides parent node until root is found
	 * @param i Initial node 
	 * @return int
	 */
	private int root(int i)
	{
		// Checks if valid input
		exceptionCheck(i);

		while (i != id[i])
		{
			id[i] = id[id[i]];
			i = id[i];
		}
		return i;
	}
	
	/** Checks if two nodes are connected
	 * @param p Node 1
	 * @param q Node 2
	 * @return boolean
	 */
	public boolean connected(int p, int q)
	{
		// Checks if valid input
		exceptionCheck(p);
		exceptionCheck(q);
		return root(p) == root(q);
	}
	
	/** Connects two nodes by making root of the smaller tree the root of the larger tree
	 * @param p Node 1
	 * @param q Node 2
	 */
	public void union(int p, int q)
	{
		// Checks if valid input
		exceptionCheck(p);
		exceptionCheck(q);
		
		// Finds root
		int i = root(p);
		int j = root(q);
		
		// Already share root
		if (i == j)
			return;
		
		// If root is zero, make primary root
		if (i == 0)
			id[j] = i;
		else if (j == 0)
			id[i] = j;
		
		// Check size, updating id and tree size
		else if (treeSize[i] < treeSize[j])
		{
			id[i] = j;
			treeSize[j] += treeSize[i];
		} else {
			id[j] = i;
			treeSize[i] += treeSize[j];
		}
	}	
	
	// Exception check
	private void exceptionCheck(int i)
	{
		// Exception catch
		if (i < 0 || i > id.length - 1)
			throw new IndexOutOfBoundsException("Invalid value for i (" + i + ").");
	}
}