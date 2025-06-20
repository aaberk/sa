package universite_paris8.iut.wad.sae_dev.Modele;

public class FlecheArc extends Projectile {

    public FlecheArc(int x, int y, int direction, Terrain terrain, int vitesse, int portee) {
        super(x, y, direction, terrain, vitesse, portee, TypeProprietaire.JOUEUR); // ✅ Flèche = joueur
    }

    @Override
    public void seDeplacer() {
        if (!estActif()) return;

        int deplacement = getVitesse() * getDirection();
        setX(getX() + deplacement);
        ajouterDistance(Math.abs(deplacement));

        // Vérification collision avec le terrain
        if (getTerrain().collision(getX(), getY()) || !peutToucher()) {
            desactiver();
            return;
        }

        // Vérification collision avec les ennemis
        for (Ennemi ennemi : getTerrain().getListeEnnemis()) {
            if (estEnCollisionAvec(ennemi)) {
                System.out.println("Ennemi touché par flèche !");
                ennemi.subirDegats(1);
                this.desactiver();
                return; // Sortie immédiate après collision
            }
        }
    }
}