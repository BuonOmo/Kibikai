
import java.awt.geom.Point2D;

public class Camera extends Object{
    
    Canvas gc;
    double screenWidth;
    
    /**
     * the x-position of our "camera" in pixel
     */
    protected double cameraX;

    /**
     * the y-position of our "camera" in pixel
     */
    protected double cameraY;
    
    protected Point2D.Double currentCenterPoint;

    /**
     * Create a new camera
     *
     */
    public Camera(Canvas c) {
    	gc = c;
    }

    /**
     * "locks" the camera on the given coordinates. The camera tries to keep the
     * location in its center.
     *
     * @param x the real x-coordinate (in pixel) which should be centered on the
     * screen
     * @param y the real y-coordinate (in pixel) which should be centered on the
     * screen
     * @return
     */
    public Point2D.Double centerOn(double x, double y) {
        //try to set the given position as center of the camera by default
        cameraX = x - screenWidth / 2;
        cameraY = y - gc.getHeight() / 2;

        //if the camera is at the right or left edge lock it to prevent a black bar
        if (cameraX < 0) {
            cameraX = 0;
        }
        if (cameraX + gc.getWidth() > Finals.WIDTH) {
            cameraX = Finals.WIDTH - gc.getWidth();
        }

        //if the camera is at the top or bottom edge lock it to prevent a black bar
        if (cameraY < 0) {
            cameraY = 0;
        }
        if (cameraY + gc.getHeight() > Finals.HEIGTH) {
            cameraY = Finals.HEIGTH - gc.getHeight();
        }

        currentCenterPoint.setLocation(cameraX, cameraY);
        return currentCenterPoint;
    }
    
    public void centerOn(Point2D p){
        centerOn(p.getX(), p.getY());
    }

    /**
     * "locks" the camera on the center of the given Rectangle. The camera tries
     * to keep the location in it's center.
     *
     * @param x the x-coordinate (in pixel) of the top-left corner of the
     * rectangle
     * @param y the y-coordinate (in pixel) of the top-left corner of the
     * rectangle
     * @param height the height (in pixel) of the rectangle
     * @param width the width (in pixel) of the rectangle
     */
    public void centerOn(double x, double y, double height, double width) {
        this.centerOn(x + width / 2, y + height / 2);
    }
    
    /**
     * "locks the camera on the center of the given Shape. The camera tries to
     * keep the location in it's center.
     *
     * @param shape the Shape which should be centered on the screen
     */
    public void centerOn(Item item) {
        this.centerOn(item.getCenter());
    }

    /**
     * draws the part of the map which is currently focused by the camera on
     * the screen
     */
    public void drawMap(int layer) {
        this.drawMap(0, 0, layer);
    }

    /**
     * draws the part of the map which is currently focused by the camera on
     * the screen.<br>
     * You need to draw something over the offset, to prevent the edge of the
     * map to be displayed below it<br>
     * Has to be called before Camera.translateGraphics() !
     *
     * @param offsetX the x-coordinate (in pixel) where the camera should start
     * drawing the map at
     * @param offsetY the y-coordinate (in pixel) where the camera should start
     * drawing the map at
     */
    public void drawMap(int offsetX, int offsetY, int layer) {
        
        //draw the section of the map on the screen
        
    }

}
