import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class OptionsFrame extends JFrame {

    JFrame optFrame;
    int optFrameHeight;
    int optFrameWidth;
    private JButton test;
    private JComboBox colorPlayer;
    private JComboBox colorIA;

    public OptionsFrame() {

        optFrame = new JFrame();
        optFrame.setSize(Finals.screenWidth * 3 / 4, Finals.screenHeight * 3 / 4);
        optFrame.setLocation(Finals.screenWidth / 8, Finals.screenHeight / 8);

        //get the size of the created frame
        Rectangle r = optFrame.getBounds();
        optFrameHeight = r.height;
        optFrameWidth = r.width;

        //put the JPanel on (null) in order to locate the buttons wherever we want
        JPanel pnlButton = new JPanel();
        pnlButton.setLayout(null);

        this.test = new JButton("Test");

        Dimension size = new Dimension(300, 40);
        test.setSize(size);
        test.setBounds(optFrameWidth / 2 - size.width / 2, optFrameHeight * 4 / 12 - size.height / 2, size.width,
                       size.height);
        pnlButton.add(test);
        optFrame.add(pnlButton);

        //JFrame properties
        optFrame.setTitle("OPTIONS");
        optFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        optFrame.setVisible(true);
    }

}
