package universite_paris8.iut.wad.sae_dev.Modele.Utilisables;

import universite_paris8.iut.wad.sae_dev.Modele.Role;
import universite_paris8.iut.wad.sae_dev.Modele.TypeMateriaux;

public class Arc extends ObjetUtilisable {
    public Arc() {
        super("Arc", Role.ARME, TypeMateriaux.ARC);
        this.utilisation = new UtilisationArc();
    }
}
