public abstract class Unit extends Item {
    /**
     * @param owner
     * Détenteur de l’unité
     * @param locationToSet
     * Position initiale de l’unité
     */
    public Unit(Player owner, Location locationToSet){
        super(owner, locationToSet);
    }
    
    /**
     * Permet de déplacer une unité vers un point donné.
     *
     * @param location
     * Point d’arrivée de l’unité
     */
    public abstract void moveTo(Location location);
}
