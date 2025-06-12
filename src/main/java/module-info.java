module universite_paris8.iut.wad.sae_dev {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;

    opens universite_paris8.iut.wad.sae_dev to javafx.fxml;
    exports universite_paris8.iut.wad.sae_dev;
    exports universite_paris8.iut.wad.sae_dev.Modele;
    opens universite_paris8.iut.wad.sae_dev.Modele to javafx.fxml;
    exports universite_paris8.iut.wad.sae_dev.Controleur;
    opens universite_paris8.iut.wad.sae_dev.Controleur to javafx.fxml;
}