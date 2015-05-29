import java.awt.Color;
import java.awt.geom.Point2D;

import java.util.LinkedList;




public abstract class Unit extends Item {
    
    public LinkedList <IAHistObj> histoList = new LinkedList <IAHistObj>();
    double lifeMAX;
    double firstAppearance;
    int strategyincurs =0; 
    
    /**
     * @param owner
     * @param topLeftCorner
     * @param width
     * @param height
     */
    public Unit(Player owner, Point2D topLeftCorner, double lifeMAXToSet, int width, int height){
        super(owner, topLeftCorner, width, height);
        lifeMAX = lifeMAXToSet;
        owner.units.add(this);
        firstAppearance = UI.time;
    }   
    
    /**
     * @param owner Possesseur de l’objet
     * @param topLeftCorner
     * @param side coté de la hitBox
     */
    public Unit(Player owner, Point2D topLeftCorner, double lifeMAXToSet, int side){
        this(owner, topLeftCorner, lifeMAXToSet, side, side);
    }
    
    /**
     * @param owner Possesseur de l’objet
     * @param topLeftCorner
     * @param side coté de la hitBox
     */
    public Unit(Player owner, Point2D topLeftCorner, double lifeMAXToSet, int side, Point2D targetToSet){
        this(owner, topLeftCorner, lifeMAXToSet, side);
        setTarget(targetToSet);
    }
    
    //________________MÉTHODES_______________//
   
    /**
     * arrête le mouvement d’une unité.
     */
    public void stop(){
        target = this.getCenter();
    }
    
    /**
     * Gère la vie d’une unité.
     * @param amount vie ajoutée (- pour en enlever)
     */
    public boolean getLife(double amount){
        life+= amount;
        if (life <=0)
            return this.isDestructed();
        else  if (life >= lifeMAX)
            life = lifeMAX;
        return false;
    }
    
    @Override
    public Color getColor(){
        if (selected)
            return new Color(0,255,255,100);
        if (Listeners.louHammel)
            return new Color((int) (255.0 * Math.random()),
                             (int) (255.0 * Math.random()),
                             (int) (255.0 * Math.random()));
        double percent = ((life+lifeMAX/2.0)/(1.5*lifeMAX));
        return new Color((int)(color.getRed()*percent), (int)(color.getGreen()*percent), (int)(color.getBlue()*percent), color.getAlpha());
    }
    
    /**
     * Gère le déplacement d’une unité.
     */
    public void move(){
        // deplace l’objet de la distance renvoyée par canMove
        Point2D d;
        d = new Point2D.Double();
        d.setLocation(setVector());
        hitBox.setFrame(hitBox.getX() + d.getX(), 
                       hitBox.getY() + d.getY(), 
                       hitBox.getWidth(), 
                       hitBox.getHeight() );
    }
    
    
    public abstract boolean execute();
    
    //________MÉTHODES POUR LE DÉPLACEMENT______//
    
    /**
     * permet de trouver le point d’arrivée du déplacement en fonction 
     * d’un angle alpha par rapport au vecteur unité/objectif.
     * @param alpha angle du déplacement par rapport à la droite Objet-Cible
     * @return point d’arrivé du déplacement
     */
    
    public Point2D getShortTarget(double alpha){
        
        Point2D shortTarget;
        shortTarget = new Point2D.Double();
        
        double x, y;
        x = (double) (target.getX() - hitBox.getCenterX()) * (double) DISTANCE_TO_MOVE / this.distanceTo(target);
        y = (double) (target.getY() - hitBox.getCenterY()) * (double) DISTANCE_TO_MOVE / this.distanceTo(target);
        
        shortTarget.setLocation( Math.cos(alpha + getAlphaOffset())*x+hitBox.getCenterX(), Math.sin(alpha)*y+hitBox.getCenterY());
        return shortTarget;
    }
    
    
    /**
     * permet de trouver le vecteur du déplacement en fonction 
     * d’un angle alpha par rapport au vecteur unité/objectif.
     * @param alpha angle du déplacement par rapport à la droite Objet-Cible
     * @return point d’arrivé du déplacement
     */
    private Point2D getVector(double alphaDegre){
        double alpha;
        alpha = Math.toRadians(alphaDegre);
        Point2D vector;
        vector = new Point2D.Double();
        double x, y;
        if (distanceTo(target)>DISTANCE_TO_MOVE){
            x = (double) (target.getX() - hitBox.getCenterX()) * DISTANCE_TO_MOVE / this.distanceTo(target); 
            y = (double) (target.getY() - hitBox.getCenterY()) * DISTANCE_TO_MOVE / this.distanceTo(target);
        }
        else{
            x = 0; 
            y = 0;
        }
        if (distanceTo(target)>DISTANCE_TO_MOVE)
            vector.setLocation(DISTANCE_TO_MOVE*Math.cos(getAlphaOffset()- alpha), // ça merde encore
                               DISTANCE_TO_MOVE*Math.sin(getAlphaOffset()- alpha));
        else 
            vector.setLocation(0, 0);
        return vector;
    }
    
