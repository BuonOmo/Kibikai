import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Canvas extends JComponent{
	Rectangle2D hitBox;
	Unit simpleUnit;
	Building b1;
	public Canvas(){
		hitBox = new Rectangle2D.Double (0,0, 10, 10);
		simpleUnit = new SimpleUnit(new Player (Color.GREEN, b1, "Player one RPZ" ), new Point2D.Double(50,50), null) ;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLUE);
		g.fillRoundRect((int)hitBox.getX()*Finals.scale, (int)hitBox.getY()*Finals.scale, 
				(int)hitBox.getWidth()*Finals.scale, (int)hitBox.getHeight()*Finals.scale,3*Finals.scale,3*Finals.scale);
		simpleUnit.print(g);
	}
	
}
