package universite_paris8.iut.wad.sae_dev.Modele;

import java.util.ArrayList;
import java.util.List;

public abstract class Ennemi extends Personnage {

    private Joueur joueur;
    private int compteur = 0;

    public Ennemi(int x, int y, Terrain terrain, Joueur joueur, int largeur, int hauteur) {
        super(x, y, largeur, hauteur, 5, 2, terrain);
        this.joueur = joueur;
        setDansLesAirs(true);
    }



    public int getCompteur() { return compteur; }

    public Joueur getJoueur() { return joueur; }

    public abstract void deplacerVersJoueur(int distanceX, int distanceY);

    public boolean peutSeDeplacerHorizontalement(int nouveauX) {
        boolean dansLesLimites = nouveauX >= 0 && nouveauX + getLargeur() <= getTerrain().getLargeurPixels();
        boolean pasDeCollision = !getTerrain().collision(nouveauX, getY());
        return dansLesLimites && pasDeCollision;
    }

    public boolean peutSeDeplacerVerticalement(int nouveauY) {
        boolean dansLesLimites = nouveauY >= 0 && nouveauY + getHauteur() <= getTerrain().getHauteurPixels();
        boolean pasDeCollision = !getTerrain().collision(getX(), nouveauY);
        return dansLesLimites && pasDeCollision;
    }

    public boolean toucheJoueur() {
        int distance = 40;
        return Math.abs(getX() - joueur.getX()) < distance &&
                Math.abs(getY() - joueur.getY()) < distance;
    }

    public void subirDegats(int degats) {
        for (int i = 0; i < degats; i++) {
            retirerVie(); // méthode héritée de Personnage
        }
        System.out.println("Vie de l'ennemi après attaque : " + getVie());
    }


}