package universite_paris8.iut.wad.sae_dev.Modele.Entites;

import universite_paris8.iut.wad.sae_dev.Modele.Terrain;

/**
 * PnjJake : PNJ statique qui reste sur place
 * Remplace le fichier PnjJake.java existant
 */
public class PnjJake extends Pnj {

    public PnjJake(int x, int y, Terrain terrain, Joueur joueur) {
        super(x, y, terrain, joueur, 46, 60);
    }

    /**
     * Jake ne bouge pas, il reste sur place
     */
    @Override
    protected void comportementPnj() {
        // Jake reste immobile, pas de déplacement
    }

    /**
     * Calcule la distance avec le joueur
     */
    public double getDistanceAvecJoueur() {
        int joueurX = getJoueurPnj().getX();
        int joueurY = getJoueurPnj().getY();

        int dx = this.getX() - joueurX;
        int dy = this.getY() - joueurY;

        return Math.sqrt(dx * dx + dy * dy);
    }
}