    /**
     * permet de trouver le vecteur unitaire de déplacement en fonction 
     * d’un angle alpha par rapport au vecteur unité/objectif.
     * @return vecteur de déplacement unitaire
     */
    private Point2D getVector(){
        
        // evite le tremblement
        if (distanceTo(target)>DISTANCE_TO_MOVE)
            return new Point2D.Double( (target.getX() - hitBox.getCenterX()) * DISTANCE_TO_MOVE / this.distanceTo(target), 
                                       (target.getY() - hitBox.getCenterY()) * DISTANCE_TO_MOVE / this.distanceTo(target));
        else
            return new Point2D.Double(0, 0);
    }
    
    Point2D setVector(){
        LinkedList<Item> obstacle;
        obstacle = new LinkedList<Item>(aliveItems);
        obstacle.remove(this);
        /*
        for (Item i : obstacle){
            if (intersect(i))
                System.out.println("il y a une intersection");
                return getVector(90);
        }
        */
        return getVector(30);
    }
    /**
     * Donne les deux points possible de déplacement de l’unité en fonction d’un Item qui fait obstacle.
     * @param other Un autre item qui est proche du premier (methode a n'utiliser que si il y a intersection, 
     * sinon tableau vide renvoyé).
     * @return tableau de Point 2D contenant les intersections entres les deux cerles entourant les items.
     */
    public Point2D.Double[] getIntersect(Item other){
                        
        double x0 = this.getCenter().getX();
        double y0 = this.getCenter().getY();
        double x1 = other.getCenter().getX();
        double y1 = other.getCenter().getY();
        /*
        double x0Coin = this.hitBox.getX();
        double y0Coin = this.hitBox.getY();
        double x1Coin = other.hitBox.getX();
        double y1Coin = other.hitBox.getY();
        */
        
        //double R0 = Math.sqrt((x0Coin-x0)*(x0Coin-x0)+(y0Coin-y0)*(y0Coin-y0));
        double R0 = DISTANCE_TO_MOVE;
        //double R1 = Math.sqrt((x1Coin-x1)*(x1Coin-x1)+(y1Coin-y1)*(y1Coin-y1));
        double R1 = radius + other.radius;
        
        double deltaX = x0-x1;
        double deltaY = y0-y1;
        
        double Xa;
        double Ya;
        double Xb;
        double Yb;
        
        if(y1!=y0){
             
            double N = (R1*R1-R0*R0-x1*x1+x0*x0-y1*y1+y0*y0)/(2*deltaY);
            double A = (deltaX/deltaY)*(deltaX/deltaY)+1;
            double B = 2*(y0-N)*(deltaX/deltaY)-2*x0;
            double C = y0*y0+x0*x0+N*N-R0*R0-2*y0*N;
             
            double DELTA = B*B-4*A*C;
             
            if(DELTA>0){ //mettre >= si on veut qu’il retourne lorsqu’il y a une seule intersection
                DELTA = Math.sqrt(DELTA);
                Xa=(-B-DELTA)/(2*A);
                Ya=N-Xa*(deltaX/deltaY);
                Xb=(-B+DELTA)/(2*A);
                Yb=N-Xb*(deltaX/deltaY);
                Point2D.Double[] Intersects = {new Point2D.Double(Xa,Ya) , new Point2D.Double(Xb,Yb)};
                return Intersects;
            }
            else{
                return null;
            }
             
        }
        else{
            Xa=(R1*R1-R0*R0-x1*x1+x0*x0)/(2*deltaX);        
            double A = 1;
            double B = -2*y1;
            double C = x1*x1+Xa*Xa-2*x1*Xa-R1*R1;
            double DELTA = B*B - 4*A*C;
            if(DELTA>0){ //mettre >= si on veut qu’il retourne lorsqu’il y a une seule intersection
                DELTA = Math.sqrt(DELTA);
                Ya=(-B-DELTA)/(2*A);
                Yb=(-B+DELTA)/(2*A);
                Point2D.Double[] Intersects = {new Point2D.Double(Xa,Ya) , new Point2D.Double(Xa,Yb)};
                return Intersects;
            }
            else{
                return null;
            } 
        }       
    }


