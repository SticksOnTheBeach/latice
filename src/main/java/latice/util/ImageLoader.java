package latice.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;
import latice.model.exceptions.InvalidImagePathException;

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
	public static Image load(String path) throws InvalidImagePathException {
        // Si c'est déjà dans le coffre, on se sert !
        if (cache.containsKey(path)) {
            return cache.get(path);
        }

        try {
            InputStream is = ImageLoader.class.getResourceAsStream(path);
            
            if (is == null) {
                throw new InvalidImagePathException("ERROR : l'image est introuvable !", path);
            }

            Image image = new Image(is);
            cache.put(path, image);
            return image;
            
        } catch (InvalidImagePathException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidImagePathException("Erreur inattendue lors du chargement : " + e.getMessage(), path);
        }
    }
}