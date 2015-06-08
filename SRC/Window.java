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
        frame.setBackground(Finals.BACKGROUND_COLOR);


        //put the JPanel on (null) in order to locate the buttons wherever we want
        JPanel pnlButton = new JPanel();
        pnlButton.setLayout(null);
        pnlButton.setBackground(Finals.BACKGROUND_COLOR);
        
        
        //Images of the buttons
        this.newgameButton = new JButton(new ImageIcon( "IMG/Window/play.png"));
        this.optionsButton = new JButton(new ImageIcon( "IMG/Window/Options.png"));
        
        newgameButton.setBackground(Finals.BACKGROUND_COLOR);
        optionsButton.setBackground(Finals.BACKGROUND_COLOR);
        
        
        //Random Background
        for (int i = 0;  i<5;i++){
            JLabel soGreen  = new JLabel( new ImageIcon( "IMG/blue/Soldier/2.png"));
            pnlButton.add(soGreen);
            soGreen.setBounds((int)(Math.random()*frameWidth), (int)(Math.random()*frameHeight), 60, 60);
            JLabel sored  = new JLabel( new ImageIcon( "IMG/orange/Soldier/2.png"));
            pnlButton.add(sored);
            sored.setBounds((int)(Math.random()*frameWidth), (int)(Math.random()*frameHeight), 60, 60);
        }
        JLabel BaseGreen  = new JLabel( new ImageIcon( "IMG/Window/Base.png"));
        pnlButton.add(BaseGreen);
        BaseGreen.setBounds((int)(0.2*frameWidth), (int)(0.3*frameHeight), 50, 50);


        
        //Location and size of the buttons
        newgameButton.setBounds((frameWidth / 2) - 20, (frameHeight / 2) - 20, 40, 40);
        optionsButton.setBounds((frameWidth / 2) - 20, frameHeight * 8 / 12 - 20, 40, 40);
        
        
        //Add the buttons in the frame
        pnlButton.add(newgameButton);
        pnlButton.add(optionsButton);        
        frame.add(pnlButton);

        
        //ToolTipText (text that appears when you let the mouse on a button without clicking it)
        newgameButton.setToolTipText("Start a new game");
        optionsButton.setToolTipText("Change your preferences");

        
        //Action Listeners
        ActionListener start = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UI gui = new UI();
                gui.timer.start();
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
