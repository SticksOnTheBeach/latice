package latice.util;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class ImageLoader {
	// CACHE PERMERTTANT DE NE PAS CHARGER DES IMAGES DÉJÀ CHARGÉE POUR PLUS D'OPTIMISATION
	private static final Map<String, Image> cache = new HashMap<>();

    /**
     * Charge une image depuis le dossier resources.
     * Si elle a déjà été chargée, elle est retournée depuis le cache.
     *
     * @param path Le chemin de l'image ex: "/images/sun.png"
     * @return L'image chargée, ou null si introuvable.
     */
    public static Image load(String path) {
        if (cache.containsKey(path)) {
            return cache.get(path);
        }

        try {
            Image image = new Image(ImageLoader.class.getResourceAsStream(path));
            cache.put(path, image);
            return image;
        } catch (Exception e) {
            System.err.println("Image introuvable : " + path);
            return null;
        }
    }
}