package universite_paris8.iut.wad.sae_dev.Modele;

public class PnjDonut extends Pnj {

    private int limiteGauche;
    private int limiteDroite;

    public PnjDonut(int x, int y, Terrain terrain, int distancePatrouille) {
        super(x, y, terrain);
        this.limiteGauche = x - distancePatrouille;
        this.limiteDroite = x + distancePatrouille;

        // Commence par aller vers la droite
        setDirection(1);
    }

    public PnjDonut(int x, int y, Terrain terrain) {
        // Distance de patrouille par défaut de 100 pixels
        this(x, y, terrain, 30);
    }

    @Override
    public void seDeplacer() {
        if (getX() <= limiteGauche) {
            setDirection(1); // Va vers la droite
        }
        else if (getX() >= limiteDroite) {
            setDirection(-1); // Va vers la gauche
        }

        switch (getDirection()) {
            case -1 -> deplacerGauche();
            case 1 -> deplacerDroite();
        }

        appliquerGravite();
    }
}