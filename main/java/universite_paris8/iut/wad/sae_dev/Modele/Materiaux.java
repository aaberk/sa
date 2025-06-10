
package universite_paris8.iut.wad.sae_dev.Modele;


public class Materiaux {
    private TypeMateriaux nom;
    private Role role;

    public Materiaux(TypeMateriaux nom, Role role) {
        this.nom = nom;
        this.role = role;
    }

    public TypeMateriaux getNom() {
        return nom;
    }

    public void setNom(TypeMateriaux nom) {
        this.nom = nom;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}