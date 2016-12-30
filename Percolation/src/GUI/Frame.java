package GUI;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;
import static Others.Constants.*;

/** 
 * Creates frame object to hold panels
 */
@SuppressWarnings("serial")
public class Frame extends JFrame {
	
	public Frame()
	{
		// Frame initial variables
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(frameTitle);
		setVisible(true);
		setSize(initialFrameSize);

		// Size of pop up window, semi centering on computer screen
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension dim = kit.getScreenSize();
		setLocation(dim.width / 4, dim.height / 4 - 25);
	}	
}