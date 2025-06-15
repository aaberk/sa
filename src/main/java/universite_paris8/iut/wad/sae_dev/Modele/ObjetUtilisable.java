package universite_paris8.iut.wad.sae_dev.Modele;

public abstract class ObjetUtilisable {

    private final String nom;
    private final Role role;

    public ObjetUtilisable(String nom, Role role) {
        this.nom = nom;
        this.role = role;
    }

    public String getNom () {
        return this.nom;
    }

    public Role getRole() {
        return this.role;
    }

    public abstract void utiliser ();
}
