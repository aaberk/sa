package universite_paris8.iut.wad.sae_dev.Modele.Entites;

import universite_paris8.iut.wad.sae_dev.Modele.Terrain;

/**
 * PnjDonut : PNJ qui patrouille entre deux points
 * Remplace le fichier PnjDonut.java existant
 */
public class PnjDonut extends Pnj {

    private int limiteGauche;
    private int limiteDroite;
    private int distancePatrouille;

    public PnjDonut(int x, int y, Joueur joueur, Terrain terrain) {
        super(x, y, terrain, joueur, 46, 56);
        distancePatrouille = 100;
        this.limiteGauche = x - distancePatrouille;
        this.limiteDroite = x + distancePatrouille;
        setDirection(1);
    }

    /**
     * Comportement de patrouille : va et vient entre deux points
     */
    @Override
    protected void comportementPnj() {
        // Changement de direction aux limites
        if (getX() <= limiteGauche) {
            setDirection(1); // Va vers la droite
        }
        else if (getX() >= limiteDroite) {
            setDirection(-1); // Va vers la gauche
        }

        // Déplacement selon la direction
        switch (getDirection()) {
            case -1 -> deplacerGauche();
            case 1 -> deplacerDroite();
        }
    }
}