package universite_paris8.iut.wad.sae_dev.Modele;

public abstract class Pnj extends Personnage {

    private int compteurMouvement = 0;
    private final int CHANGEMENT_DIRECTION = 120;

    public Pnj(int x, int y, Terrain terrain) {
        super(x, y, 40, 60, 2, 1, terrain);
        setDirection(1);
    }

    public int getCompteurMouvement() {return compteurMouvement;
    }
    public int getChangementDirection() {return CHANGEMENT_DIRECTION;}

}