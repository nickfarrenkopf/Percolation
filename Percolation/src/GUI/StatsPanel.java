package GUI;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import Methods.Controller;
import Methods.PercolationStats;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static Others.Constants.*;

/** 
 * Stats panel that holds various statistic values for percolation thresholds (how many until percolation).
 * Allows for user to input size of percolation grid (n) and number of trials (trial).
 * Outputs the mean, standard deviation, and 95% confidence interval given inputs. 
 */
@SuppressWarnings("serial")
public class StatsPanel extends JPanel implements ActionListener{

	// Controller variable
	private Controller controller;
	
	// JCompmonent variables
	private JTextField trialsField;
	private JTextField sizeField;
	private JButton runButton;
	
	// Statistic variables
	private PercolationStats percStats;
	private JLabel mean;
	private JLabel std;
	private JLabel confidence;
	private JLabel low;
	private JLabel high;
	
	/** Initialize panel of panel holding percolation statistics
	 * @param controller variable
	 */
	public StatsPanel(Controller controller)
	{
		// Sets controller
		this.controller = controller;
		
		// Number of trials label and text field
		trialsField = new JTextField(initialNumTrials + "");
		trialsField.setPreferredSize(textFieldSize);
		JLabel trialsLabel = new JLabel("Number of trials:  ");
		JPanel trialsPanel = new JPanel();
		trialsPanel.add(trialsLabel);
		trialsPanel.add(trialsField);
		
		// Grid size label and text field
		sizeField = new JTextField(initialGridSize + "");
		sizeField.setPreferredSize(textFieldSize);
		JLabel sizeLabel = new JLabel("Size of Grid:  ");
		JPanel sizePanel = new JPanel();
		sizePanel.add(sizeLabel);
		sizePanel.add(sizeField);
		
		// Run button initialization
		runButton = new JButton("Run");
		runButton.addActionListener(this);
		
		// Panel of stats
		mean = new JLabel("Mean: ");
		std = new JLabel("Standard Deviation: ");
		confidence = new JLabel("95% Confidence Level");
		low = new JLabel("Low bound: ");
		high = new JLabel("High bound: ");
		
		// Adds all to separate panel
		JPanel allStatsPanel = new JPanel();
		allStatsPanel.setLayout(new BoxLayout(allStatsPanel, BoxLayout.Y_AXIS));
		allStatsPanel.add(mean);
		allStatsPanel.add(std);
		allStatsPanel.add(new JLabel(" "));
		allStatsPanel.add(confidence);
		allStatsPanel.add(low);
		allStatsPanel.add(high);
		
		// Sets orientation on panel of stats
		mean.setAlignmentX(Component.CENTER_ALIGNMENT);
		std.setAlignmentX(Component.CENTER_ALIGNMENT);
		confidence.setAlignmentX(Component.CENTER_ALIGNMENT);
		low.setAlignmentX(Component.CENTER_ALIGNMENT);
		high.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// Sets layout of panel
		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(grid);

		// Add to panel
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0.5;
		add(trialsPanel, c);
		
		// Adds size panel
		c.gridy = 1;
		add(sizePanel, c);
		
		// Adds run button
		c.gridy = 2;
		c.weighty = 0.5;
		add(runButton, c);
		
		// Adds stats panel
		c.gridy = 3;
		c.weighty = 1;
		add(allStatsPanel, c);
		
		// Refresh screen
		revalidate();
	}
	
	/** Calculates mean, standard deviation, and 95% confidence interval given inputs
	 * @param gridSize Size of percolation grid to be iterated
	 * @param numTrials Number of times grid is iterated over
	 */
	public void calculateStats(int gridSize, int numTrials)
	{
		// Calculate stats
		percStats = new PercolationStats(controller, gridSize, numTrials);
		
		// Change labels with specified decimal format
		mean.setText("Mean:   " + df.format(percStats.mean()));
		std.setText("Standard Deviation:   " + df.format(percStats.stddev()));
		confidence.setText("95% Confidence Level");
		low.setText("Low bound:   " + df.format(percStats.confidenceLo()));
		high.setText("High bound:   " + df.format(percStats.confidenceHi()));
		
		// Refresh screen
		repaint();
	}

	// What happens when things are pressed
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// When run button is pressed
		try {
			int gridSize = Integer.parseInt(sizeField.getText());
			int numTrials = Integer.parseInt(trialsField.getText());
			calculateStats(gridSize, numTrials);
			
		// What happens if fail
		} catch (Exception error) {
			String message = "Size of grid and number of trials must be positive integer.";
			String header = "Input error";
			JOptionPane.showMessageDialog(null, message, header, JOptionPane.INFORMATION_MESSAGE);
		}	
	}	
}