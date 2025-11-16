package universite_paris8.iut.wad.sae_dev.Modele.Entites;

import universite_paris8.iut.wad.sae_dev.Modele.Terrain;

/**
 * Classe abstraite pour les PNJ (Personnages Non Joueurs)
 * Utilise le Template Method de Personnage
 */
public abstract class Pnj extends Personnage {

    private Joueur joueur;

    public Pnj(int x, int y, Terrain terrain, Joueur joueur, int largeur, int hauteur) {
        super(x, y, largeur, hauteur, 2, 1, terrain);
        this.joueur = joueur;
        setDirection(1);
    }

    /**
     * Implémentation de la méthode abstraite
     * Les PNJ ont un comportement simple par défaut
     */
    @Override
    protected void effectuerDeplacementSpecifique() {
        // Comportement par défaut : ne rien faire
        comportementPnj();
    }

    /**
     * Méthode abstraite pour le comportement spécifique de chaque PNJ
     */
    protected abstract void comportementPnj();

    /**
     * Vérifie si le joueur est proche du PNJ
     */
    public boolean joueurEstProche() {
        if (joueur == null) return false;

        int distance = Math.abs(this.getX() - joueur.getX());
        return distance < 100;
    }

    protected Joueur getJoueurPnj() {
        return joueur;
    }
}