    /**
     * Vérifie s’il y a intersection entre un Item et la nouvelle position (fictive) de l’unité.
     * @param other autre Item
     * @return true s’il y a intersection
     */
    public boolean intersect(Item other){
        
        if (DISTANCE_TO_MOVE + radius + other.radius > distanceTo(other))
            return true;
        
        return false;
        
    }
    
    /**
     * @param shortTarget
     * @return angle du déplacement par rapport à la droite Objet-Cible
     */
    public double findAngle(Point2D shortTarget){
        if (shortTarget == null)
            return 0.0;
        Point2D zero;
        zero = getShortTarget(0.0);
        zero.setLocation(zero.getX()-hitBox.getCenterX(), zero.getY()-hitBox.getCenterY());

        Point2D sT;
        sT = new Point2D.Double(shortTarget.getX() - hitBox.getCenterX(),
        			shortTarget.getY() - hitBox.getCenterY());

        return Math.acos(((zero.getX()-hitBox.getCenterX())*(sT.getX()-hitBox.getCenterX()) + 
                          (zero.getY() - hitBox.getCenterY())*(sT.getY() - hitBox.getCenterY()))
                         /(DISTANCE_TO_MOVE*DISTANCE_TO_MOVE));
    }
        
    public double getAlphaOffset(){
        Point2D vect = getVector();
        double offset;
        offset = Math.atan(vect.getY() / vect.getX());
        if (vect.getX()<0)
            return offset + Math.PI;
        return offset;
    }
    
    /**
     * @param obstacle liste de tout les obstacles possible
     * @param shortTarget vecteur unitaire de déplacement
     * @return l’unitée peut faire un déplacement unitaire
     */
    public Item findObstacle(LinkedList<Item> obstacle,Point2D shortTarget){

        for(Item element : obstacle)
            if (this.intersect(element))
                return element;
        
        return null;
    }

    /**
     * Vérifie si l’unité peut se déplacer.
     * @return point d’arrivée du déplacement unitaire (null si l’unité est coincée
     */
    public Point2D canMove(){
                
        // angle de déplacement positif minimum
        double alpha;
        alpha = 0;

        // angle de déplacement négatif minimum
        double beta;
        beta = 0;
        
        // liste des obstacles à contourner
        LinkedList<Item> obstacle;
        obstacle = new LinkedList<Item>(aliveItems);
        obstacle.remove(this);
        
        // vecteur unitaire de déplacement
        Point2D shortTarget;
        
        // obstacle rencontré
        Item toAvoid;
        
        // variable temporaire pour simplifier les calculs
        double temp;
        
        do{            
            // setShortTarget pour le minimum des deux angles
            shortTarget = getShortTarget((alpha < Math.abs(beta)) ? alpha : beta);
            
            toAvoid = findObstacle(obstacle, shortTarget);
            
            if (toAvoid == null)
                return shortTarget;
            
            // enleve l’obstacle de la liste des obstacles une fois qu’il a été traité
            obstacle.remove(toAvoid);
            
            //setAlpha et setBeta
            Point2D[] intersection = getIntersect(toAvoid);
            for (int i=0 ; i<2 ;i++){
                // les deux angles possible de contournement de l’obstacle
                temp = findAngle(intersection[i]);
                
                beta = (temp < beta) ? temp : beta;
                alpha = (temp > alpha) ? temp : alpha;
            }
            
        }while(alpha < 180.0 || beta > -180.0);  // mettre un "et" logique ici ?
        
        // impossible de se déplacer
        return null;
    }
}
