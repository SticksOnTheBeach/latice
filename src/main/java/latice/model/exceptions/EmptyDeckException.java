package latice.model.exceptions;

public class EmptyDeckException extends Exception{
	public EmptyDeckException(String message) {
		super(message);
	}
	
	public EmptyDeckException() {
		super("Error : the deck is empty !");
	}
}
