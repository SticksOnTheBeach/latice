package latice.exceptions;

public class EmptyDeckException extends Exception{
	// TODO	
	public EmptyDeckException(String message) {
		super(message);
	}
	
	public EmptyDeckException() {
		super("Error : the deck is empty !");
	}
}
