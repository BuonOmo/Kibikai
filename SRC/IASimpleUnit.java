public class IASimpleUnit extends IAUnite {
    public SimpleUnitGroup simpleUnitGroup ;
    public IASimpleUnit(SimpleUnitGroup simpleUnitGroupToSet) {
        simpleUnitGroup=simpleUnitGroupToSet;
        super.setAll(simpleUnitGroupToSet);
    }
    public int calculateStaite() {
        return 0;
    }

    public int chooseStrategy(int staite) {
        return 0;
    }

    public void applyStrategy(int strategy) {
    }
}
