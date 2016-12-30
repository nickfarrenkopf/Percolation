package GUI;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import Methods.Controller;
import Methods.Percolation;
import static Others.Constants.*;

/** 
 * Creates panel that outputs GUI to user. Panel contains a tabbed pane
 * holding one pane to show percolation image and a second pane to show statistics
 * of the percolations.
 */
@SuppressWarnings("serial")
public class PercPanel extends JPanel implements ActionListener{

	// Controller variable
	private Controller controller;
	
	// Percolation variable
	private Percolation perc;

	// GUI control variables
	private JButton runOnceButton;
	private JButton runTimedButton;
	private JButton runAllButton;
	private JButton resetButton;
	private JTextField gridSizeField;
	
	// Timer variables
	private Timer timer;
	private int pauseTime;
	
	/** Initialize panel of panel holding percolation image
	 * @param controller
	 */
	public PercPanel(Controller controller)
	{
		// Controller variable
		this.controller = controller;
		 
		// Initialize all RUN buttons
		runOnceButton = new JButton("Run Once");
		runTimedButton = new JButton("Run Timed");
		runAllButton = new JButton("Run All");
		
		// Reset Button and text field
		resetButton = new JButton("Reset");
		JLabel gridSizeLabel = new JLabel("Grid Size:");
		gridSizeField = new JTextField(initialGridSize + "");
		gridSizeField.setPreferredSize(textFieldSize);

		// Set action listeners
		runOnceButton.addActionListener(this);
		runTimedButton.addActionListener(this);
		runAllButton.addActionListener(this);
		resetButton.addActionListener(this);
		
		// Adds all things to panel
		add(runOnceButton);
		add(runTimedButton);
		add(runAllButton);
		add(resetButton);
		add(gridSizeLabel);
		add(gridSizeField);
		
		// Refresh Screen
		revalidate();
	}
	
	/** Sets current percolation for panel
	 * 	@param p percolation
	 */
	public void setPerc(Percolation p)
	{
		perc = p;
		add(perc);
		revalidate();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// Iterate button to open single square
		if(e.getSource() == runOnceButton)
			perc.openRandom();

		// Run timed until percolation button
		if (e.getSource() == runTimedButton)
		{
			// Initialize pause time
			int size = perc.getGridSize();
			pauseTime = (int) (pauseConstant / size / size);
			if (pauseTime < 1)
				pauseTime = 1;
			
			// What to do to start timed 
			if (runTimedButton.getText().equals("Run Timed") && !perc.percolates())
			{
				// Change button text and switch buttons
				runTimedButton.setText("Pause");
				runOnceButton.setEnabled(false);
				runAllButton.setEnabled(false);
				
				// Set timer for iterations
				timer = new Timer();
				timer.schedule(new TimedIteration(), 0);
				
			// What to do to stop timed
			} else if (runTimedButton.getText().equals("Pause")) {
				
				// Change text and switch buttons
				runTimedButton.setText("Run Timed");
				runOnceButton.setEnabled(true);
				runAllButton.setEnabled(true);
				
				// Cancel current timer
				timer.cancel();
			}
		}
		
		// Run until completion
		if (e.getSource() == runAllButton)	
			while(!perc.percolates())
				perc.openRandom();
			
		// Reset Button click
		if (e.getSource() == resetButton)
		{
			// Ends timers if already running
			if (runTimedButton.getText().equals("Pause"))
				runTimedButton.doClick();
			
			// Try to reset percolation 
			try {
				int i = Integer.parseInt(gridSizeField.getText());
				controller.newPercolation(i);
			
			// Catch invalid input error
			} catch (Exception error) {
				String message = "Invalid input for grid size (integer needed).";
				String header = "Error: Invalid grid input.";
				JOptionPane.showMessageDialog(null, message, header, JOptionPane.INFORMATION_MESSAGE);
				gridSizeField.setText(initialGridSize + "");
			}
		}
		
		// Repaint screen
		repaint();
	}
	
	/** Runs iteration on a timed basis
	 */
	class TimedIteration extends TimerTask {
		public void run() {
			// Open random and schedule new timer
			if (!perc.percolates())
			{
				perc.openRandom();
				timer.schedule(new TimedIteration(), pauseTime);
			}
			
			// Resets button if percolates
			if (perc.percolates())
				runTimedButton.doClick();
			repaint();
		}
	}
	
	/** Paints percolation to screen
	 */
	public void paint(Graphics g)
	{
		super.paint(g);
		if (perc != null)
			perc.paint(g);
	}
}