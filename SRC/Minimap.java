import java.awt.Color;

import java.awt.Graphics;

import javax.swing.JPanel;

public class Minimap extends JPanel {
    public Minimap() {

    }
    public void paint(Graphics g){
        int Height =(int)this.getSize().getHeight();
        int Width = (int)this.getSize().getWidth();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0,Width,Height);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0,Width-1,Height-1);
        for (Item i : Item.aliveItems){
                    i.print(g, 0, 0,Width/Finals.WIDTH);
        }

    }
    
}
