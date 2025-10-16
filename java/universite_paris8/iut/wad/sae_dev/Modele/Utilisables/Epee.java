package universite_paris8.iut.wad.sae_dev.Modele.Utilisables;

import universite_paris8.iut.wad.sae_dev.Modele.Entites.Ennemi;
import universite_paris8.iut.wad.sae_dev.Modele.Role;
import universite_paris8.iut.wad.sae_dev.Modele.Terraformer;
import universite_paris8.iut.wad.sae_dev.Modele.Terrain;
import universite_paris8.iut.wad.sae_dev.Modele.TypeMateriaux;

public class Epee extends ObjetUtilisable {

    public Epee() {
        super("Épée", Role.ARME, TypeMateriaux.EPEE);
    }

    @Override
    public void utiliser(int x, int y, Terrain terrain, Terraformer terraformer) {
        System.out.println(getNom() + " utilisée pour attaquer");
        System.out.println("Épée utilisée en (" + x + ", " + y + ")");
        System.out.println("Liste ennemis : " + terrain.getListeEnnemis().size());

        Ennemi ennemiCible = rechercherEnnemiAPortee(x, y, terrain);

        if (ennemiCible != null) {
            attaquerEnnemi(ennemiCible);
        }
    }

    private Ennemi rechercherEnnemiAPortee(int x, int y, Terrain terrain) {
        for (Ennemi ennemi : terrain.getListeEnnemis()) {
            int tuileX = ennemi.getX() / terrain.getTailleTuile();
            int tuileY = ennemi.getY() / terrain.getTailleTuile();

            System.out.println("Test ennemi en : (" + tuileX + ", " + tuileY + ")");

            if (estAPortee(tuileX, tuileY, x, y)) {
                return ennemi;
            }

            System.out.println(ennemi.getVie());
        }
        return null;
    }

    private boolean estAPortee(int tuileEnnemiX, int tuileEnnemiY, int tuileAttaqueX, int tuileAttaqueY) {
        return Math.abs(tuileEnnemiX - tuileAttaqueX) <= 1 &&
                Math.abs(tuileEnnemiY - tuileAttaqueY) <= 1;
    }

    private void attaquerEnnemi(Ennemi ennemi) {
        ennemi.subirDegats(1);
    }
}