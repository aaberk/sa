package universite_paris8.iut.wad.sae_dev.Modele.Entites;

import universite_paris8.iut.wad.sae_dev.Modele.Terrain;

public class Joueur extends Personnage {

    private final double FORCE_SAUT = -10.0;

    public Joueur(int x, int y, Terrain terrain) {
        super(x, y, 46, 56, 5, 6, terrain);
    }

    @Override
    protected void effectuerDeplacementSpecifique() {
        switch (getDirection()) {
            case -1 -> deplacerGauche();
            case 1 -> deplacerDroite();
            // 0 = immobile
        }
    }

    public void saut() {
        if (!isDansLesAirs()) {
            if (!getTerrain().collision(getX(), getY() - 10) &&
                    !getTerrain().collision(getX() + getLargeur(), getY() - 10)) {
                setVelociteY(FORCE_SAUT);
                setDansLesAirs(true);
            }
        }
    }
}