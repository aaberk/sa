package universite_paris8.iut.wad.sae_dev.Modele.Utilisables;

import universite_paris8.iut.wad.sae_dev.Modele.Jeu;
import universite_paris8.iut.wad.sae_dev.Modele.Role;
import universite_paris8.iut.wad.sae_dev.Modele.Terraformer;
import universite_paris8.iut.wad.sae_dev.Modele.TypeMateriaux;

public abstract class ObjetUtilisable {
    private final String nom;
    private final Role role;
    private final TypeMateriaux type;
    protected UtilisationObjet utilisation;

    public ObjetUtilisable(String nom, Role role, TypeMateriaux type) {
        this.nom = nom;
        this.role = role;
        this.type = type;
    }

    public String getNom() {
        return this.nom;
    }

    public Role getRole() {
        return this.role;
    }

    public TypeMateriaux getType() {
        return this.type;
    }

    public void utiliser(int x, int y, Jeu jeu, Terraformer terraformer){
        if (this.utilisation != null){
            utilisation.utiliser(x, y, jeu, terraformer);
        }
    }

}