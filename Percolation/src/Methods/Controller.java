package Methods;
import java.awt.Dimension;
import javax.swing.JTabbedPane;
import GUI.Frame;
import GUI.PercPanel;
import GUI.StatsPanel;
import static Others.Constants.*;

/**
 * Contains Controller and main method for Percolation program.
 * Allows user to create an n x n grid to simulate percolation.
 * Allows user to view simple statistics of (n) sized grid iterated (trial) number of times.
 * Simulates percolation by creating a tree and connecting using quick union.
 * @author Nick Farrenkopf
 */
public class Controller {

	/**
	 *  Test client.
	 */
	public static void main(String[] args)
	{
		new Controller();
	}

	// GUI variables
	private Frame frame;
	private PercPanel percPanel;
	private StatsPanel statsPanel;
	
	// Percolation variables
	private Percolation perc;

	// Initialize controller
	public Controller()
	{
		// Initialize variables
		frame = new Frame();
		percPanel = new PercPanel(this);
		statsPanel = new StatsPanel(this);
		newPercolation(initialGridSize);
		
		// Adds to tabbed pane
		JTabbedPane tabbed = new JTabbedPane();
		tabbed.add("Percolation Image", percPanel);
		tabbed.add("Percolation Stats", statsPanel);
		
		// Add tabbed pane to frame
		frame.add(tabbed);

		// refresh screen
		frame.revalidate();
	}
	
	/** Creates a new percolation, updating panel
	 * @param n Size of percolation grid
	 */
	public void newPercolation(int n)
	{
		// Exception check
		if (n <= 0)
			throw new IllegalArgumentException("Invalid value for n.");
		
		perc = new Percolation(this, n);
		percPanel.setPerc(perc);
	}
	
	/** Grabs current size of frame for aesthetically pleasing drawing
	 * @return Dimension
	 */
	public Dimension getFrameSize()
	{
		return frame.getSize();
	}
}