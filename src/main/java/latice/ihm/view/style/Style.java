package latice.ihm.view.style;

public class Style {

    public static final String BTN_STYLE =
        "-fx-font-size: 16px; " +
        "-fx-font-weight: bold; " +
        "-fx-font-family: \"Courier New\"; " +
        "-fx-text-fill: #1a2e35; " +
        "-fx-background-color: #a8d8a8; " +
        "-fx-background-radius: 0px; " +
        "-fx-border-width: 3px; " +
        "-fx-padding: 10px 30px; " +
        "-fx-cursor: hand; " +
        "-fx-min-width: 160px;";

    public static final String BTN_HOVER_STYLE =
        "-fx-font-size: 16px; " +
        "-fx-font-weight: bold; " +
        "-fx-font-family: \"Courier New\"; " +
        "-fx-text-fill: #1a2e35; " +
        "-fx-background-color: #ffffff; " +
        "-fx-background-radius: 0px; " +
        "-fx-border-color: #ffffff #ffffff #ffffff #ffffff; " +
        "-fx-border-width: 3px; " +
        "-fx-padding: 10px 30px; " +
        "-fx-cursor: hand; " +
        "-fx-min-width: 160px;";

    public static final String BTN_GHOST_STYLE =
        "-fx-font-size: 14px; " +
        "-fx-font-family: \"Courier New\"; " +
        "-fx-background-color: transparent; " +
        "-fx-border-color: rgba(168,216,168,0.4); " +
        "-fx-border-radius: 6px; " +
        "-fx-background-radius: 6px; " +
        "-fx-text-fill: rgba(168,216,168,0.8); " +
        "-fx-padding: 8px 14px; " +
        "-fx-cursor: hand;";
    
    public static final String BTN_GHOST_HOVER_STYLE =
            "-fx-font-size: 14px; " +
            "-fx-font-family: \"Courier New\"; " +
            "-fx-background-color: transparent; " +
            "-fx-border-color: #ffffff; " +
            "-fx-border-radius: 6px; " +
            "-fx-background-radius: 6px; " +
            "-fx-text-fill: rgba(168,216,168,0.8); " +
            "-fx-padding: 8px 14px; " +
            "-fx-cursor: hand;";

    private static final String BTN_COLOR_BASE = 
            "-fx-border-radius: 50em; " +
            "-fx-background-radius: 50em; " +
            "-fx-min-width: 28px; " +
            "-fx-min-height: 28px; " +
            "-fx-max-width: 28px; " +
            "-fx-max-height: 28px; " +
            "-fx-border-width: 2px; " +
            "-fx-padding: 0; " +
            "-fx-cursor: hand;";

        // Rouge
        public static final String BTN_COLOR_RED = 
        		BTN_COLOR_BASE + 
        		"-fx-background-color: red; -fx-border-color: red;";
        public static final String BTN_COLOR_RED_HOVER = 
        		BTN_COLOR_BASE + 
        		"-fx-background-color: red; -fx-border-color: #ffffff;";

        // Bleu
        public static final String BTN_COLOR_BLUE = 
        		BTN_COLOR_BASE + 
        		"-fx-background-color: blue; -fx-border-color: blue;";
        public static final String BTN_COLOR_BLUE_HOVER = 
        		BTN_COLOR_BASE + 
        		"-fx-background-color: blue; -fx-border-color: #ffffff;";

        // Vert
        public static final String BTN_COLOR_GREEN = 
        		BTN_COLOR_BASE + 
        		"-fx-background-color: green; -fx-border-color: green;";
        public static final String BTN_COLOR_GREEN_HOVER = 
        		BTN_COLOR_BASE + 
        		"-fx-background-color: green; -fx-border-color: #ffffff;";

        // Jaune
        public static final String BTN_COLOR_YELLOW = 
        		BTN_COLOR_BASE + 
        		"-fx-background-color: yellow; -fx-border-color: yellow;";
        public static final String BTN_COLOR_YELLOW_HOVER = 
        		BTN_COLOR_BASE + 
        		"-fx-background-color: yellow; -fx-border-color: #ffffff;";
    
    public static final String BTN_COLOR_HOVER_STYLE =
            "-fx-font-size: 14px; " +
            "-fx-font-family: \"Courier New\"; " +
            "-fx-background-color: transparent; " +
            "-fx-border-color: #ffffff; " +
            "-fx-border-radius: 50em; " +
            "-fx-background-radius: 50em; " +
            "-fx-min-width: 28px; " +
            "-fx-min-height: 28px; " +
            "-fx-max-width: 28px; " +
            "-fx-max-height: 28px; " +
            "-fx-padding: 0; " +
            "-fx-alignment: center; " +
            "-fx-text-fill: rgba(168,216,168,0.8); " +
            "-fx-cursor: hand;";

    public static final String INPUT_STYLE =
        "-fx-background-color: rgba(255,255,255,0.08); " +
        "-fx-border-color: rgba(168,216,168,0.3); " +
        "-fx-border-radius: 6px; " +
        "-fx-background-radius: 6px; " +
        "-fx-text-fill: white; " +
        "-fx-font-size: 14px; " +
        "-fx-padding: 8px 12px;";

    public static final String BTN_PARAMETERS_STYLE =
        "-fx-background-color: transparent; " +
        "-fx-cursor: hand; " +
        "-fx-border-color: transparent; " +
        "-fx-border-width: 2px; " +
        "-fx-border-radius: 8px; " +
        "-fx-background-radius: 8px; " +
        "-fx-padding: 0px;";

    public static final String BTN_PARAMETERS_HOVER_STYLE =
        "-fx-background-color: rgba(255,255,255,0.15); " +
        "-fx-cursor: hand; " +
        "-fx-border-color: rgba(255,255,255,0.3); " +
        "-fx-border-width: 2px; " +
        "-fx-border-radius: 8px; " +
        "-fx-background-radius: 8px; " +
        "-fx-padding: 0px;";

    public static final String BTN_PARAMETERS_PRESSED_STYLE =
        "-fx-background-color: rgba(255,255,255,0.05); " +
        "-fx-cursor: hand; " +
        "-fx-border-color: rgba(255,255,255,0.2); " +
        "-fx-border-width: 2px; " +
        "-fx-border-radius: 8px; " +
        "-fx-background-radius: 8px; " +
        "-fx-padding: 0px;";
}