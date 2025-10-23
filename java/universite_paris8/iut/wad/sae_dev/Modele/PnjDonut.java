package universite_paris8.iut.wad.sae_dev.Modele;

public class PnjDonut extends Pnj {

    private int limiteGauche;
    private int limiteDroite;
    private int distancePatrouille;

    public PnjDonut(int x, int y, Joueur joueur, Terrain terrain) {
        super(x, y, terrain,joueur,46,56);
        distancePatrouille = 100;
        this.limiteGauche = x - distancePatrouille;
        this.limiteDroite = x + distancePatrouille;

        // Commence par aller vers la droite
        setDirection(1);
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