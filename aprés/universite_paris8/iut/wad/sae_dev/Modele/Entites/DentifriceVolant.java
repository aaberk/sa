package universite_paris8.iut.wad.sae_dev.Modele.Entites;

import universite_paris8.iut.wad.sae_dev.Modele.Terrain;

/**
 * DentifriceVolant : ennemi volant qui suit le joueur horizontalement
 */
public class DentifriceVolant extends Ennemi {

    private int vitesseTir = 0;
    private int distanceDetection = 200;
    private int distanceTir = 150;

    public DentifriceVolant(int x, int y, Terrain terrain, Joueur joueur) {
        super(x, y, terrain, joueur, 46, 300);
    }

    /**
     * Implémentation du comportement spécifique du DentifriceVolant
     */
    @Override
    protected void comportementEnnemi() {
        vitesseTir++;

        if (getCompteur() >= 8) {
            int distanceX = calculerDistanceX();
            int distanceY = calculerDistanceY();

            deplacerVersJoueur(distanceX, distanceY);

            // Message de debug pour vérifier le tir
            if (estJoueurProche(distanceX, distanceY) && doitTirer(distanceX, distanceY)) {
                System.out.println("Drone tire un projectile!");
            }
        }
    }

    // === MÉTHODES SPÉCIFIQUES AU DENTIFRICE ===

    private int calculerDistanceX() {
        return getJoueur().getX() - getX();
    }

    private int calculerDistanceY() {
        return getJoueur().getY() - getY();
    }

    private boolean estJoueurProche(int distanceX, int distanceY) {
        int distanceAbsX = Math.abs(distanceX);
        int distanceAbsY = Math.abs(distanceY);

        return distanceAbsX <= distanceDetection && distanceAbsY <= distanceDetection;
    }

    private boolean doitTirer(int distanceX, int distanceY) {
        int distanceAbsX = Math.abs(distanceX);
        return distanceAbsX <= distanceTir && vitesseTir >= 60;
    }

    private void deplacerVersJoueur(int distanceX, int distanceY) {
        int nouveauX = getX();

        if (distanceX > 10) {
            nouveauX += getVitesse();
            setDirection(1);
        } else if (distanceX < -10) {
            nouveauX -= getVitesse();
            setDirection(-1);
        } else {
            setDirection(0);
        }

        if (peutSeDeplacerHorizontalement(nouveauX)) {
            setX(nouveauX);
        }
    }

    @Override
    public boolean peutSeDeplacerHorizontalement(int nouveauX) {
        boolean dansLesLimites = nouveauX >= 0 && nouveauX + getLargeur() <= getTerrain().largeurEnPixels();

        boolean pasDeCollisionLaterale = true;
        if (nouveauX < getX()) {
            pasDeCollisionLaterale = !getTerrain().collision(nouveauX, getY() + getHauteur() / 2);
        }
        else if (nouveauX > getX()) {
            pasDeCollisionLaterale = !getTerrain().collision(nouveauX + getLargeur(), getY() + getHauteur() / 2);
        }

        return dansLesLimites && pasDeCollisionLaterale;
    }

    public boolean peutTirer() {
        return vitesseTir >= 100;
    }

    public void reinitialiserCompteurTir() {
        vitesseTir = 0;
    }

    public int getDirectionTir() {
        return 0;
    }

    public int getPositionTirX() {
        return getX() + getLargeur() / 2;
    }

    public int getPositionTirY() {
        return getY() + getHauteur();
    }
}