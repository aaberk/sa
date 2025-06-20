package universite_paris8.iut.wad.sae_dev.Modele;

public abstract class ObjetUtilisable {

    private final String nom;
    private final Role role;
    private final TypeMateriaux type;

    public ObjetUtilisable(String nom, Role role, TypeMateriaux type) {
        this.nom = nom;
        this.role = role;
        this.type = type;
    }

    public String getNom () {
        return this.nom;
    }

    public Role getRole() {
        return this.role;
    }

    public TypeMateriaux getType() {
        return this.type;
    }

    public abstract void utiliser (int x, int y, Terrain terrain, Terraformer terraformer);
}
