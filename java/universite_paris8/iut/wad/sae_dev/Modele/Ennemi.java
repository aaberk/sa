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

    public int getCompteur() {
        return compteur;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public abstract void deplacerVersJoueur(int distanceX, int distanceY);

    /**
     * Méthode de base pour vérifier le déplacement horizontal
     * Peut être surchargée par les classes filles pour des comportements spécifiques
     */
    public boolean peutSeDeplacerHorizontalement(int nouveauX) {
        // Vérifier les limites du terrain
        boolean dansLesLimites = nouveauX >= 0 && nouveauX + getLargeur() <= getTerrain().getLargeurPixels();

        if (!dansLesLimites) {
            return false;
        }

        // Vérifier les collisions sur plusieurs points de la hauteur
        int y = getY();
        int hauteur = getHauteur();

        // Vérifier collision au niveau des pieds (point le plus important)
        boolean pasDeCollisionPieds = !getTerrain().collision(nouveauX, y + hauteur) &&
                !getTerrain().collision(nouveauX + getLargeur(), y + hauteur);

        // Vérifier collision au niveau du torse
        boolean pasDeCollisionTorse = !getTerrain().collision(nouveauX, y + hauteur/2) &&
                !getTerrain().collision(nouveauX + getLargeur(), y + hauteur/2);

        // Vérifier collision au niveau de la tête
        boolean pasDeCollisionTete = !getTerrain().collision(nouveauX, y) &&
                !getTerrain().collision(nouveauX + getLargeur(), y);

        return pasDeCollisionPieds && pasDeCollisionTorse && pasDeCollisionTete;
    }

    public boolean peutSeDeplacerVerticalement(int nouveauY) {
        boolean dansLesLimites = nouveauY >= 0 && nouveauY + getHauteur() <= getTerrain().getHauteurPixels();

        if (!dansLesLimites) {
            return false;
        }

        // Vérifier les collisions sur toute la largeur
        boolean pasDeCollision = !getTerrain().collision(getX(), nouveauY) &&
                !getTerrain().collision(getX() + getLargeur(), nouveauY) &&
                !getTerrain().collision(getX(), nouveauY + getHauteur()) &&
                !getTerrain().collision(getX() + getLargeur(), nouveauY + getHauteur());

        return pasDeCollision;
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