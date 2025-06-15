package universite_paris8.iut.wad.sae_dev.Modele;

public class Jake extends Personnage {

    private int compteurMouvement = 0;
    private final int CHANGEMENT_DIRECTION = 120;

    public Jake(int x, int y, Terrain terrain) {
        super(x, y, 40, 50, 2, 1, terrain);
        setDirection(1);
    }

    @Override
    public void seDeplacer() {
        boolean collisionGauche = getTerrain().collision(getX() - getVitesse(), getY() + getHauteur());
        boolean collisionDroite = getTerrain().collision(getX() + getLargeur() + getVitesse(), getY() + getHauteur());
        boolean bordGauche = (getX() - getVitesse()) < 0;
        boolean bordDroit = (getX() + getLargeur() + getVitesse()) >= getTerrain().getLargeurPixels();

        if ((getDirection() == -1 && (collisionGauche || bordGauche)) ||
                (getDirection() == 1 && (collisionDroite || bordDroit))) {
            setDirection(-getDirection());
        }

        compteurMouvement++;
        if (compteurMouvement >= CHANGEMENT_DIRECTION) {
            setDirection(-getDirection());
            compteurMouvement = 0;
        }

        switch (getDirection()) {
            case -1 -> deplacerGauche();
            case 1 -> deplacerDroite();
        }

        appliquerGravite();
    }
}