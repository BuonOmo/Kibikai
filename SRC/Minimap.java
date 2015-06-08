import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Minimap extends JPanel {

    double scale;
    boolean fog = true;
    
    public Minimap() {

    }
    public void paint(Graphics g){
        this.addMouseListener(new MinimapMouse());
        double Height =this.getSize().getHeight();
        double Width = this.getSize().getWidth();
        scale = Width/(Finals.WIDTH);
        g.setColor(Finals.BACKGROUND_COLOR);
        g.fillRect(0, 0,(int)Width,(int)Height);
        g.setColor(Color.BLACK);

        boolean tab[][] = new boolean [(int)Width][(int)Height];
        BufferedImage newImage = new BufferedImage((int)Width, (int)Height,BufferedImage.TYPE_INT_ARGB);


        for (int i = 0 ;i<Width; i++)
            for (int j = 0 ; j<Height ; j++)
                tab[i][j]=true ;
 

        if(fog){
        for (Item i : Game.human.items) {
                i.fog(0,0, tab,scale );
        }
        }
        for (Item i : Game.computer.units){
            i.printToMinimap(g, scale, 5);
        }
        if(fog){
        int RGB =Finals.FOG_COLOR.getRGB();
        for (int i = 0 ;i<Width; i++)
            for (int j = 0 ; j<Height ; j++)
                if (tab[i][j])
                    newImage.setRGB(i, j,RGB);
        g.drawImage(newImage,0,0,this); 
        }
        
        for (Item i : IA.player.items){
                    i.printToMinimap(g,scale,5);
        }
        IA.computer.base.printToMinimap(g,scale,5);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0,(int)Width-1,(int)Height-1);
        g.setColor(Color.CYAN);
        g.drawRect((int)(Camera.cameraX*scale), (int)(Camera.cameraY*scale), (int)(Camera.cameraWidth*scale/Camera.scale), (int)(Camera.cameraHeight*scale/Camera.scale));

    }
    
    class MinimapMouse implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO corriger les positions x et y
            Camera.setLocation(e.getX() * scale / Camera.scale, e.getY() * scale / Camera.scale);
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            // TODO Implement this method
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            // TODO Implement this method
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
            // TODO Implement this method
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
            // TODO Implement this method
        }
    }
    
}
