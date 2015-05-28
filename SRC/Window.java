import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


@SuppressWarnings("serial")
public class Window extends JFrame{
	
	private JButton newgameButton;
	private JButton loadgameButton;
	private JButton optionsButton;
	
	private JButton test;
	
	int frameHeight;
	int frameWidth;
	
	JFrame frame;
	
	JDesktopPane desktop;
	
	public Window (){
		
		frame = new JFrame();
	    
	    //Location and size of the frame
	    frame.setSize(Finals.screenWidth*3/4, Finals.screenHeight*3/4); //Put the size of the frame at 3/4 screen
	    frame.setLocation(Finals.screenWidth/8, Finals.screenHeight/8); //Put the frame in the middle of the screen
	    
	    //get the size of the created frame
	    Rectangle r = frame.getBounds();
	    frameHeight = r.height;
	    frameWidth = r.width;
	    
	    //put the JPanel on (null) in order to locate the buttons wherever we want
		JPanel pnlButton = new JPanel();
	    pnlButton.setLayout(null);
	    
	    ///frame.getContentPane().setBackground(Color.GREEN);
        
		this.newgameButton = new JButton ("New game");
		this.loadgameButton = new JButton ("Load Game");
		this.optionsButton = new JButton ("Options");
		
		this.test = new JButton ("Test");
		
		
		//Add the buttons in the frame
		pnlButton.add(newgameButton);
		pnlButton.add(loadgameButton);
		pnlButton.add(optionsButton);
		
		//Location and size of the newgameButton
		newgameButton.setSize(300, 40);
		Dimension size = newgameButton.getSize();
		newgameButton.setBounds((frameWidth/2)-size.width/2, (frameHeight/2)-size.height/2, size.width, size.height);
		
		//Location and size of the loadgameButton
		loadgameButton.setSize(size);
		loadgameButton.setBounds(frameWidth/2-size.width/2, frameHeight*8/12-size.height/2, size.width, size.height);
		
		//Location and size of the optionsButton
		optionsButton.setSize(size);
		optionsButton.setBounds(frameWidth/2-size.width/2, frameHeight*10/12-size.height/2, size.width, size.height);
		
		test.setSize(size);
		test.setBounds(frameWidth/2-size.width/2, frameHeight*4/12-size.height/2, size.width, size.height);
		frame.add(pnlButton);
		
		//ToolTipText (text that appears when you let the mouse on a button without clicking it)
		newgameButton.setToolTipText("Start a new game");
		loadgameButton.setToolTipText("Load a previous game");
		optionsButton.setToolTipText("Change your preferences");
		
		//Action Listeners
		ActionListener start = new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        UI gui = new UI();
                        gui.timer.start();
                    }
                };
		ActionListener load = new ActionListener(){public void actionPerformed(ActionEvent e){/*open game saves*/}};
		ActionListener options = new ActionListener(){public void actionPerformed(ActionEvent e){OptionsFrame opt = new OptionsFrame();}};
		
		//Button actions
		newgameButton.addActionListener(start);
		loadgameButton.addActionListener(load);
		optionsButton.addActionListener(options);
				
		//JFrame properties
		frame.setTitle("LUCA");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void optionsFrame(){
		
	}
	
	/*public void paintComponent(Graphics g){
		buffer.setColor(Color.black);
		buffer.fillRect(screenWidth*3/4, screenHeight*3/4,screenWidth*3/4+frameWidth,screenHeight*3/4+frameHeight);
		g.drawImage(ArrierePlan,0,0,this);
	}*/
	
	public static void main (String[] args){
		@SuppressWarnings("unused")
		Window wind = new Window();
	}
}
