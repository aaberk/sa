package universite_paris8.iut.wad.sae_dev.Modele;

public enum TypeMateriaux {
    COOKIE(null,Role.CONSTRUCTION),
    PIOCHE(new Pioche(),Role.OUTIL),
    ARC(new Arc(),Role.ARME),
    EPEE(new Epee(),Role.ARME),
    BROWNIE(null,Role.CONSTRUCTION),
    //    SUCRE_ORGE(Role.CONSOMMABLE),
//    BONBON_COCA(Role.CONSOMMABLE),
//    CARAMEL(Role.CONSOMMABLE),
//    BONBON_CLE(Role.MONNAIE),
//    CLE(Role.OUTIL),
//    PELLE(Role.OUTIL),
    PELOUSE(null,Role.CONSTRUCTION);

    private final ObjetUtilisable objet;
    private final Role role;

    TypeMateriaux(ObjetUtilisable objet, Role role) {
        this.objet = objet;
        this.role = role;

    }

    public ObjetUtilisable getObjet() {
        if (this == PIOCHE) {
            return new Pioche();
        } else if (this == ARC) {
            return new Arc();
        } else if (this == EPEE) {
            return new Epee();
        }
        return null;
    }


    public Role getRole() {
        return role;
    }
}
