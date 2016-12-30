package Methods;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JComponent;
import static Others.Constants.*;

/**
 * Creates percolation object, which is an n x n grid of points. 
 * Allows user to open specified grid, open random grid, check if grid has percolated 
 */
@SuppressWarnings("serial")
public class Percolation extends JComponent {
	
	// Controller variable
	private Controller controller;
	
	// Quick Union object variable for easy reference
	private QuickUnion qu;
	
	// List to keep track of open and closed sites
	private int[] gridOpen;
	
	// Keeps size (n x n) of system for easy reference
	private int size;
	
	// Holds location of last opened cell
	private int[] last;

	/**
	 * Create n-by-n grid, with all sites initially blocked
	 * @param controller Controller variable
	 * @param n Size of grid (n x n)
	 */
	public Percolation(Controller controller, int n)
	{
		// Controller variable
		this.controller = controller;
		
		// Exception catch
		if (n <= 0)
			throw new IllegalArgumentException("Invalid value for n.");
		
		// Creates quick union (+1 so grid starts at 1 and can connect top to 0)
		size = n;
		qu = new QuickUnion(n * n + 1);
		last = new int[2];

		// Creates list for open or closed (0 open, 1 closed)
		gridOpen = new int[n * n + 1];
		gridOpen[0] = 0;
		for (int i=1; i<gridOpen.length; i++)
			gridOpen[i] = 1;
	}
	
	/** Open site (row i, column j) if closed
	 * @param i Row i
	 * @param j Column j
	 */
	public void open(int i, int j)
	{
		exceptionCheck(i, j);
		int index = (i - 1) * size + j;
		gridOpen[index] = 0;
		
		// Top neighbor
		if (i == 1)
			qu.union(index, 0);
		if (i - 1 > 0 && isOpen(i - 1, j))
			qu.union(index, index - size);
		
		// Bottom neighbor
		if (i + 1 <= size && isOpen(i + 1, j))
			qu.union(index, index + size);
		
		// Right neighbor
		if (j + 1 <= size && isOpen(i, j + 1))
			qu.union(index, index + 1);
		
		// Left neighbor
		if (j - 1 > 0 && isOpen(i, j - 1))
			qu.union(index, index - 1);
		
		// Holds location of last opened cell
		last[0] = i;
		last[1] = j;
	}
	
	/** Opens random closed cell in grid
	 */
	public void openRandom()
	{
		Random rand = new Random();
		int i = rand.nextInt(size) + 1;
		int j = rand.nextInt(size) + 1;

		// Open if closed
		while(isOpen(i, j))
		{
			i = rand.nextInt(size) + 1;
			j = rand.nextInt(size) + 1;
		}
		open(i, j);
	}
	
	/** Checks if cell (i, j) is open (connected to top)
	 * @param i Row j
	 * @param j Column i
	 * @return boolean
	 */
	public boolean isOpen(int i, int j)
	{
		exceptionCheck(i, j);
		return (gridOpen[(i - 1) * size + j] == 0 ? true : false);
	}
	
	/** Checks if cell (i, j) percolates (top reaches bottom)
	 * @param i Row i
	 * @param j Column j
	 * @return boolean
	 */
	private boolean isFull(int i, int j)
	{
		exceptionCheck(i, j);
		return qu.connected((i - 1) * size + j, 0);
	}
	
	/** Checks if system percolates (top reaches bottom)
	 * @return boolean
	 */
	public boolean percolates()
	{
		for (int j=1; j<=size; j++)
			if (isFull(size, j))
				return true;
		
		return false;
	}

	/** Check if (i, j) is valid grid cell
	 * @param i Grid row
	 * @param j Grid column
	 */
	private void exceptionCheck(int i, int j)
	{
		// Exception catch
		if (i <= 0 || i > size)
			throw new IndexOutOfBoundsException("Invalid value for i (" + i + ").");
		if (j <= 0 || j > size)
			throw new IndexOutOfBoundsException("Invalid value for j (" + j + ").");
	}
	
	// Paint component method
	// White if open and not full, black if closed, blue if open and full, red if cell that percolates
	public void paintComponent(Graphics g)
	{
		// Initialize variables
		super.paintComponent(g);
		Color c = Color.BLACK;
		
		// Size of grid  and cells based on current frame size
		double width = controller.getFrameSize().getWidth();
		double height = controller.getFrameSize().getHeight();
		double min = (width < height? width: height);
		int rect = (int) min / (size + gridWeight);
		
		// Size of space in between grid
		int space = (rect / 4 > 1 ? rect / 4: 1);

		// Iterate through grid values
		for (int i=1; i<=size; i++)
		{
			for (int j=1; j<=size; j++)
			{
				// Set rectangle color
				if (isOpen(i, j))
					c = Color.WHITE;
				else if (!isOpen(i, j))
					c = Color.BLACK;
				if (isFull(i, j) && isOpen(i, j))
					c = Color.BLUE;
				if ((i == last[0] && j == last[1]) && percolates())
					c = Color.RED;

				// Draw rectangle of specified color
				g.setColor(c);
				g.fillRect(j * rect + buttonSize, i * rect + buttonSize, rect - space, rect - space);
			}
		}
	}
	
	/** Returns current size of percolation grid
	 * @return int
	 */
	public int getGridSize()
	{
		return size;
	}
}