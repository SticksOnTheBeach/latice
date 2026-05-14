package latice.console;

import latice.model.Player;
import latice.model.Position;

import java.util.Scanner;

public class Console {
	
	public static final String SEPARATOR_LINE = "--------------------------------------------";
	
	public static void message(String text) {
		System.out.println(text);
	}

	public static void title(String text) {
		message(SEPARATOR_LINE);
		message("-- " + text + " --");
		message(SEPARATOR_LINE);
	}	
	public Position selectPosition() {
		int lenght;
		int width;
		Scanner scanner = new Scanner(System.in);
		message("Veuillez entrer la ligne (1-9) :");
		lenght = scanner.nextInt() - 1; // car l'indice commence a 0
		while (lenght < 0 || lenght > 8) {
			message("Veuillez entrer une ligne valide (1-9) :");
			lenght = scanner.nextInt() - 1;
		}
		message("Veuillez entrer la colonne (1-9) :");
		width = scanner.nextInt() - 1; // car l'indice commence a 0
		while (width < 0 || width > 8) {
			message("Veuillez entrer une colonne valide (1-9) :");
			width = scanner.nextInt() - 1;

	}
	return new Position(lenght, width);
	}
	public int selectTileInRack(Player player) {
		int tileValue;
		Scanner scanner = new Scanner(System.in);
		message("Veuillez entrer la valeur de la tuile que vous souhaitez jouer entre 1 et" + player.getRack().size());
		tileValue = scanner.nextInt() - 1; // car l'indice commence a 0
		while (tileValue < 0 || tileValue >= player.getRack().size()) {
			message("Veuillez entrer une valeur valide entre 1 et" + player.getRack().size());
			tileValue = scanner.nextInt() - 1;
		}
		return tileValue;
	}
}

		


