package universite_paris8.iut.wad.sae_dev.Modele.Utilisables;

import universite_paris8.iut.wad.sae_dev.Modele.Role;
import universite_paris8.iut.wad.sae_dev.Modele.TypeMateriaux;

public class Epee extends ObjetUtilisable {
    public Epee() {
        super("Epee", Role.ARME, TypeMateriaux.EPEE);
        this.utilisation = new UtilisationEpee();
    }
}
