package pt.iscte.pidesco.clazznav;

import java.util.HashMap;

/**
 * 
 * @author tiagocms
 *
 */
public class Parameters {

	private Parameters(){
		//like singleton	
	}

	// App config
	public static boolean DEBUG_MODE = false;

	// View sizes
	public static int VIEW_WIDTH = 300;
	public static int VIEW_HEIGHT = 300;

	// Buttons size
	public static int BUTTON_WIDTH  = 50;
	public static int BUTTON_HEIGHT = 50;

	// View Modes
	public static int VIEW_MODE_SIMPLE = 1;
	public static int VIEW_MODE_GRAPHIC = 2;

	public static String VIEW_MODE_SIMPLE_DESC = "MODO SIMPLES";
	public static String VIEW_MODE_GRAPHIC_DESC = "MODO GRÁFICO";

	public static HashMap<Integer, String> VIEW_MODES = new HashMap<>(); 
	static {
		VIEW_MODES.put(VIEW_MODE_SIMPLE, VIEW_MODE_SIMPLE_DESC);
		VIEW_MODES.put(VIEW_MODE_GRAPHIC, VIEW_MODE_GRAPHIC_DESC);
	}

}

