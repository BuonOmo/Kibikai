import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;


public class SideBand extends JPanel implements Finals {

    public SideBand() {
        this.setBackground(Color.GRAY);
        this.setBounds(Finals.screenWidth * 5 / 6, 0, Finals.screenWidth * (1 / 6), Finals.screenHeight);
        this.setVisible(true);
        BorderLayout eastWest = new BorderLayout();
        this.setLayout(eastWest);
        String nomJoueur = "Johnny";
        JButton player = new JButton(nomJoueur);
        player.setSize(Finals.screenWidth * (1 / 6), 50);
        this.add("North", player);
    }

    public void paint(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, Finals.screenWidth / 6, Finals.screenHeight);
    }


}
