package latice.model.exceptions;

public class InvalidImagePathException extends Exception {
    private String path;

    public InvalidImagePathException(String message, String path) {
        super(message + " (Chemin introuvable : " + path + ")");
        this.path = path;
    }
	
    public String getPath() {
        return path;
    }
}