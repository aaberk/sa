package universite_paris8.iut.wad.sae_dev.Modele.Utilisables;

import universite_paris8.iut.wad.sae_dev.Modele.Entites.Ennemi;
import universite_paris8.iut.wad.sae_dev.Modele.Jeu;
import universite_paris8.iut.wad.sae_dev.Modele.Terraformer;
import universite_paris8.iut.wad.sae_dev.Modele.Terrain;

public class UtilisationEpee implements UtilisationObjet {
    @Override
    public void utiliser(int x, int y, Jeu jeu, Terraformer terraformer) {
        Ennemi ennemiCible = rechercherEnnemiAPortee(x, y, jeu);

        if (ennemiCible != null) {
            attaquerEnnemi(ennemiCible);
        }
    }

    private Ennemi rechercherEnnemiAPortee(int x, int y, Jeu jeu) {
        Terrain terrain = jeu.getTerrain();

        for (Ennemi ennemi : jeu.getListeEnnemis()) {
            int tuileX = ennemi.getX() / terrain.getTailleTuile();
            int tuileY = ennemi.getY() / terrain.getTailleTuile();

            if (estAPortee(tuileX, tuileY, x, y)) {
                return ennemi;
            }
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
