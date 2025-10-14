package universite_paris8.iut.wad.sae_dev.Modele.Entites;

import universite_paris8.iut.wad.sae_dev.Modele.Terrain;

public class PnjJake extends Pnj {

    private Joueur joueur;

    public PnjJake(int x, int y, Terrain terrain, Joueur joueur) {
        super(x, y, terrain, joueur,46,60);
        this.joueur = joueur;
    }

    public double getDistanceAvecJoueur() {
        int joueurX = joueur.getX();
        int joueurY = joueur.getY();

        int dx = this.getX() - joueurX;
        int dy = this.getY() - joueurY;

        return Math.sqrt(dx * dx + dy * dy);
    }
}
