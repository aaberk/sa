package universite_paris8.iut.wad.sae_dev.Modele;

import universite_paris8.iut.wad.sae_dev.Modele.Entites.*;
import universite_paris8.iut.wad.sae_dev.Modele.Projectiles.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe principale qui gère la logique du jeu.
 * Gère les ennemis, projectiles, collisions et interactions entre entités.
 */
public class Jeu {
    private Terrain terrain;
    private Joueur joueur;
    private List<Ennemi> ennemis;
    private List<Projectile> projectiles;
    private static final int SEUIL_COLLISION = 40;

    public Jeu(Terrain terrain, Joueur joueur) {
        this.terrain = terrain;
        this.joueur = joueur;
        this.ennemis = new ArrayList<>();
        this.projectiles = new ArrayList<>();
    }

    // === GESTION DES ENNEMIS ===

    /**
     * Ajoute un ennemi au jeu
     */
    public void ajouterEnnemi(Ennemi ennemi) {
        ennemis.add(ennemi);
    }

    /**
     * Retire un ennemi du jeu
     */
    public void retirerEnnemi(Ennemi ennemi) {
        if (ennemis != null) {
            ennemis.remove(ennemi);
            System.out.println("Ennemi retiré du jeu");
        }
    }

    /**
     * Retourne la liste des ennemis
     */
    public List<Ennemi> getListeEnnemis() {
        return ennemis;
    }

    /**
     * Déplace tous les ennemis vivants
     */
    public void deplacerEnnemis() {
        for (Ennemi ennemi : ennemis) {
            if (!ennemi.estMort()) {
                ennemi.seDeplacer();
            }
        }
    }

    // === GESTION DES PROJECTILES ===

    /**
     * Ajoute un projectile au jeu
     */
    public void ajouterProjectile(Projectile p) {
        projectiles.add(p);
    }

    /**
     * Retire un projectile du jeu
     */
    public void retirerProjectile(Projectile projectile) {
        if (projectiles != null) {
            projectiles.remove(projectile);
        }
    }

    /**
     * Retourne la liste des projectiles
     */
    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    /**
     * Met à jour tous les projectiles et gère leur cycle de vie
     */
    public void miseAJourProjectiles() {
        Iterator<Projectile> it = projectiles.iterator();
        while (it.hasNext()) {
            Projectile p = it.next();
            p.seDeplacer();
            if (!p.estActif()) {
                it.remove();
            }
        }
    }

    /**
     * Gère la création de nouveaux projectiles et la mise à jour des existants
     */
    public List<Projectile> gererProjectiles() {
        List<Projectile> nouveaux = new ArrayList<>();

        // Créer les nouveaux projectiles des ennemis
        for (Ennemi ennemi : ennemis) {
            if (ennemi instanceof DentifriceVolant) {
                DentifriceVolant dentifrice = (DentifriceVolant) ennemi;
                if (dentifrice.peutTirer()) {
                    ProjectileDentifrice p = ProjectileDentifrice.creerProjectileVertical(
                            dentifrice.getPositionTirX(),
                            dentifrice.getPositionTirY(),
                            terrain
                    );
                    ajouterProjectile(p);
                    nouveaux.add(p);
                    dentifrice.reinitialiserCompteurTir();
                }
            }
        }

        // Mettre à jour tous les projectiles
        miseAJourProjectiles();

        // Gérer les collisions projectiles-ennemis
        gererCollisionsProjectilesEnnemis();

        return nouveaux;
    }

    /**
     * Crée une flèche tirée par le joueur
     */
    public FlecheArc tirerFlecheDuJoueur(int direction) {
        int flecheX = joueur.getX() + 35;
        int flecheY = joueur.getY() + 10;
        FlecheArc fleche = new FlecheArc(flecheX, flecheY, direction, terrain, 8, 300);
        ajouterProjectile(fleche);
        return fleche;
    }

    // === GESTION DES COLLISIONS ===

    /**
     * Gère les collisions entre les projectiles du joueur et les ennemis
     */
    private void gererCollisionsProjectilesEnnemis() {
        List<Projectile> projectilesASupprimer = new ArrayList<>();
        List<Ennemi> ennemisARetirer = new ArrayList<>();

        for (Projectile projectile : projectiles) {
            if (projectile.estProjectileDuJoueur() && projectile.estActif()) {
                for (Ennemi ennemi : ennemis) {
                    if (!ennemi.estMort() && projectileToucheEnnemi(projectile, ennemi)) {
                        ennemi.subirDegats(1);
                        projectile.desactiver();
                        projectilesASupprimer.add(projectile);

                        if (ennemi.estMort()) {
                            System.out.println(ennemi.getClass().getSimpleName() + " éliminé !");
                            ennemisARetirer.add(ennemi);
                        }
                        break;
                    }
                }
            }
        }

        for (Projectile proj : projectilesASupprimer) {
            retirerProjectile(proj);
        }

        for (Ennemi ennemi : ennemisARetirer) {
            retirerEnnemi(ennemi);
        }
    }

    /**
     * Vérifie si un projectile touche un ennemi
     */
    private boolean projectileToucheEnnemi(Projectile projectile, Ennemi ennemi) {
        int distanceX = Math.abs(projectile.getX() - ennemi.getX());
        int distanceY = Math.abs(projectile.getY() - ennemi.getY());
        return distanceX < SEUIL_COLLISION && distanceY < SEUIL_COLLISION;
    }

    /**
     * Gère les collisions entre les ennemis/projectiles et le joueur
     */
    public void gererCollisionsEnnemisJoueur() {
        // Collision avec les ennemis
        for (Ennemi ennemi : ennemis) {
            if (!ennemi.estMort() && ennemi.toucheJoueur()) {
                joueur.retirerVie();
                return;
            }
        }

        // Collision avec les projectiles ennemis
        for (Projectile projectile : projectiles) {
            if (projectile.estProjectileEnnemi() && projectile.toucheJoueur(joueur)) {
                joueur.retirerVie();
                System.out.println("Projectile ennemi touche le joueur!");
                break;
            }
        }
    }

    // === GETTERS ===

    public Terrain getTerrain() {
        return terrain;
    }

    public Joueur getJoueur() {
        return joueur;
    }
}