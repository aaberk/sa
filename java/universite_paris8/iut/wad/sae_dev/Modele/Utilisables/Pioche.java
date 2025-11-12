package universite_paris8.iut.wad.sae_dev.Modele.Utilisables;

import universite_paris8.iut.wad.sae_dev.Modele.Role;
import universite_paris8.iut.wad.sae_dev.Modele.TypeMateriaux;

public class Pioche extends ObjetUtilisable {
    public Pioche() {
        super("Pioche", Role.OUTIL, TypeMateriaux.PIOCHE);
        this.utilisation = new UtilisationPioche();
    }
}
