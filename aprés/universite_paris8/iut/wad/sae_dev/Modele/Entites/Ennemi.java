package universite_paris8.iut.wad.sae_dev.Modele.Entites;

import universite_paris8.iut.wad.sae_dev.Modele.Terrain;

/**
 * Classe abstraite pour les ennemis
 * Utilise aussi le Template Method de Personnage
 */
public abstract class Ennemi extends Personnage {

    private Joueur joueur;
    private int compteur = 0;

    public Ennemi(int x, int y, Terrain terrain, Joueur joueur, int largeur, int hauteur) {
        super(x, y, largeur, hauteur, 5, 2, terrain);
        this.joueur = joueur;
    }

    /**
     * Implémentation de la méthode abstraite du template
     * Définit le comportement commun à tous les ennemis
     */
    @Override
    protected void effectuerDeplacementSpecifique() {
        compteur++;

        if (compteur > 1000) {
            compteur = 0;
        }

        comportementEnnemi();
    }

    /**
     * Méthode abstraite : chaque type d'ennemi a son propre comportement
     */
    protected abstract void comportementEnnemi();

    // === MÉTHODES UTILITAIRES POUR LES ENNEMIS ===

    public boolean peutSeDeplacerHorizontalement(int nouveauX) {
        boolean dansLesLimites = nouveauX >= 0 && nouveauX + getLargeur() <= getTerrain().largeurEnPixels();
        if (!dansLesLimites) return false;

        int y = getY();
        int hauteur = getHauteur();

        boolean pasDeCollisionPieds = !getTerrain().collision(nouveauX, y + hauteur) &&
                !getTerrain().collision(nouveauX + getLargeur(), y + hauteur);

        boolean pasDeCollisionTorse = !getTerrain().collision(nouveauX, y + hauteur/2) &&
                !getTerrain().collision(nouveauX + getLargeur(), y + hauteur/2);

        boolean pasDeCollisionTete = !getTerrain().collision(nouveauX, y) &&
                !getTerrain().collision(nouveauX + getLargeur(), y);

        return pasDeCollisionPieds && pasDeCollisionTorse && pasDeCollisionTete;
    }

    public boolean peutSeDeplacerVerticalement(int nouveauY) {
        boolean dansLesLimites = nouveauY >= 0 && nouveauY + getHauteur() <= getTerrain().getHauteurPixels();
        if (!dansLesLimites) return false;

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
            retirerVie();
        }
        System.out.println("Vie de l'ennemi après attaque : " + getVie());
    }

    // === GETTERS ===

    public int getCompteur() {
        return compteur;
    }

    public Joueur getJoueur() {
        return joueur;
    }
}