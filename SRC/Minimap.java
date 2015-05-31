import java.awt.Color;

import java.awt.Graphics;

import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Minimap extends JPanel {
    public Minimap() {

    }
    public void paint(Graphics g){
        int Height =(int)this.getSize().getHeight();
        int Width = (int)this.getSize().getWidth();
        double scale = Width/Finals.WIDTH;
        g.setColor(Color.WHITE);
        g.fillRect(0, 0,Width,Height);
        g.setColor(Color.BLACK);

        boolean tab[][] = new boolean [Width][Height];
        BufferedImage newImage = new BufferedImage(Width, Height,BufferedImage.TYPE_INT_ARGB);


        for (int i = 0 ;i<Width; i++)
            for (int j = 0 ; j<Height ; j++)
                tab[i][j]=true ;
 


        for (Item i : IA.player.items) {
                i.fog(0,0, tab,scale );
            
        }
        int RBG =new Color(150,150,150).getRGB();
        for (int i = 0 ;i<Width; i++)
            for (int j = 0 ; j<Height ; j++)
                if (tab[i][j])
                    newImage.setRGB(i, j,RBG);
        g.drawImage(newImage,0,0,this); 
        
        for (Item i : IA.player.items){
                    i.print(g, 0, 0,scale,5);
        }
        IA.computer.base.print(g, 0, 0,scale,5);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0,Width-1,Height-1);

    }
    
}
