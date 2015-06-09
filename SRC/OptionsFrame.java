import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class OptionsFrame extends JFrame {

    JFrame optFrame;
    int optFrameHeight;
    int optFrameWidth;
    private JButton saveChanges;
    private JButton cancel;
    private JLabel colorPlayerLabel;
    private JLabel colorIALabel;
    @SuppressWarnings("rawtypes")
    private JComboBox colorPlayerChoice;
    @SuppressWarnings("rawtypes")
    private JComboBox colorIAChoice;
    private JCheckBox music;
    private JCheckBox fog;

    public static String colorPlayer;
    public static String colorIA;
    public static String musicOnOff;
    public static String fogOnOff;


    /**
     * Constructeur.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public OptionsFrame() {

        optFrame = new JFrame();
        optFrame.setSize(Finals.screenWidth * 3 / 4, Finals.screenHeight * 3 / 4);
        optFrame.setLocation(Finals.screenWidth / 8, Finals.screenHeight / 8);


        //Initializing
        colorPlayer = "blue";
        colorIA = "orange";
        musicOnOff = "MusicOn";
        fogOnOff = "FogOn";


        //get the size of the created frame
        Rectangle r = optFrame.getBounds();
        optFrameHeight = r.height;
        optFrameWidth = r.width;


        //put the JPanel on (null) in order to locate the buttons wherever we want
        JPanel panel = new JPanel();
        panel.setLayout(null);


        //Initializing the components
        String[] colors = { "blue", "green", "orange", "pink" };
        String[] colors2 = { "pink", "green", "orange", "blue" };
        this.colorPlayerChoice = new JComboBox(colors);
        this.colorIAChoice = new JComboBox(colors2);
        this.colorPlayerLabel = new JLabel("Player's Color :");
        this.colorIALabel = new JLabel("IA's Color :");
        this.saveChanges = new JButton("Save Changes");
        this.cancel = new JButton("Cancel");
        this.music = new JCheckBox("Music");
        this.music.setSelected(true);
        this.fog = new JCheckBox("Fog of war");
        this.fog.setSelected(true);


        //Bounds of the components
        Dimension size = new Dimension(200, 40);
        int w = size.width; //width of a component
        int h = size.height; //height of a component
        colorPlayerChoice.setBounds(optFrameWidth * 7 / 12 - w / 2, optFrameHeight * 3 / 12 - h / 2, w, h);
        colorIAChoice.setBounds(optFrameWidth * 7 / 12 - w / 2, optFrameHeight * 5 / 12 - h / 2, w, h);
        colorPlayerLabel.setBounds(optFrameWidth * 5 / 12 - w / 2, optFrameHeight * 3 / 12 - h / 2, w, h);
        colorIALabel.setBounds(optFrameWidth * 5 / 12 - w / 2, optFrameHeight * 5 / 12 - h / 2, w, h);
        music.setBounds(optFrameWidth * 5 / 12 - w / 2, optFrameHeight * 13 / 24 - h / 2, 150, h);
        fog.setBounds(optFrameWidth * 5 / 12 - w / 2, optFrameHeight * 15 / 24 - h / 2, 150, h);
        saveChanges.setBounds(optFrameWidth * 5 / 12 - w / 2, optFrameHeight * 10 / 12 - h / 2, 150, h);
        cancel.setBounds(optFrameWidth * 7 / 12 - w / 2, optFrameHeight * 10 / 12 - h / 2, 150, h);


        //Adding the components to the frame
        panel.add(colorPlayerChoice);
        panel.add(colorIAChoice);
        panel.add(colorPlayerLabel);
        panel.add(colorIALabel);
        panel.add(music);
        panel.add(fog);
        panel.add(saveChanges);
        panel.add(cancel);

        optFrame.add(panel);


        //Action Listeners of the components
        ActionListener colorPlayerListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                colorPlayer = (String) colorPlayerChoice.getSelectedItem();
            }
        };

        ActionListener colorIAListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                colorIA = (String) colorIAChoice.getSelectedItem();
            }
        };

        ActionListener savingChanges = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] s = { colorPlayer, colorIA, musicOnOff, fogOnOff };
                Game.setOptions(s);
                optFrame.dispose();
            }
        };

        ActionListener cancelling = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optFrame.dispose();
            }
        };

        ActionListener musicListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (music.isSelected())
                    musicOnOff = "MusicOn";
                else
                    musicOnOff = "MusicOff";
            }
        };

        ActionListener fogListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fog.isSelected())
                    fogOnOff = "FogOn";
                else
                    fogOnOff = "FogOff";
            }
        };

        //Ajout des ActionListeners aux components
        colorPlayerChoice.addActionListener(colorPlayerListener);
        colorIAChoice.addActionListener(colorIAListener);
        music.addActionListener(musicListener);
        fog.addActionListener(fogListener);
        saveChanges.addActionListener(savingChanges);
        cancel.addActionListener(cancelling);


        //JFrame properties
        optFrame.setTitle("OPTIONS");
        optFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        optFrame.setVisible(true);


    }

}
