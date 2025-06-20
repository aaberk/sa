package universite_paris8.iut.wad.sae_dev.Modele;

public class Epee extends ObjetUtilisable {
    public Epee() {
        super("Épée", Role.ARME,TypeMateriaux.EPEE);
    }

    @Override
    public void utiliser(int x, int y, Terrain terrain, Terraformer terraformer) {
        System.out.println(getNom() + " utilisée pour attaquer");
        System.out.println("Épée utilisée en (" + x + ", " + y + ")");
        System.out.println("Liste ennemis : " + terrain.getListeEnnemis().size());

        for (Ennemi ennemi : terrain.getListeEnnemis()) {

            int tuileX = ennemi.getX() / terrain.getTailleTuile();
            int tuileY = ennemi.getY() / terrain.getTailleTuile();

            System.out.println("Test ennemi en : (" + tuileX + ", " + tuileY + ")");

            if (Math.abs(tuileX - x) <= 1 && Math.abs(tuileY - y) <= 1){
                ennemi.subirDegats(1);
                return;
            }

            System.out.println(ennemi.getVie());
        }

    }
}