package universite_paris8.iut.wad.sae_dev.Modele;


public class Pnj extends Personnage {

    private int compteurMouvement = 0;
    private final int CHANGEMENT_DIRECTION = 120;

    public Pnj(int x, int y, Terrain terrain) {
        super(x, y, 40, 50, 2, 1, terrain);
        setDirection(1);
    }

    @Override
    public void seDeplacer() {
        boolean collisionGauche = terrain.collision(getX() - vitesse, getY() + hauteur);
        boolean collisionDroite = terrain.collision(getX() + largeur + vitesse, getY() + hauteur);
        boolean bordGauche = (getX() - vitesse) < 0;
        boolean bordDroit = (getX() + largeur + vitesse) >= terrain.getLargeurPixels();

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