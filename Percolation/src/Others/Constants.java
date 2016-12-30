package Others;
import java.awt.Dimension;
import java.text.DecimalFormat;

/**
 * Contains constants used in percolation program.
 */
public class Constants {

	// Frame constants
	public static final String frameTitle = "Percolation";
	public static final Dimension initialFrameSize = new Dimension(500, 510);
	
	// Panel constants
	public static final Dimension textFieldSize = new Dimension(50, 20);
	public static final int buttonSize = (int) textFieldSize.getHeight() + 8;
	
	// Timed iterations constants
	public static final int pauseConstant = 25000;
	public static final double pauseWeight = 30;
	
	// Percolation variables
	public static final int initialGridSize = 20;
	public static final int gridWeight = 6;
	
	// Percolation statistic variables
	public static final int initialNumTrials = 50;
	public static final DecimalFormat df = new DecimalFormat("0.#####");
}