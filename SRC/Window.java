import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Window extends JFrame {

    private JButton newgameButton;
    private JButton loadgameButton;
    private JButton optionsButton;

    int frameHeight;
    int frameWidth;
    
    JFrame frame;

    JDesktopPane desktop;

    public Window() {

        frame = new JFrame();

        //Location and size of the frame
        frame.setSize(Finals.screenWidth * 3 / 4,
                      Finals.screenHeight * 3 / 4); //Put the size of the frame at 3/4 screen
        frame.setLocation(Finals.screenWidth / 8, Finals.screenHeight / 8); //Put the frame in the middle of the screen
        Rectangle r = frame.getBounds();//get the size of the created frame
        frameHeight = r.height;
        frameWidth = r.width;
        frame.setBackground(Color.white);


        //put the JPanel on (null) in order to locate the buttons wherever we want
        JPanel pnlButton = new JPanel();
        pnlButton.setLayout(null);
        pnlButton.setBackground(Color.white);
        
        
        //Images of the buttons
        this.newgameButton = new JButton(new ImageIcon( "new Game.png"));
        this.loadgameButton = new JButton(new ImageIcon( "Save.png"));
        this.optionsButton = new JButton(new ImageIcon( "options.png"));
        
        newgameButton.setBackground(Color.white);
        loadgameButton.setBackground(Color.white);
        optionsButton.setBackground(Color.white);
        
        
        //Random Background
        for (int i = 0;  i<5;i++){
            JLabel soGreen  = new JLabel( new ImageIcon( "SoldierGreen.png"));
            pnlButton.add(soGreen);
            soGreen.setBounds((int)(Math.random()*frameWidth), (int)(Math.random()*frameHeight), 20, 20);
            JLabel sored  = new JLabel( new ImageIcon( "SoldierRed.png"));
            pnlButton.add(sored);
            sored.setBounds((int)(Math.random()*frameWidth), (int)(Math.random()*frameHeight), 20, 20);
        }
        JLabel BaseGreen  = new JLabel( new ImageIcon( "BigBaseGreen.png"));
        pnlButton.add(BaseGreen);
        BaseGreen.setBounds((int)(0.2*frameWidth), (int)(0.3*frameHeight), 50, 50);


        
        //Location and size of the newgameButton
        newgameButton.setSize(300, 40);
        Dimension size = newgameButton.getSize();
        newgameButton.setBounds((frameWidth / 2) - 20, (frameHeight / 2) - 20 / 2, 40, 40);

        
        //Location and size of the loadgameButton
        loadgameButton.setSize(size);
        loadgameButton.setBounds((frameWidth / 2) - 20, frameHeight * 8 / 12 - 20, 40, 40);

        
        //Location and size of the optionsButton
        optionsButton.setSize(size);
        optionsButton.setBounds(frameWidth / 2 - 20, frameHeight * 10 / 12 - 20, 40, 40);
        
        
        //Add the buttons in the frame
        pnlButton.add(newgameButton);
        pnlButton.add(loadgameButton);
        pnlButton.add(optionsButton);        
        frame.add(pnlButton);

        
        //ToolTipText (text that appears when you let the mouse on a button without clicking it)
        newgameButton.setToolTipText("Start a new game");
        loadgameButton.setToolTipText("Load a previous game");
        optionsButton.setToolTipText("Change your preferences");

        
        //Action Listeners
        ActionListener start = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UI gui = new UI();
                gui.timer.start();
            }
        };
        ActionListener load = new ActionListener() {
            public void actionPerformed(ActionEvent e) { /*open game saves*/
            	//TODO ouvrir une fenetre de JFileChooser pour les sauvegardes
            }
        };
        ActionListener options = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                @SuppressWarnings("unused")
				OptionsFrame opt = new OptionsFrame();
            }
        };

        
        //Button actions
        newgameButton.addActionListener(start);
        loadgameButton.addActionListener(load);
        optionsButton.addActionListener(options);

        
        //JFrame properties
        frame.setTitle("LUCA");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /*public void paintComponent(Graphics g){
		buffer.setColor(Color.black);
		buffer.fillRect(screenWidth*3/4, screenHeight*3/4,screenWidth*3/4+frameWidth,screenHeight*3/4+frameHeight);
		g.drawImage(ArrierePlan,0,0,this);
	}*/


    public static void main(String[] args) {
        Window wind;
        wind = new Window();
    }
}
