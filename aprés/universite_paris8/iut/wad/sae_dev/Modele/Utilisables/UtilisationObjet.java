package universite_paris8.iut.wad.sae_dev.Modele.Utilisables;

import universite_paris8.iut.wad.sae_dev.Modele.Jeu;
import universite_paris8.iut.wad.sae_dev.Modele.Terraformer;

public interface UtilisationObjet {
    void utiliser(int x, int y, Jeu jeu, Terraformer terraformer);
    }
