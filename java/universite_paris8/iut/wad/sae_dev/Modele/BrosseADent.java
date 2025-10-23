package universite_paris8.iut.wad.sae_dev.Modele;

import java.util.*;

public class BrosseADent extends Ennemi {

    private int compteur = 0;
    private int distanceDetection = 300; // Distance pour activer le BFS

    public BrosseADent(int x, int y, Terrain terrain, Joueur joueur) {
        super(x, y, terrain, joueur, 46, 60);
        setDansLesAirs(true);
    }

    @Override
    public void seDeplacer() {
        compteur++;

        // Réduire la fréquence du calcul BFS pour les performances
        if (compteur >= 60) { // Calcul BFS toutes les 15 frames (~4 fois par seconde)
            compteur = 0;

            int distanceX = calculerDistanceX();
            int distanceY = calculerDistanceY();

            if (estJoueurProche(distanceX, distanceY)) {
                deplacerVersJoueurBFS();
            }
        }

        appliquerGravite();
    }

    @Override
    public void deplacerVersJoueur(int distanceX, int distanceY) {
    }

    /**
     * Déplace la BrosseADent vers le joueur en utilisant l'algorithme BFS
     */
    public void deplacerVersJoueurBFS() {
        Terrain terrain = getTerrain();

        // Coordonnées de départ (BrosseADent)
        int debutX = terrain.getColonne(getX());
        int debutY = terrain.getLigne(getY());

        // Coordonnées cible (Joueur)
        int xJoueur = terrain.getColonne(getJoueur().getX());
        int yJoueur = terrain.getLigne(getJoueur().getY());

        // Dimensions du terrain
        int ligne = terrain.ligne();
        int colonne = terrain.colonne();

        // Vérifier les limites
        if (debutX < 0 || debutX >= colonne || debutY < 0 || debutY >= ligne ||
                xJoueur < 0 || xJoueur >= colonne || yJoueur < 0 || yJoueur >= ligne) {
            return;
        }

        // Structures pour le BFS
        boolean[][] visite = new boolean[ligne][colonne];
        Map<String, int[]> parentMap = new HashMap<>();
        Queue<int[]> queue = new LinkedList<>();

        // Initialisation
        queue.add(new int[]{debutY, debutX});
        visite[debutY][debutX] = true;

        // Directions : droite, bas, gauche, haut
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        boolean trouver = false;

        // Algorithme BFS
        while (!queue.isEmpty() && !trouver) {
            int[] current = queue.poll();
            int y = current[0];
            int x = current[1];

            // Vérifier si on a atteint le joueur
            if (x == xJoueur && y == yJoueur) {
                trouver = true;
                break;
            }

            // Explorer les 4 directions
            for (int[] d : directions) {
                int ny = y + d[0];
                int nx = x + d[1];

                // Vérifier les limites et les collisions
                if (estPositionValide(nx, ny, ligne, colonne, visite, terrain)) {
                    visite[ny][nx] = true;
                    queue.add(new int[]{ny, nx});
                    parentMap.put(ny + "," + nx, new int[]{y, x});
                }
            }
        }

        // Si aucun chemin trouvé, ne pas bouger
        if (!trouver) {
            return;
        }

        // Reconstruction du chemin
        List<int[]> path = new ArrayList<>();
        int[] curr = new int[]{yJoueur, xJoueur};

        while (!(curr[0] == debutY && curr[1] == debutX)) {
            path.add(curr);
            String key = curr[0] + "," + curr[1];
            if (!parentMap.containsKey(key)) {
                break; // Sécurité pour éviter les boucles infinies
            }
            curr = parentMap.get(key);
        }

        Collections.reverse(path);

        // Se déplacer vers la première case du chemin
        if (!path.isEmpty()) {
            int nextY = path.get(0)[0];
            int nextX = path.get(0)[1];

            // Convertir les coordonnées de grille en pixels et déplacer
            int nouveauX = nextX * terrain.getTailleTuile();
            int nouveauY = nextY * terrain.getTailleTuile();

            // Déterminer la direction pour l'animation
            if (nouveauX > getX()) {
                setDirection(1); // Droite
            } else if (nouveauX < getX()) {
                setDirection(-1); // Gauche
            }

            setX(nouveauX);
            setY(nouveauY);
        }
    }

    /**
     * Vérifie si une position est valide pour le BFS
     */
    private boolean estPositionValide(int x, int y, int maxLigne, int maxColonne,
                                      boolean[][] visite, Terrain terrain) {
        // Vérifier les limites
        if (y < 0 || y >= maxLigne || x < 0 || x >= maxColonne) {
            return false;
        }

        // Vérifier si déjà visité
        if (visite[y][x]) {
            return false;
        }

        // Vérifier les collisions (convertir en coordonnées pixel)
        int pixelX = x * terrain.getTailleTuile();
        int pixelY = y * terrain.getTailleTuile();

        return !terrain.collision(pixelX, pixelY);
    }

    public int calculerDistanceX() {
        return getJoueur().getX() - getX();
    }

    public int calculerDistanceY() {
        return getJoueur().getY() - getY();
    }

    public boolean estJoueurProche(int distanceX, int distanceY) {
        int distanceAbsX = Math.abs(distanceX);
        int distanceAbsY = Math.abs(distanceY);

        return distanceAbsX <= distanceDetection && distanceAbsY <= distanceDetection;
    }

    @Override
    public boolean toucheJoueur() {
        int distance = 35;
        int distanceX = Math.abs(getX() - getJoueur().getX());
        int distanceY = Math.abs(getY() - getJoueur().getY());

        return distanceX < distance && distanceY < distance;
    }
}