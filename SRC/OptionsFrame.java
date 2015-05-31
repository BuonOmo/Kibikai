import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class OptionsFrame extends JFrame {

	JFrame optFrame;
	int optFrameHeight;
	int optFrameWidth;
	private JButton saveChanges;
	private JButton cancel;
	private JLabel colorPlayerLabel;
	private JLabel colorIALabel;
	private JComboBox colorPlayerChoice; //TODO Voir si JColorChooser est mieux
	private JComboBox colorIAChoice;
	
	public OptionsFrame() {

		optFrame = new JFrame();
		optFrame.setSize(Finals.screenWidth * 3 / 4, Finals.screenHeight * 3 / 4);
		optFrame.setLocation(Finals.screenWidth / 8, Finals.screenHeight / 8);
		
		//get the size of the created frame
		Rectangle r = optFrame.getBounds();
		optFrameHeight = r.height;
    	optFrameWidth = r.width;
    	
    	//put the JPanel on (null) in order to locate the buttons wherever we want
    	JPanel panel = new JPanel();
    	panel.setLayout(null);
    	
    	String[] colors = {"Blue", "Green", "Yellow"};
    	this.colorPlayerChoice = new JComboBox(colors);
    	this.colorIAChoice = new JComboBox(colors);
    	
    	//Size and bounds of the components
    	Dimension size = new Dimension(300, 40);
    	colorPlayerChoice.setSize(size);
    	colorPlayerChoice.setBounds(optFrameWidth / 2 - size.width / 2, optFrameHeight * 4 / 12 - size.height / 2, size.width, size.height);
    	colorIAChoice.setSize(size);
    	colorIAChoice.setBounds(optFrameWidth / 2 - size.width / 2, optFrameHeight * 6 / 12 - size.height / 2, size.width, size.height);
    	
    	panel.add(colorPlayerChoice);
    	panel.add(colorIAChoice);
    	optFrame.add(panel);
    	
    	//JFrame properties
    	optFrame.setTitle("OPTIONS");
    	optFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	optFrame.setVisible(true);
	}

}
