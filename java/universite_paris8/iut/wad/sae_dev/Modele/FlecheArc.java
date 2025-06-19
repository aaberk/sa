package universite_paris8.iut.wad.sae_dev.Modele;


public class FlecheArc extends Projectile {


    public FlecheArc(int x, int y, int direction, Terrain terrain, int vitesse, int portee) {
        super(x, y, direction, terrain, vitesse, portee); // direction = 0 = vertical
    }

    @Override
    public void seDeplacer() {
        if (!estActif()) return;

        int deplacement = getVitesse() * getDirection();
        setX(getX() + deplacement);
        ajouterDistance(Math.abs(deplacement));

        if (getTerrain().collision(getX(), getY()) || !peutToucher()) {
            desactiver();
        }
        for (Ennemi ennemi : getTerrain().getListeEnnemis()) {
            if (estEnCollisionAvec(ennemi)) {
                System.out.println(" Ennemi touché !");
                ennemi.subirDegats(1);
                this.desactiver();
                break;
            }

        }

    }

    public boolean toucheEnnemi(Ennemi ennemi) {
        if (!estActif()) return false;

        boolean collision = estEnCollisionAvec(ennemi);
        System.out.println(" Collision test : " + collision + " avec ennemi en (" + ennemi.getX() + ", " + ennemi.getY() + ")");

        if (collision) {
            ennemi.retirerVie();  // inflige 1 point de dégât
            desactiver();
        }

        return collision;
    }



}
