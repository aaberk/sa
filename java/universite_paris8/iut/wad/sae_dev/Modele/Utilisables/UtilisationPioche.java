package universite_paris8.iut.wad.sae_dev.Modele.Utilisables;

import universite_paris8.iut.wad.sae_dev.Modele.Jeu;
import universite_paris8.iut.wad.sae_dev.Modele.Terraformer;

public class UtilisationPioche implements UtilisationObjet {

    @Override
    public void utiliser(int x, int y, Jeu jeu, Terraformer terraformer) {
        terraformer.casserUnBloc(x, y);
    }
}
