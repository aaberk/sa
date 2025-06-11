package universite_paris8.iut.wad.sae_dev.Modele;

/**
 * Cette classe représente un joueur et hérite de Personnage.
 * Elle ajoute des fonctionnalités spécifiques au joueur comme le saut et la sélection de blocs.
 */
public class Joueur extends Personnage {

    private final double FORCE_SAUT = -10.0;
    private int blocSelectionne = 2;

    public Joueur(int x, int y, Terrain terrain) {
        super(x, y, 46, 56, 5, 3, terrain);
    }

    @Override
    public void seDeplacer() {
        switch (getDirection()) {
            case -1 -> deplacerGauche();
            case 1 -> deplacerDroite();
            // 0 = immobile
        }
        appliquerGravite();
    }

    public void saut() {
        if (!dansLesAirs) {
            if (!terrain.collision(getX(), getY() - 10) &&
                    !terrain.collision(getX() + largeur, getY() - 10)) {
                velociteY = FORCE_SAUT;
                dansLesAirs = true;
            }
        }
    }

    public int getBlocSelectionne() {
        return blocSelectionne;
    }

    public void setBlocSelectionne(int bloc) {
        this.blocSelectionne = bloc;
        System.out.println("Bloc sélectionné : " + blocSelectionne);
    }
}